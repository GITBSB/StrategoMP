package stratego.view.tui

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.{AttackEvent, FigureSetEvent, GameQuitEvent, GameStartedEvent, InvalidMoveEvent, MoveFigureEvent, WinnerEvent}

import scala.swing.Reactor

class ConsoleOutput(view: ConsoleView) extends LazyLogging with Reactor {

  reactions += {
    case event: GameStartedEvent => printToConsole(view.gameStartToString(event.gameEngine))
    case event: FigureSetEvent => printToConsole(view.figureSetResultToString(event.gameEngine))
    case event: MoveFigureEvent => printToConsole(view.moveResultToString(event.gameEngine))
    case event: InvalidMoveEvent => printToConsole(view.moveResultToString(event.gameEngine))
    case event: AttackEvent => printToConsole(view.moveResultToString(event.gameEngine))
    case event: WinnerEvent => printToConsole(view.winnerToString(event.gameEngine))
    case event: GameQuitEvent => printToConsole(view.gameQuitToString(event.gameEngine))
  }

  def printToConsole(output: String): Unit = {
    logger.info(output)
  }

  def printInitialMenu: Unit = {
    printToConsole(view.gameMenuToString)
  }
}
