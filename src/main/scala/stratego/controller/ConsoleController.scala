package stratego.controller


import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.GameEngineInterface
import stratego.model.gridComponent.FigureType

class ConsoleController(gameEngine: GameEngineInterface) extends LazyLogging{
  var stopProcessingInput = false
  def processInputLine(input: String): Boolean = {
    input match {
      case "n" =>
        gameEngine.createNewGrid
      case "q" => stopProcessingInput = true
        gameEngine.quitGame
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
        val inputFigure = scala.io.StdIn.readInt()
        logger.info("\nCoordinates - Input example: B,3")
        val inputCo = scala.io.StdIn.readLine()
        val split = inputCo.split(",")
        gameEngine.setFigure(convertInputToFigureType(inputFigure), 7, 9) //TODO: row, col
    }
    stopProcessingInput
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
