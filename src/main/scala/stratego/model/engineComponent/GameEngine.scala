package stratego.model.engineComponent

import com.google.inject.Inject
import stratego.gameEngine.GameStatus._
import GameState._
import stratego.gameEngine.GameStatus
import stratego.model.playerComponent.PlayerType._
import stratego.model.engineComponent.GameEvent.{AttackEvent, FigureSetEvent, GameQuitEvent, GameStartedEvent, InvalidMoveEvent}
import stratego.model.gridComponent.Figure.{Bomb, Captain, Colonel, Flag, Lieutenant, Major, Marshal, Miner, Scout, Sergeant, Spy}
import stratego.model.gridComponent.FigureType.FigureType
import stratego.model.gridComponent.FieldType._
import stratego.model.gridComponent.{FieldType, Figure, FigureSet, FigureType, GridInterface, Position}
import stratego.model.playerComponent.Player

class GameEngine @Inject()(var grid: GridInterface, //TODO: Think about making Game Engine Immmutable
                           var gameState: GameState = INACTIVE,
                           val playerA: Player = Player("PlayerA", A_SIDE),
                           val playerB: Player = Player("PlayerB", B_SIDE), //TODO: Think about extracting figureset from player
                           var winner: Option[Player] = None,
                           var statusLine: GameStatus = IDLE) extends GameEngineInterface {

  var activePlayer: Player = playerA // Sets player A to start the game
  var figureSet = Map(playerA -> new FigureSet(playerA), playerB -> new FigureSet(playerB))

  def startNewGame(): Unit = {
    this.grid = this.grid.createNewGrid
    this.gameState = NEW_GAME
    publish(GameStartedEvent)
  }

  def quitGame(): Unit = { //TODO: Review quit game logic
    this.gameState = END
    publish(GameQuitEvent)
  }

  def getFigure(position: Position): Option[Figure] = {
    this.grid.getField(position).getFigure
  }

  def setFigure(figureType: FigureType, position: Position):Unit = {
    if(figureSet(activePlayer).getFigureCount(figureType) > 0) {
      if (grid.getField(position).getFieldType() == activePlayer.fieldType) {
        if(grid.getField(position).getFigure() != None) {
          val figureSetTmp = figureSet(activePlayer).addFigure(grid.getField(position).getFigure().get)
          figureSet = figureSet.updated(activePlayer, figureSetTmp)
        }
        val figureSetTmp = figureSet(activePlayer).removeFigure(figureType)
        figureSet = figureSet.updated(activePlayer, figureSetTmp)
        figureSet(activePlayer).figures.get(figureType).foreach(println)
        grid = grid.assignField(position, figureSet(activePlayer).getLastFigure())
      } else {
        statusLine = GameStatus.INVALID_POSITION
      }
    } else {
      statusLine = GameStatus.NO_FIGURES_LEFT
    }
    gameState = SET_FIGURES
    publish(FigureSetEvent)
  }

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

  def moveFigure(from: Position, to: Position): Unit = {
    val source = this.grid.getField(from)
    val destination = this.grid.getField(to)
    if (source.getFigure.isDefined &&
      source.getFigure.get.player == activePlayer &&
      !isImmobileFigure(source.getFigure.get) ) {
      //TODO: Implement move validation for spy
      val figure = source.getFigure.get
      if (destination.getFieldType != FieldType.NO_FIELD && destination.getFigure.isEmpty && isValidMove(from, to)) {
        this.grid = this.grid.move(from, to)
      } else if (destination.getFigure.get.player != activePlayer && isValidMove(from, to)) {
        val opponent = destination.getFigure.get
        (figure, opponent) match {
          case (a:Spy, b:Marshal) => this.grid = this.grid.move(from, to)
          case (c:Miner, d:Bomb) => this.grid = this.grid.move(from, to)
          case (a:Figure, b:Bomb) => deleteFigureAt(from)
          case (a:Figure, b:Flag) =>
            this.winner = Some(activePlayer)
            this.gameState = END
          case _ =>
            if (figure.strength > opponent.strength) {
              // figure wins!
              deleteFigureAt(to)
            }
            else if (figure.strength < opponent.strength) {
              // opponent wins!
              deleteFigureAt(from)
            }
            else {
              // draw
              deleteFigureAt(from)
              deleteFigureAt(to)
            }
            publish(AttackEvent)
        }
      } else {
        this.statusLine = GameStatus.INVALID_POSITION
        publish(InvalidMoveEvent)
      }
    } else {
      this.statusLine = GameStatus.NO_VALID_FIGURE
      publish(InvalidMoveEvent)
    }
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

  private def deleteFigureAt(position: Position): Unit = {
    this.grid = this.grid.assignField(position, None)
  }

  def startBattle: Unit = {
    gameState = FIGHT
    publish(new GameChanged)
  }

  def changePlayer: Unit = {
    if (activePlayer == playerA) activePlayer = playerB else activePlayer = playerA
    publish(new GameChanged)
  }

  def getActivePlayer: Player = activePlayer

  def gridToString: String = grid.toStringTUI(gameState, activePlayer)

  def getGameState: String = gameState.toString

  def getFigureSetActivePlayer: FigureSet = figureSet(activePlayer)
}
