package stratego.view

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent._
import stratego.model.gridComponent.FigureType

import scala.swing.Reactor

class ConsoleView extends LazyLogging with Reactor {

  reactions += {
    case event: Init => printMenu
    case event: GameStartedEvent => printGameStart(event.gameEngine)
    case event: FigureSetEvent => printFigureSetResult(event.gameEngine)
    case event: MoveFigureEvent => printMoveResult(event.gameEngine)
    case event: InvalidMoveEvent => printMoveResult(event.gameEngine)
    case event: AttackEvent => printMoveResult(event.gameEngine)
    case event: WinnerEvent => printWinner(event.gameEngine)
    case event: GameQuitEvent => printGameQuit(event.gameEngine)
  }

  def printMenu: Unit = {
    logger.info("\nWelcome to Stratego\n\nOptions:"
      + "\n\"n\": Start a new game"
      + "\n\"d\": Start game with default setup"
      + "\n\"q\": Quit the game" )
  }

  def printGameStart(gameEngine: GameEngineInterface): Unit = {
    logger.info("New game started:\n")
    printGrid(gameEngine)
    logger.info("Player " + gameEngine.getActivePlayer.toString + " starts")
    printSetFigureSelection(gameEngine)
  }

  def printFigureSetResult(gameEngine: GameEngineInterface): Unit = {
    gameEngine.getGameState match {
      case GameState.FIGHT => {
        printActivePlayer(gameEngine)
        printGrid(gameEngine)
        logger.info("All figures set!\nMatch beginns...\n\n"
          + "Options:"
          + "\n\"m [a,y] [x,y]\": Move figure from field [a,b] to field [x,y]")
      }
      case GameState.SET_FIGURES => {
        printGameStatus(gameEngine)
        logger.info("Active player: " + gameEngine.getActivePlayer)
        printGrid(gameEngine)
        printSetFigureSelection(gameEngine)
      }
    }
  }

  def printMoveResult(gameEngine: GameEngineInterface): Unit = {
    printGameStatus(gameEngine)
    printActivePlayer(gameEngine)
    printGrid(gameEngine)
    logger.info("Options:"
      + "\n\"m [a,y] [x,y]\": Move figure from field [a,b] to field [x,y]")
  }

  def printGameState(gameEngine: GameEngineInterface): Unit = {
    logger.info("Current game state: " + gameEngine.getGameState)
  }

  def printGameStatus(gameEngine: GameEngineInterface): Unit = {
    logger.info("Current game status: " + gameEngine.getStatusLine)
  }

  def printActivePlayer(gameEngine: GameEngineInterface): Unit = {
    logger.info("Active player: " + gameEngine.getActivePlayer)
  }

  def printSetFigureSelection(gameEngine: GameEngineInterface): Unit = {
    logger.info(
      "\n\"s [n] [a,b]\": Set a figure[n] to field[a,b]"
      + "\nFigure Selection:"
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
      + "\n12 ->" + FigureType.FLAG + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.FLAG)
      + "\nExample: 's 2 B,3'")

  }

  def printGrid(gameEngine: GameEngineInterface): Unit = {
    logger.info(gameEngine.gridToString)
  }

  def printWinner(gameEngine: GameEngineInterface): Unit = {
    logger.info("Player " + gameEngine.getWinner.toString + " wins the game!")
  }

  def printGameQuit(gameEngine: GameEngineInterface): Unit = {
    logger.info("Quitting...")
  }
}
