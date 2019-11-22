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
      case "q" =>
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
        val inputFigure = scala.io.StdIn.readLine()
        logger.info("\nCoordinates - Input example: B,3")
        val inputCo = scala.io.StdIn.readLine()
        val split = inputCo.split(",")
    }
    stopProcessingInput
  }
}
