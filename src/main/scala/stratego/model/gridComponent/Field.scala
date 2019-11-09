package stratego.model.gridComponent
import scala.stratego.model.gridComponent.FieldType._

case class Field(fieldType: FieldType) extends FieldInterface() {
  def this(fieldType: FieldType) = this(fieldType)
}
