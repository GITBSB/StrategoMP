package stratego.gameEngine

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.{GameEngineInterface, GameEngineProxy}
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
        val split = values(2).split(",")
        gameEngine.setFigure(convertInputToFigureType(values(1).toInt), Position(split(1).toInt, split(0).toInt))
      case "m" =>
        //inputCordinates.matches("[A-J],[0-9]")//TODO: First values is currently also an int for grid Position/Field
        val from = values(1).split(",")
        val fromRow = from(0).toInt
        val fromCol = from(1).toInt
        val to = values(2).split(",")
        val toRow = to(0).toInt
        val toCol = to(1).toInt
        gameEngine.moveFigure(Position(fromRow, fromCol),Position(toRow, toCol))
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