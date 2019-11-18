package stratego.controller

import scala.swing.Publisher
import scala.swing.event.Event

trait ControllerInterface extends Publisher {

 def createNewGrid: Unit
 def gridToString: String
 def getGameStatus: String

}

class GameChanged extends Event
class ShutdownStratego extends Event