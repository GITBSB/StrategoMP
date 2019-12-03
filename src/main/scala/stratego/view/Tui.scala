package stratego.view

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.GameEngineInterface
import stratego.model.engineComponent.GameEvents.{GameQuitEvent, GameStartedEvent}

import scala.swing.Reactor

class Tui(gameEngine: GameEngineInterface) extends LazyLogging with Reactor {

  //TODO: Check how to access Event
  listenTo(gameEngine)
  reactions += {
    case event: GameStartedEvent=> printTui
   //case event: ShutdownStratego => stopProcessingInput = true
  }

  def printTui: Unit = {
    logger.info("printTui")
  }
}
