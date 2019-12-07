package stratego.model.engineComponent

import com.google.inject.Inject
import GameState._
import stratego.model.engineComponent.ActivePlayer._
import stratego.model.engineComponent.GameEvent.{FigureSetEvent, GameQuitEvent, GameStartedEvent}
import stratego.model.engineComponent.GameStatus._
import stratego.model.gridComponent.Figure.{Bomb, Captain, Colonel, Flag, Lieutenant, Major, Marshal, Miner, Scout, Sergeant, Spy}
import stratego.model.gridComponent.FigureType.FigureType
import stratego.model.gridComponent.{Figure, FigureSet, FigureType, GridInterface}
import stratego.model.playerComponent.Player
import stratego.model.gridComponent.FieldType

class GameEngine @Inject()(var grid: GridInterface,
                           var gameState: GameState = INACTIVE,
                           var playerA: Player = Player("PlayerA", new FigureSet(), FieldType.A_SIDE),
                           var playerB: Player = Player("PlayerB", new FigureSet(), FieldType.B_SIDE),
                           var activePlayer: ActivePlayer = ActivePlayer.PLAYER_A, // Sets player A to start the game
                           var statusLine: GameStatus = WRONG_INPUT) extends GameEngineInterface {

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

  def setFigure(figureName: String, row: Int, col: Int): Unit = {
    val figureType = FigureType.withName(figureName)
    val player = if (activePlayer == PLAYER_A) playerA else playerB

    if (player.figureSet.getFigureCount(figureType) != 0) {
      if (this.grid.field(row, col).fieldType == player.fieldType) {
        val figure = createFigure(figureType, player)
        this.grid = this.grid.assignField(row, col, figure)
        activePlayer = if (activePlayer == PLAYER_A) PLAYER_B else PLAYER_A
      } else {
        statusLine = WRONG_SIDE
      }
    } else {
      statusLine = NO_FIGURES_LEFT
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

  def gridToString: String = grid.toString

  def getGameState: String = gameState.toString
}
