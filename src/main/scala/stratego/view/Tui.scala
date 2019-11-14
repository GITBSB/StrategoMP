package stratego.view

import java.io.BufferedReader

import stratego.controller.ControllerInterface
import com.typesafe.scalalogging.{LazyLogging, Logger}

class Tui(controller: ControllerInterface) extends LazyLogging {
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

  def printTui: Unit =  {
    logger.info("")
  }

  def processInputLine(input: String): Unit = {
    input match {
      case "n" => controller.createNewGrid
    }
  }
}
