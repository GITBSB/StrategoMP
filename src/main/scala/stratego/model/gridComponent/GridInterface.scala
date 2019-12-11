package stratego.model.gridComponent

import stratego.gameEngine.GameStatus.GameStatus
import stratego.model.playerComponent.Player

trait GridInterface {

  def createNewGrid(): GridInterface
  def createPlayableGrid(): GridInterface
  def size(): Int
  def setFigure(row: Int, col: Int, figure: Option[Figure]): GridInterface
  def getField(row: Int, col: Int): FieldInterface
  def toStringTUI(gameStatus: GameStatus, activePlayer: Player): String

}
