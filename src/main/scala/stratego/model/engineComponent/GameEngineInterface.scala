package stratego.model.engineComponent

import stratego.model.playerComponent.Player
import scala.swing.Publisher

trait GameEngineInterface extends Publisher {
  def startNewGame(playerA: Player, playerB: Player): Unit
  def gridToString: String
  def getGameStatus: String
  def quitGame:Unit
}

