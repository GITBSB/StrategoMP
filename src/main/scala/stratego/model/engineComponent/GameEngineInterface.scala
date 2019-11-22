package stratego.model.engineComponent

import scala.swing.Publisher
import scala.swing.event.Event

trait GameEngineInterface extends Publisher {
  def createNewGrid: Unit
  def gridToString: String
  def getGameStatus: String
  def quitGame:Unit


}

case class GameChanged() extends Event
case class GameQuit() extends Event

