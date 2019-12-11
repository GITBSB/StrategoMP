package stratego.model.gridComponent

import stratego.gameEngine.GameStatus
import stratego.gameEngine.GameStatus.GameStatus
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType._

case class Field(fieldType: FieldType = EMPTY_FIELD, figure: Option[Figure]) extends FieldInterface() {
  override def setFigure(figure: Option[Figure]): Field = copy(fieldType, figure)

  override def toStringTUI(gameStatus: GameStatus, player: Player): String = {
    if (figure eq None) fieldType.toString
    else if(gameStatus == GameStatus.END) figure.get.toString
    else if (figure.get.player != player) "[??]"
    else figure.get.toString
  }
  override def getFieldType(): FieldType = fieldType
  override def getFigure(): Option[Figure] = figure
}
