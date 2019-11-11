package stratego.controller

import com.google.inject.Inject
import com.typesafe.scalalogging.LazyLogging
import stratego.controller.GameStatus._
import stratego.model.gridComponent.{GameGrid, GridInterface}

case class Controller @Inject() (var grid: GridInterface) extends ControllerInterface with LazyLogging {
  var gameStatus: GameStatus = INACTIVE

  def createNewGrid: Unit = {
    grid = grid.createNewGameGrid()
    gameStatus = NEW_GAME
  }
  def gridToString: String = grid.toString
}
