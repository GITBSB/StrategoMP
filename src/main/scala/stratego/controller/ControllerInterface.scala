package stratego.controller

import stratego.model.gridComponent.{GameGrid, GridInterface}

trait ControllerInterface  {
 def createNewGrid: Unit
 def gridToString: String
}
