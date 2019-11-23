package stratego.model.gridComponent

import stratego.controller.GameStatus.GameStatus
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType._

case class Field(fieldType: FieldType = EMPTY_FIELD, figure: Option[Figure]) extends FieldInterface() {
  override def setFigure(figure: Option[Figure]): Field = copy(fieldType, figure)

  override def toStringTUI(gameStatus: GameStatus, player: Player): String = {
    if (figure eq None) fieldType.toString
    else if (figure.get.player != player) "[??]"
    else figure.get.toString
  }
  override def getFieldType(): FieldType = fieldType
  override def getFigure(): Option[Figure] = figure
}
