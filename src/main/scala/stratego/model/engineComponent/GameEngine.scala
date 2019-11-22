package stratego.model.engineComponent

import com.google.inject.Inject
import com.typesafe.scalalogging.LazyLogging
import stratego.controller.GameStatus._
import stratego.model.gridComponent.{FigureSet, GridInterface}
import stratego.model.playerComponent.Player

class GameEngine @Inject()(var grid: GridInterface) extends GameEngineInterface with LazyLogging {
  val playerA: Player = Player("PlayerA", new FigureSet())
  val playerB: Player = Player("PlayerB", new FigureSet())

  var gameStatus: GameStatus = INACTIVE

  def createNewGrid: Unit = {
    grid = grid.createNewGrid()
    gameStatus = NEW_GAME

    publish(new GameChanged)

  }

  def quitGame():Unit = {
    publish(new GameQuit)
  }



  def gridToString: String = grid.toString

  def getGameStatus: String = gameStatus.toString

}
