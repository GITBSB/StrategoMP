package stratego.model

import org.scalatest.{Matchers, WordSpec}
import stratego.model.gridComponent.{Field, FieldType, Figure, FigureSet, FigureType}
import stratego.model.gridComponent.Figure.Major
import stratego.model.playerComponent.Player


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
      val field = Field(FieldType.EMPTY_FIELD, Some(Major(Player("test",new FigureSet, FieldType.A_SIDE))))
      "create a new field with the new figure" in {
        field shouldBe a [Field]
        field should not be(field)
        field.figure shouldBe a [Major]
      }
    }
  }
}
