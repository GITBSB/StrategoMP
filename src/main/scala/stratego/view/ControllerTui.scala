package stratego.view

import java.io.BufferedReader

import stratego.controller.{ControllerInterface, GameChanged, QuitStratego}
import com.typesafe.scalalogging.{LazyLogging, Logger}
import stratego.model.gridComponent.FigureType

import scala.swing.Reactor
import scala.swing.event.Event

class ControllerTui(controller: ControllerInterface) extends Reactor with LazyLogging {
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
      case "n" =>
        controller.createNewGrid
      case "q" =>
        stopProcessingInput = true
        controller.quitGame
      case "s" =>
        logger.info("\nChose which figure to place on field:\n"
          + "\n1 ->" + FigureType.BOMB
          + "\n2 ->" + FigureType.MARSHAL
          + "\n3 ->" + FigureType.GENERAL
          + "\n4 ->" + FigureType.COLONEL
          + "\n5 ->" + FigureType.MAJOR
          + "\n6 ->" + FigureType.CAPTAIN
          + "\n7 ->" + FigureType.LIEUTENANT
          + "\n8 ->" + FigureType.SERGEANT
          + "\n9 ->" + FigureType.MINER
          + "\n10 ->" + FigureType.SCOUT
          + "\n11 ->" + FigureType.SPY
          + "\n12 ->" + FigureType.FLAG)
        val inputFigure = scala.io.StdIn.readLine()
        logger.info("\nCoordinates - Input example: B,3")
        val inputCo = scala.io.StdIn.readLine()
        val split = inputCo.split(",")
    }
  }

  private def letterCode(letter: Char): Int = letter - 'A'

  //TODO: not working
  reactions += {
    case _: GameChanged =>
      logger.info("GameChanged")
      printTui
    case _: QuitStratego =>
      logger.info("QuitStratego")
      stopProcessingInput = true
  }

  def printTui: Unit = {
    logger.info("printTui")
    logger.info(controller.gridToString)
    logger.info(controller.getGameStatus)
  }
}
