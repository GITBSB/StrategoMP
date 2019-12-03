package stratego.view

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.{GameEngineInterface, GameEvents}
import stratego.model.engineComponent.GameEvents.{GameQuitEvent, GameStartedEvent}

import scala.swing.Reactor

class Tui(gameEngine: GameEngineInterface) extends LazyLogging with Reactor {

  listenTo(gameEngine)
  reactions += {
    case event: GameStartedEvent.type => printTui
   //case event: ShutdownStratego => stopProcessingInput = true
  }

  def printTui: Unit = {
    logger.info(gameEngine.gridToString)
  }
}
