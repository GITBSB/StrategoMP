package stratego.view

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.{GameChanged, ShutdownStratego}

import scala.swing.Reactor

class Tui() extends LazyLogging with Reactor {

  //TODO: Check how to pass gameState from Publisher to Reactor
  reactions += { //Model Events
    case event: GameChanged => printTui
  }

  def printTui: Unit = {
    logger.info("printTui")
    //logger.info(controller.gridToString)
    //logger.info(controller.getGameStatus)
  }
}
