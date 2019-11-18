package stratego.controller

import com.google.inject.{Guice, Inject, Injector}
import com.typesafe.scalalogging.LazyLogging
import stratego.controller.GameStatus._
import stratego.model.gridComponent.{GridInterface}
import stratego.model.playerComponent.Player

class Controller@Inject()(var grid: GridInterface) extends ControllerInterface with LazyLogging  {
  val playerA: Player = Player("PlayerA")
  val playerB: Player = Player("PlayerB")

  var gameStatus: GameStatus = INACTIVE

  def createNewGrid: Unit = {
    grid = grid.createNewGrid()
    gameStatus = NEW_GAME
    logger.info("printTui")
    logger.info(gridToString)
    logger.info(getGameStatus)
    publish(new ShutdownStratego)
    publish(new GameChanged)
  }

  def gridToString: String = grid.toString

  def getGameStatus: String = gameStatus.toString
}
