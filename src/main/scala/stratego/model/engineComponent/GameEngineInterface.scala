package stratego.model.engineComponent

trait GameEngineInterface {
  def createNewGrid: Unit
  def gridToString: String
  def getGameStatus: String
  def quitGame:Unit
}

