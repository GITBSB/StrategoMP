package stratego.view

import com.typesafe.scalalogging.LazyLogging
import stratego.model.engineComponent.{GameChanged, GameEngineInterface}

import scala.swing.Reactor

class Tui(gameEngine: GameEngineInterface) extends LazyLogging with Reactor {
  listenTo(gameEngine)

  reactions += {
    case event: GameChanged => printTui
  }

  def printTui: Unit = {
    logger.info(gameEngine.gridToString)
    logger.info(gameEngine.getGameStatus)
  }
}
