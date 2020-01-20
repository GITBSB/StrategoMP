package stratego.model.engineComponent

import stratego.gameEngine.GameStatus._
import stratego.model.engineComponent.GameState._
import stratego.model.gridComponent.FieldType._
import stratego.model.gridComponent.Figure._
import stratego.model.gridComponent.FigureType._
import stratego.model.gridComponent.{FigureType => _, _}
import stratego.model.playerComponent.Player

// TODO: Think about adding State Pattern instead of enum gameState
case class GameEngine (grid: GridInterface = Grid().createNewGrid(),
                           gameState: GameState = NOT_STARTED,
                           playerA: Player = Player("PlayerA", A_SIDE),
                           playerB: Player = Player("PlayerB", B_SIDE),
                           activePlayer: Player = Player("PlayerA", A_SIDE),
                           winner: Option[Player] = None,
                           figureSet: Map[Player, FigureSet] = Map(Player("PlayerA", A_SIDE) -> new FigureSet(Player("PlayerA", A_SIDE)), Player("PlayerB", B_SIDE) -> new FigureSet(Player("PlayerB", B_SIDE))),
                           statusLine: GameStatus = IDLE) extends GameEngineInterface {

  def startNewGame(): GameEngineInterface = {
    val newGameEngine = copy(gameState = GameState.SET_FIGURES)
    publish(GameStartedEvent(newGameEngine)) // Add values which changed to the event so listeners can operate on them
    newGameEngine
  }

  def quitGame(): GameEngineInterface = { //TODO: Review quit game logic
    val newGameEngine = copy(gameState = END)
    publish(GameQuitEvent(newGameEngine))
    newGameEngine
  }

  def setFigure(figureType: FigureType, position: Position): GameEngineInterface = {
    var newGrid = grid
    var newGameState = SET_FIGURES
    var newStatusLine = FIGURE_SET
    var newFigureSet = figureSet
    var nextPlayer = activePlayer
    val inactivePlayer = if (activePlayer == playerA) playerB else playerA

    if (newFigureSet(activePlayer).getFigureCount(figureType) > 0) {
      if (grid.getField(position).getFieldType() == activePlayer.fieldType) {
        if (grid.getField(position).getFigure().isDefined) {
          val figureSetTmp = newFigureSet(activePlayer).addFigure(grid.getField(position).getFigure().get)
          newFigureSet = newFigureSet.updated(activePlayer, figureSetTmp)
        }
        val figureSetTmp = newFigureSet(activePlayer).removeFigure(figureType)
        newFigureSet = newFigureSet.updated(activePlayer, figureSetTmp)
        newGrid = grid.assignField(position, newFigureSet(activePlayer).getLastFigure())
      } else {
        newStatusLine = INVALID_POSITION
      }
    } else {
      // No figures of this type left. Player has to redo this move!
      newStatusLine = NO_FIGURES_LEFT
    }

    if (newFigureSet(inactivePlayer).noFiguresLeft() && newFigureSet(activePlayer).noFiguresLeft()) {
      // No player has figures left to set
      newStatusLine = BATTLE_START
      newGameState = FIGHT
      val newGameEngine = copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, figureSet = newFigureSet, activePlayer = nextPlayer)
      publish(MoveFigureEvent(newGameEngine))
      newGameEngine
    } else {
      if (newFigureSet(activePlayer).noFiguresLeft()) {
        // Next player is allowed to set its figures
        nextPlayer = inactivePlayer
      }
      val newGameEngine = copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, figureSet = newFigureSet, activePlayer = nextPlayer)
      publish(FigureSetEvent(newGameEngine))
      newGameEngine
    }
  }

  def deleteFigure(position: Position): GameEngineInterface = {
    var newGrid = grid
    var newFigureSet = figureSet
    var newStatusLine = WRONG_INPUT
    if (grid.getField(position).getFieldType() == activePlayer.fieldType) {
      if (grid.getField(position).getFigure().isDefined) {
        newFigureSet = newFigureSet.updated(activePlayer, newFigureSet(activePlayer).addFigure(grid.getField(position).getFigure().get))
        newGrid = grid.assignField(position, None)
        newStatusLine = FIGURE_DELETED
      }
    }
    val newGameEngine = copy(grid = newGrid, figureSet = newFigureSet, statusLine = newStatusLine)
    publish(FigureDeletedEvent(newGameEngine))
    newGameEngine
  }

  def moveFigure(from: Position, to: Position): GameEngineInterface = {
    val source = grid.getField(from)
    val destination = grid.getField(to)
    var newGrid = grid
    var newGameState = gameState
    var newStatusLine = statusLine
    var newWinner = winner
    var nextPlayer = if (activePlayer == playerA) playerB else playerA

    if (source.getFigure.isDefined &&
      source.getFigure.get.player == activePlayer &&
      !isImmobileFigure(source.getFigure.get) ) {
      //TODO: Implement move validation for scout
      val figure = source.getFigure.get
      if (destination.getFieldType != NO_FIELD && destination.getFigure.isEmpty && isValidMove(from, to)) {
        newGrid = newGrid.move(from, to)
        newStatusLine = MOVE_FIGURE
        val newGameEngine = copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, winner = newWinner, activePlayer = nextPlayer)
        publish(MoveFigureEvent(newGameEngine))
      } else if (destination.getFigure.isDefined && destination.getFigure.get.player != activePlayer && isValidMove(from, to)) {
        val opponent = destination.getFigure.get
        // TODO: Wrap case code in generic function and stay DRY
        (figure, opponent) match {
          case (a:Spy, b:Marshal) =>
            newGrid = newGrid.move(from, to)
            newStatusLine = SPY_ATTACKS_MARSHAL
            val newGameEngine = copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, winner = newWinner, activePlayer = nextPlayer)
            publish(AttackEvent(newGameEngine))
          case (c:Miner, d:Bomb) =>
            newGrid = newGrid.move(from, to)
            newStatusLine = MINER_ATTACKS_BOMB
            val newGameEngine = copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, winner = newWinner, activePlayer = nextPlayer)
            publish(AttackEvent(newGameEngine))
          case (a:Figure, b:Bomb) =>
            newGrid = newGrid.assignField(from, None)
            newStatusLine = BOMB_EXPLODES
            val newGameEngine = copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, winner = newWinner, activePlayer = nextPlayer)
            publish(AttackEvent(newGameEngine))
          case (a:Figure, b:Flag) =>
            newWinner = Some(activePlayer)
            newGameState = END
            newStatusLine = FLAG_FOUND
            val newGameEngine = copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, winner = newWinner, activePlayer = nextPlayer)
            publish(WinnerEvent(newGameEngine))
          case _ =>
            if (figure.strength > opponent.strength) {
              // figure wins!
              newStatusLine = ATTACK_WIN
              newGrid = newGrid.move(from, to)
            } else if (figure.strength < opponent.strength) {
              // opponent wins!
              newStatusLine = ATTACK_LOOSE
              newGrid = newGrid.assignField(from, None)
            } else {
              // draw
              newStatusLine = ATTACK_DRAW
              newGrid = newGrid.assignField(from, None)
              newGrid = newGrid.assignField(to, None)
            }
            val newGameEngine = copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, winner = newWinner, activePlayer = nextPlayer)
            publish(AttackEvent(newGameEngine))
        }
      } else {
        newStatusLine = INVALID_POSITION
        nextPlayer = activePlayer
        val newGameEngine = copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, winner = newWinner, activePlayer = nextPlayer)
        publish(InvalidMoveEvent(newGameEngine))
      }
    } else {
      newStatusLine = NO_VALID_FIGURE
      nextPlayer = activePlayer
      val newGameEngine = copy(grid = newGrid, gameState = newGameState, statusLine = newStatusLine, winner = newWinner, activePlayer = nextPlayer)
      publish(InvalidMoveEvent(newGameEngine))
    }
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
    val newGameEngine = copy(gameState = FIGHT)
    publish(GameStartedEvent(newGameEngine))
    newGameEngine
  }

  def changePlayer: GameEngineInterface = {
    val newGameEngine = copy(activePlayer = if (activePlayer == playerA) playerB else playerA)
    publish(ChangePlayerEvent(newGameEngine))
    newGameEngine
  }

  def gridToString: String = grid.toStringTUI(gameState, activePlayer) //TODO: When time refactor these toStringTUI methods

  def getFigure(position: Position): Option[Figure] = grid.getField(position).getFigure

  def getFieldStringGUI(position:Position): String = grid.getField(position).toStringGUI(gameState, activePlayer)

  def getFigureSetActivePlayer: FigureSet = figureSet(activePlayer)

  def getGameState: GameState = gameState

  def getActivePlayer: Player = activePlayer

  def getWinner: Option[Player] = winner

  def getStatusLine: GameStatus = statusLine

}