package stratego.view

import com.typesafe.scalalogging.LazyLogging

import scala.swing.Reactor

class Tui() extends LazyLogging with Reactor {

  //TODO: Check how to pass gameState from Publisher to Reactor
  reactions += {
    case event: GameChanged => printTui
   //case event: ShutdownStratego => stopProcessingInput = true
  }

  def printTui: Unit = {
    logger.info("printTui")
  }
}
