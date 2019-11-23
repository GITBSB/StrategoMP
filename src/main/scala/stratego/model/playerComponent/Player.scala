package stratego.model.playerComponent

import scala.stratego.model.gridComponent.FieldType.FieldType

case class Player(name: String, fieldType: FieldType) {
  override def toString: String = name
}
