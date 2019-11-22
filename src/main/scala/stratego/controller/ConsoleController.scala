package stratego.controller

import stratego.model.engineComponent.GameEngine
import stratego.model.gridComponent.GridInterface

class ConsoleController(gameEngine: GameEngine) {

  def processInputLine(input: String): Boolean = {
    input match {
      case "n" => gameEngine.createNewGrid; false
    }
  }
}
