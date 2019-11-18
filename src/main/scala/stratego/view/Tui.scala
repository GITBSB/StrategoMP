package stratego.view

import java.io.BufferedReader

import stratego.controller.{ControllerInterface, GameChanged, GameStatus, ShutdownStratego}
import com.typesafe.scalalogging.{LazyLogging, Logger}

import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends LazyLogging with Reactor {
  var stopProcessingInput = false

  def processInput(input: BufferedReader) = {
    while (!stopProcessingInput) {
      if (input.ready()) {
        val line = input.readLine()
        processInputLine(line)
      } else {
        Thread.sleep(200) // don't waste cpu cycles if no input is given
      }
    }
  }

  def processInputLine(input: String): Unit = {
    input match {
      case "n" => controller.createNewGrid
    }
  }

  reactions += {
    case event: GameChanged => printTui
    case event: ShutdownStratego => stopProcessingInput = true
  }

  def printTui: Unit = {
    logger.info("printTui")
    logger.info(controller.gridToString)
    logger.info(controller.getGameStatus)
  }
}
