package stratego.model.engineComponent

import com.google.inject.Inject
import com.typesafe.scalalogging.LazyLogging
import stratego.controller.GameStatus._
import stratego.model.gridComponent.GridInterface
import stratego.model.playerComponent.Player

class GameEngine @Inject()(var grid: GridInterface) extends GameEngineInterface with LazyLogging  {
  val playerA: Player = Player("PlayerA")
  val playerB: Player = Player("PlayerB")

  var gameStatus: GameStatus = INACTIVE

  def createNewGrid: Unit = {
    grid = grid.createNewGrid()
    gameStatus = NEW_GAME

    printTui
    // TODO: Not working publish
    //publish(new GameChanged)
  }

  def quitGame():Unit = {
    //publish(new QuitStratego)
  }



  def gridToString: String = grid.toString

  def getGameStatus: String = gameStatus.toString

  def printTui: Unit = {
    logger.info("printTui")
    logger.info(gridToString)
    logger.info(getGameStatus)
  }
}
