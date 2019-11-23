package stratego.model

import org.scalatest.{Matchers, WordSpec}
import stratego.model.gridComponent.Figure.Major
import stratego.model.gridComponent.{Field, Figure}
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType

class FieldTest extends WordSpec with Matchers  {
  "A Filed" when {
    "new" should {
      val field = Field(FieldType.EMPTY_FIELD, None)
      "have a field type" in {
        field.fieldType shouldBe a[FieldType.Value]
      }
      "have a figure" in {
        field.figure shouldBe a[Figure]
      }
    }
    "set with a figure" should {
      val field = Field(FieldType.EMPTY_FIELD, None)
      val withFigure = field.setFigure(new Some(Major(Player("test", FieldType.B_SIDE))))
      "create a new field with the new figure" in {
        withFigure shouldBe a [Field]
        withFigure should not be(field)
        withFigure.figure shouldBe a [Major]
      }
    }
  }
}
