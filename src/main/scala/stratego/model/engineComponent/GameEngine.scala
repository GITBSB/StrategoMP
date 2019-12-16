package stratego.model.engineComponent

import com.google.inject.Inject
import GameState._
import stratego.model.playerComponent.PlayerType._
import stratego.model.engineComponent.GameEvent.{FigureSetEvent, GameQuitEvent, GameStartedEvent}
import stratego.model.engineComponent.GameStatus._
import stratego.model.gridComponent.Figure.{Bomb, Captain, Colonel, Flag, Lieutenant, Major, Marshal, Miner, Scout, Sergeant, Spy}
import stratego.model.gridComponent.FigureType.FigureType
import stratego.model.gridComponent.FieldType._
import stratego.model.gridComponent.{FieldType, Figure, FigureSet, FigureType, GridInterface, Position}
import stratego.model.playerComponent.Player

class GameEngine @Inject()(var grid: GridInterface,
                           var gameState: GameState = INACTIVE,
                           var playerA: Player = Player("PlayerA", new FigureSet(), PLAYER_A, A_SIDE),
                           var playerB: Player = Player("PlayerB", new FigureSet(), PLAYER_B, B_SIDE), //TODO: Think about extracting figureset from player
                           var activePlayer: PlayerType = PLAYER_A, // Sets player A to start the game
                           var statusLine: GameStatus = IDLE) extends GameEngineInterface {

  def startNewGame(playerA: Player, playerB: Player): Unit = {
    this.grid = this.grid.createNewGrid
    this.playerA = playerA
    this.playerB = playerB
    this.gameState = NEW_GAME
    publish(GameStartedEvent)
  }

  def quitGame(): Unit = { //TODO: Review quit game logic
    this.gameState = END
    publish(GameQuitEvent)
  }

  def getFigure(position: Position): Option[Figure] = {
    this.grid.field(position).figure
  }

  def placeFigure(figureName: String, position: Position): Unit = {
    val figureType = FigureType.withName(figureName) // TODO: Handle possible exception
    val player = if (activePlayer == PLAYER_A) playerA else playerB

    if (player.figureSet.getFigureCount(figureType) != 0) {
      if (this.grid.field(position).fieldType == player.fieldType) {
        player.figureSet.deleteFromFigure(figureType)
        val figure = createFigure(figureType, player)
        this.grid = this.grid.assignField(position, figure)
      } else {
        statusLine = GameStatus.INVALID_POSITION
      }
    } else {
      statusLine = GameStatus.NO_FIGURES_LEFT
    }

    List(player.figureSet.productIterator)
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
    val source = this.grid.field(from)
    val destination = this.grid.field(to)
    if (source.figure.isDefined &&
      source.figure.get.player == activePlayer &&
      !isImmobileFigure(source.figure.get) ) {

      val figure = source.figure.get
      if (destination.fieldType != FieldType.NO_FIELD && destination.figure.isEmpty) {
        this.grid = this.grid.assignField(to, figure)
      } else if (destination.figure.get.player != activePlayer) {
        val opponent = destination.figure.get
        //TODO: run attack logic
      } else {
        this.statusLine = GameStatus.INVALID_POSITION
      }
    } else {
      this.statusLine = GameStatus.NO_VALID_FIGURE
    }
  }

  private def isImmobileFigure(figure: Figure): Boolean = {
    figure match {
      case b: Bomb => true
      case f: Flag => true
      case _ => false
    }
  }

  def gridToString: String = grid.toString

  def getGameState: String = gameState.toString
}
