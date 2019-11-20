package stratego.model.engineComponent

import com.google.inject.Inject
import com.typesafe.scalalogging.LazyLogging
import stratego.controller.GameStatus._
import stratego.model.gridComponent.GridInterface
import stratego.model.playerComponent.Player

class GameEngine@Inject()(val grid: GridInterface) extends GameEngineInterface with LazyLogging {
  val playerA: Player = Player("PlayerA")
  val playerB: Player = Player("PlayerB")

  var gameStatus: GameStatus = INACTIVE

  def createNewGrid: GridInterface= {
    gameStatus = NEW_GAME
    logger.info("printTui")
    logger.info(gridToString)
    logger.info(getGameStatus)
    publish(new ShutdownStratego)
    publish(new GameChanged)
    grid.createNewGrid()
  }

  def gridToString: String = grid.toString

  def getGameStatus: String = gameStatus.toString
}
