package stratego.model.gridComponent

import stratego.controller.GameStatus.GameStatus
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType.FieldType

trait FieldInterface {

  def setFigure(figure: Option[Figure]): Field
  def getFieldType(): FieldType
  def getFigure(): Option[Figure]
  def toStringTUI(gameStatus: GameStatus, activePlayer: Player): String
}
