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
        stopProcessingInput = true
        gameEngine.quitGame
      case "c" =>
        gameEngine.changePlayer
      case "b" =>
        gameEngine.startBattle
      case "s" =>
        logger.info("\nChose which figure to place on field:\n"
          + "\n1 ->" + FigureType.BOMB + " | " +  gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.BOMB)
          + "\n2 ->" + FigureType.MARSHAL + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.MARSHAL)
          + "\n3 ->" + FigureType.GENERAL + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.GENERAL)
          + "\n4 ->" + FigureType.COLONEL + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.COLONEL)
          + "\n5 ->" + FigureType.MAJOR + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.MAJOR)
          + "\n6 ->" + FigureType.CAPTAIN + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.CAPTAIN)
          + "\n7 ->" + FigureType.LIEUTENANT + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.LIEUTENANT)
          + "\n8 ->" + FigureType.SERGEANT + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.SERGEANT)
          + "\n9 ->" + FigureType.MINER + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.MINER)
          + "\n10 ->" + FigureType.SCOUT + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.SCOUT)
          + "\n11 ->" + FigureType.SPY + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.SPY)
          + "\n12 ->" + FigureType.FLAG + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.FLAG))
        val inputFigure = scala.io.StdIn.readInt()
        var boolReadLine = true
        var inputCo = "None"
        while(boolReadLine) {
          logger.info("\nCoordinates - Input example: B,3")
          inputCo = scala.io.StdIn.readLine()
          if(inputCo.matches("[A-J],[0-9]")) boolReadLine = false
        }
        val split = inputCo.split(",")
        gameEngine.setFigure(convertInputToFigureType(inputFigure), split(1).toInt, split(0).head - 'A')
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
