package stratego.model.gridComponent

import scala.stratego.model.gridComponent.FieldType._

case class Field(fieldType: FieldType = EMPTY_FIELD, figure: Figure) extends FieldInterface() {

  override def setFigure(figure: Figure): Field = copy(fieldType, figure)

  override def toString(): String = {
    fieldType.toString
  }
}
