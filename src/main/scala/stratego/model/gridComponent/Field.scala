package stratego.model.gridComponent

import stratego.model.gridComponent.FieldType._

case class Field(fieldType: FieldType = EMPTY_FIELD, figure: Option[Figure]) {
  override def toString(): String = { //TODO: Add figure string?
    fieldType.toString
  }
}
