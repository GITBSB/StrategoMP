package stratego.model.engineComponent

import com.google.inject.Inject
import stratego.controller.GameStatus._
import stratego.model.gridComponent.GridInterface
import stratego.model.playerComponent.Player
import scala.swing.Publisher
import scala.swing.event.Event

case object GameStartedEvent extends Event

class GameEngine @Inject()(var grid: GridInterface,
                           var playerA: Player = Player("PlayerA"),
                           var playerB: Player = Player("PlayerB"),
                           var gameStatus: GameStatus = INACTIVE)
  extends Publisher
  //extends GameEngineInterface with LazyLogging TODO: Move IO operations to a higher level component
  {

  def startNewGame(playerA: Player, playerB: Player): Unit = {
    this.gameStatus = NEW_GAME
    this.grid = this.grid.createNewGrid
    this.playerA = playerA
    this.playerB = playerB
    publish(GameStartedEvent)
  }


  def quitGame(): Unit = {
    //publish(new QuitStratego)
  }



  def gridToString: String = grid.toString

  def getGameStatus: String = gameStatus.toString
}
