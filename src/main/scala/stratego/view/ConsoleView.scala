package stratego.view

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.{AttackEvent, FigureSetEvent, GameEngineInterface, GameEngineProxy, GameQuitEvent, GameStartedEvent, GameState, Init, InvalidMoveEvent, MoveFigureEvent, WinnerEvent}
import stratego.model.gridComponent.FigureType

import scala.swing.Reactor

// TODO: View currently operates on gameEngineProxy which is always on previous state on an event!!!
//TODO: Pass state/state changes as a function parameter instead of a mutable object reference
class ConsoleView(gameEngine: GameEngineInterface) extends LazyLogging with Reactor {
  listenTo(gameEngine)

  reactions += {
    case event: Init => printMenu
    case event: GameStartedEvent => printGameStart
    case event: FigureSetEvent => printFigureSetResult
    case event: MoveFigureEvent => printMoveResult
    case event: InvalidMoveEvent => printInvalidMoveResult
    case event: AttackEvent => printAttackResult
    case event: WinnerEvent =>
    case event: GameQuitEvent => printGameQuit
  }

  def printMenu: Unit = {
    logger.info("\nWelcome to Stratego\n\nOptions:"
      + "\n\"n\": Start a new game"
      + "\n\"q\": Quit the game")
  }

  def printGameStart: Unit = {
    logger.info("New game started:\n")
    printGrid
    logger.info("Player " + gameEngine.getActivePlayer.toString + " starts")
    printFigureSet
    logger.info("\nOptions:"
      + "\n\"s [n] [a,b]\": Set a figure[n] to field[a,b]")
  }

  def printFigureSetResult: Unit = {
    gameEngine.getGameState match {
      case GameState.FIGHT => {
        logger.info("All figures set!\nMatch beginns...\n\n"
          + "Options:"
          + "\n\"m [a,y] [x,y]\": Move figure from field [a,b] to field [x,y]")
        printGrid
      }
      case GameState.SET_FIGURES => {
        logger.info(gameEngine.getStatusLine.toString)
        logger.info("Active player: " + gameEngine.getActivePlayer)
        printFigureSet
        printGrid
      }
    }
  }

  def printMoveResult: Unit = {
    logger.info(gameEngine.getStatusLine.toString)
  }

  def printInvalidMoveResult: Unit = {
    logger.info(gameEngine.getStatusLine.toString)
  }

  def printAttackResult: Unit = {
    logger.info(gameEngine.getStatusLine.toString)
  }

  def printGameState: Unit = {
    logger.info("Current game state: " + gameEngine.getGameState)
  }

  def printGameStatus: Unit = {
    logger.info("Current game status: " + gameEngine.getStatusLine)
  }

  def printActivePlayer: Unit = {
    logger.info("Active player: " + gameEngine)
  }

  def printFigureSet: Unit = {
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
    logger.info("\n Plus Coordinates - Input example: B,3")
  }

  def printGrid: Unit = {
    logger.info(gameEngine.gridToString)
  }

  def printGameQuit: Unit = {
    logger.info("Quitting...")
  }
}
