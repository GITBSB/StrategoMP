package stratego.model.engineComponent

import com.google.inject.Inject
import GameStatus._
import stratego.model.engineComponent.GameEvents.{GameQuitEvent, GameStartedEvent}
import stratego.model.gridComponent.Figure.{Bomb, Captain, Colonel, Flag, Lieutenant, Major, Marshal, Miner, Scout, Sergeant, Spy}
import stratego.model.gridComponent.{Figure, FigureType, GridInterface}
import stratego.model.playerComponent.Player

//TODO: Add return type to methods other than Unit
//TODO: Move IO operations to a higher level component

class GameEngine @Inject()(var grid: GridInterface,
                           var playerA: Player = Player("PlayerA"),
                           var playerB: Player = Player("PlayerB"),
                           var gameStatus: GameStatus = INACTIVE) extends GameEngineInterface {

  val activePlayer: Player = playerA // Sets player A as the initial default active player

  def startNewGame(playerA: Player, playerB: Player): Unit = {
    this.gameStatus = NEW_GAME
    this.grid = this.grid.createNewGrid
    this.playerA = playerA
    this.playerB = playerB
    publish(GameStartedEvent)
  }

  def quitGame(): Unit = { //TODO: Review quit game logic
    this.gameStatus = END
    publish(GameQuitEvent)
  }

  def setFigure(figureName: String, row: Int, col: Int): Unit = {
    val figure: Figure = resolveFigure(figureName)
    //TODO: Check if player is allowed to assign figure to this field
    this.grid = grid.assignField(row, col, figure)
  }

  def resolveFigure(figureName: String): Figure = {
    FigureType.withName(figureName) match { // Throws an exception if string does not match any FigureType!
      case FigureType.SCOUT => Scout(activePlayer)
      case FigureType.MARSHAL => Marshal(activePlayer)
      case FigureType.COLONEL => Colonel(activePlayer)
      case FigureType.MAJOR => Major(activePlayer)
      case FigureType.CAPTAIN => Captain(activePlayer)
      case FigureType.LIEUTENANT => Lieutenant(activePlayer)
      case FigureType.SERGEANT => Sergeant(activePlayer)
      case FigureType.MINER => Miner(activePlayer)
      case FigureType.FLAG => Flag(activePlayer)
      case FigureType.SPY => Spy(activePlayer)
      case FigureType.BOMB => Bomb(activePlayer)
    }
  }

  def gridToString: String = grid.toString

  def getGameStatus: String = gameStatus.toString
}
