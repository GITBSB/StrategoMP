package stratego.model

import org.scalatest.{Matchers, WordSpec}
import stratego.model.gridComponent.{Field, Figure}
import stratego.model.gridComponent.Figure.NoFigure
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType

class FieldTest extends WordSpec with Matchers  {
  "A Filed" when {
    "new" should {
      val field = Field(FieldType.EMPTY_FIELD, new NoFigure(Player("test")))
      "have a field type" in {
        field.fieldType shouldBe a[FieldType.Value]
      }
      "have a figure" in {
        field.figure shouldBe a[Figure]
      }
    }
  }
}
