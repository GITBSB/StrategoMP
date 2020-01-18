package stratego.gameEngine

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.{GameEngineInterface}
import stratego.model.gridComponent.{FigureType, Position}

class ConsoleController(var gameEngine: GameEngineInterface) extends LazyLogging {
  def processInputLine(input: String): Boolean = {
    var continueProcessing = true
    val values: Array[String] = input.split(" ")

    values(0) match {
      case "n" =>
        gameEngine.startNewGame
      case "q" =>
        continueProcessing = false
        gameEngine.quitGame
      case "c" =>
        gameEngine.changePlayer
      case "b" =>
        gameEngine.startBattle
      case "s" =>
        //inputCordinates.matches("[A-J],[0-9]")//TODO: First values is currently also an int for grid Position/Field
        if (values.size == 3) {
          val position = values(2).split(",")
          if (position.size == 2) {
            val row = if (position(1).toInt > 9 || position(1).toInt < 0) 0 else position(1).toInt
            val col = if (position(0).toInt > 9 || position(0).toInt < 0) 4 else position(0).toInt
            val figure = if (values(1).toInt > 12 || values(1).toInt < 1) 1 else values(1).toInt
            gameEngine.setFigure(convertInputToFigureType(figure), Position(row, col))
          }
        }
      case "m" =>
        //inputCordinates.matches("[A-J],[0-9]")//TODO: First values is currently also an int for grid Position/Field
        if (values.size == 3) {
          val from = values(1).split(",")
          val to = values(2).split(",")
          if (from.size == 2 && to.size == 2) {
            val fromRow = if (from(1).toInt > 9 || from(1).toInt < 0) 0 else from(1).toInt
            val fromCol = if (from(0).toInt > 9 || from(0).toInt < 0) 4 else from(0).toInt
            val toRow = if (to(1).toInt > 9 || to(1).toInt < 0) 4 else to(1).toInt
            val toCol = if (to(0).toInt > 9 || to(0).toInt < 0) 4 else to(0).toInt
            gameEngine.moveFigure(Position(fromRow, fromCol), Position(toRow, toCol))
          }
        }
      case "d" =>
        gameEngine.setUpDefaultGrid
      case _ =>
        // Handle unknown input
    }
    continueProcessing
  }

  def convertInputToFigureType(number: Int): FigureType.FigureType = {
    number match {
      case 1 => FigureType.BOMB
      case 2 => FigureType.MARSHAL
      case 3 => FigureType.GENERAL
      case 4 => FigureType.COLONEL
      case 5 => FigureType.MAJOR
      case 6 => FigureType.CAPTAIN
      case 7 => FigureType.LIEUTENANT
      case 8 => FigureType.SERGEANT
      case 9 => FigureType.MINER
      case 10 => FigureType.SCOUT
      case 11 => FigureType.SPY
      case 12 => FigureType.FLAG
    }
  }
}