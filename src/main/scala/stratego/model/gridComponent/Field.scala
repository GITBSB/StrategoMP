package stratego.model.gridComponent
import scala.stratego.model.gridComponent.FieldType._

case class Field(fieldType: FieldType, figure: Option[Figure]) extends FieldInterface() {
  override def setFigure(figure: Figure): Field = copy(fieldType, Some(figure))

}
