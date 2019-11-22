package stratego.controller

import scala.swing.Publisher
import scala.swing.event.Event

trait ControllerInterface extends Publisher {

 def createNewGrid: Unit
 def gridToString: String
 def getGameStatus: String
 def quitGame():Unit

}


class GameChanged extends Event
class QuitStratego extends Event

