package stratego.view

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.{GameChanged, GameEngineInterface}

import scala.swing.Reactor

//TODO: Pass state/state changes as a function parameter instead of a mutable object reference
class Tui(gameEngine: GameEngineInterface) extends LazyLogging with Reactor {
  listenTo(gameEngine)

  reactions += {
    case event: GameChanged => printTui
  }

  def printTui: Unit = {
    logger.info(gameEngine.gridToString)
    logger.info(gameEngine.getGameState)
  }
}
