package stratego.model.engineComponent

import stratego.model.gridComponent.GridInterface

import scala.swing.Publisher
import scala.swing.event.Event

trait GameEngineInterface extends Publisher {

 def createNewGrid: GridInterface
 def gridToString: String
 def getGameStatus: String

}

class GameChanged extends Event
class ShutdownStratego extends Event