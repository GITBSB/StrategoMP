package stratego.model.engineComponent

import stratego.gameEngine.GameStatus._
import GameState._
import stratego.gameEngine.GameStatus
import stratego.model.gridComponent.Figure.{Bomb, Captain, Colonel, Flag, Lieutenant, Major, Marshal, Miner, Scout, Sergeant, Spy}
import stratego.model.gridComponent.FigureType.FigureType
import stratego.model.gridComponent.FieldType._
import stratego.model.gridComponent.{FieldType, Figure, FigureSet, FigureType, GridInterface, Position}
import stratego.model.playerComponent.Player
import stratego.model.gridComponent.Grid

// TODO: Think about adding State Pattern instead of enum gameState
// TODO: Think about removing publish methods because these are sort of side effects. Observer pattern not purely function possible
// TODO: Maybe pass state changes as parameters to event so that the observers dont have to call the observee
// TODO: Top level component has to set all the current observers(Views) to the new version of the obersee(GameEngine)
case class GameEngine (grid: GridInterface = Grid().createNewGrid(),
                           gameState: GameState = NOT_STARTED,
                           playerA: Player = Player("PlayerA", A_SIDE),
                           playerB: Player = Player("PlayerB", B_SIDE),
                           activePlayer: Player = Player("PlayerA", A_SIDE),
                           winner: Option[Player] = None,
                           figureSet: Map[Player, FigureSet] = Map(Player("PlayerA", A_SIDE) -> new FigureSet(Player("PlayerA", A_SIDE)), Player("PlayerB", B_SIDE) -> new FigureSet(Player("PlayerB", B_SIDE))),
                           statusLine: GameStatus = IDLE) extends GameEngineInterface {

  def startNewGame(): GameEngineInterface = {
    publish(GameStartedEvent()) // Add values which changed to the event so listeners can operate on them
    copy(gameState=NEW_GAME)
  }

  def quitGame(): GameEngineInterface = { //TODO: Review quit game logic
    publish(GameQuitEvent())
    copy(gameState = END)
  }

  def setFigure(figureType: FigureType, position: Position): GameEngineInterface = {
    var newGrid = this.grid
    var newGameState = SET_FIGURES
    var newStatusLine = FIGURE_SET
    var newFigureSet = this.figureSet
    val inactivePlayer = if (activePlayer == playerA) playerA else playerB

    if (newFigureSet(activePlayer).getFigureCount(figureType) > 0) {
      if (grid.getField(position).getFieldType() == activePlayer.fieldType) {
        if(grid.getField(position).getFigure().isDefined) {
          val figureSetTmp = newFigureSet(activePlayer).addFigure(grid.getField(position).getFigure().get)
          newFigureSet = newFigureSet.updated(activePlayer, figureSetTmp)
        }
        val figureSetTmp = newFigureSet(activePlayer).removeFigure(figureType)
        newFigureSet = newFigureSet.updated(activePlayer, figureSetTmp)
        newFigureSet(activePlayer).figures.get(figureType).foreach(println)
        newGrid = grid.assignField(position, newFigureSet(activePlayer).getLastFigure())
      } else {
        newStatusLine = GameStatus.INVALID_POSITION
      }
    } else {
      newStatusLine = GameStatus.NO_FIGURES_LEFT
    }

    if (figureSet(inactivePlayer).noFiguresLeft() && figureSet(activePlayer).noFiguresLeft()) {
      // No player has figures left to set
      newStatusLine = GameStatus.NO_FIGURES_LEFT
      newGameState = FIGHT
    }

    publish(FigureSetEvent())
    copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, figureSet = newFigureSet, activePlayer = inactivePlayer)
  }

  //TODO: remove this method
  def createFigure(figureType: FigureType, player: Player): Figure = {
     figureType match { // Throws an exception if string does not match any FigureType!
      case FigureType.SCOUT => Scout(player)
      case FigureType.MARSHAL => Marshal(player)
      case FigureType.COLONEL => Colonel(player)
      case FigureType.MAJOR => Major(player)
      case FigureType.CAPTAIN => Captain(player)
      case FigureType.LIEUTENANT => Lieutenant(player)
      case FigureType.SERGEANT => Sergeant(player)
      case FigureType.MINER => Miner(player)
      case FigureType.FLAG => Flag(player)
      case FigureType.SPY => Spy(player)
      case FigureType.BOMB => Bomb(player)
    }
  }

  def moveFigure(from: Position, to: Position): GameEngineInterface = {
    val source = this.grid.getField(from)
    val destination = this.grid.getField(to)
    var newGrid = this.grid
    var newGameState = this.gameState
    var newStatusLine = this.statusLine
    var newWinner = this.winner
    val nextPlayer = if (activePlayer == playerA) playerA else playerB

    if (source.getFigure.isDefined &&
      source.getFigure.get.player == activePlayer &&
      !isImmobileFigure(source.getFigure.get) ) {
      //TODO: Implement move validation for scout
      val figure = source.getFigure.get
      if (destination.getFieldType != FieldType.NO_FIELD && destination.getFigure.isEmpty && isValidMove(from, to)) {
        newGrid = newGrid.move(from, to)
        newStatusLine = GameStatus.MOVE_FIGURE //TODO: Pass status arguments
        publish(MoveFigureEvent())
      } else if (destination.getFigure.get.player != activePlayer && isValidMove(from, to)) {
        val opponent = destination.getFigure.get
        (figure, opponent) match {
          case (a:Spy, b:Marshal) => newGrid = newGrid.move(from, to) // TODO: publish and set statusline
          case (c:Miner, d:Bomb) => newGrid = newGrid.move(from, to)// TODO: publish and set statusline
          case (a:Figure, b:Bomb) => newGrid = newGrid.assignField(from, None)// TODO: publish and set statusline
          case (a:Figure, b:Flag) =>
            newWinner = Some(activePlayer)
            newGameState = END
            publish(WinnerEvent())
          case _ =>
            if (figure.strength > opponent.strength) {
              // figure wins!
              newGrid.assignField(to, None)
            }
            else if (figure.strength < opponent.strength) {
              // opponent wins!
              newGrid.assignField(from, None)
            }
            else {
              // draw
              newGrid.assignField(from, None)
              newGrid.assignField(to, None)
            }
            //TODO: Set status line to indicate how won the attack
            publish(AttackEvent())
        }
      } else {
        newStatusLine = GameStatus.INVALID_POSITION
        publish(InvalidMoveEvent())
      }
    } else {
      newStatusLine = GameStatus.NO_VALID_FIGURE
      publish(InvalidMoveEvent())
    }
    //TODO: Fix - currently if a player does an invalid move his move is skipt
    copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, winner = newWinner, activePlayer = nextPlayer)
  }

  private def isImmobileFigure(figure: Figure): Boolean = {
    figure match {
      case b: Bomb => true
      case f: Flag => true
      case _ => false
    }
  }

  private def isValidMove(from: Position, to: Position): Boolean = {
    ((to.row == from.row + 1 || to.row == from.row - 1) && from.col == to.col) || ((to.col == from.col + 1 || to.col == from.col - 1) && from.row == to.row)
  }

  def startBattle: GameEngineInterface = {
    publish(GameStartedEvent())
    copy(gameState = FIGHT)
  }

  def changePlayer: GameEngineInterface = {
    publish(ChangePlayerEvent())
    copy(activePlayer = if (activePlayer == playerA) playerB else playerA)
  }

  def gridToString: String = grid.toStringTUI(gameState, activePlayer) //TODO: When time refactor these toStringTUI methods

  def getFigure(position: Position): Option[Figure] = this.grid.getField(position).getFigure

  def getFigureSetActivePlayer: FigureSet = figureSet(activePlayer)

  def getGrid: GridInterface = this.grid

  def getGameState: GameState = this.gameState

  def getActivePlayer: Player = this.activePlayer

  def getWinner: Option[Player] = this.winner

  def getStatusLine: GameStatus = this.statusLine

}
