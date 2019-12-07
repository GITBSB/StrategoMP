package stratego.view

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.{GameEngineInterface, GameEvent}
import stratego.model.engineComponent.GameEvent.{GameQuitEvent, GameStartedEvent}
import scala.swing.Reactor

//TODO: Pass state/state changes as a function parameter instead of a mutable object reference
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
