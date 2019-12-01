package stratego.model.engineComponent

trait GameEngineInterface {
  def startNewGame: Unit
  def gridToString: String
  def getGameStatus: String
  def quitGame:Unit
}

