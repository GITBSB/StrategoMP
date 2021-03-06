package stratego.model.gridComponent

import org.scalatest.{Matchers, WordSpec}
import stratego.model.engineComponent.GameState
import stratego.model.gridComponent.Figure.Major
import stratego.model.playerComponent.Player


class FieldTest extends WordSpec with Matchers{
  "A Field" when {
    "new" should {
      val field = Field(FieldType.EMPTY_FIELD, None)
      "have a field type" in {
        field.fieldType shouldBe a[FieldType.Value]
      }
      "have a figure" in {
        field.figure shouldBe None
      }
    }
    "set with a figure" should {
      val field = Field(FieldType.B_SIDE, None)
      val player = Player("TestPlayer", FieldType.B_SIDE)
      val figure: Option[Figure] = Some(Major(player))
      val fieldWithFigure = field.setFigure(figure)
      "create a new field with the new figure" in {
        fieldWithFigure shouldBe a [Field]
        fieldWithFigure should not be(field)
        fieldWithFigure.getFigure.get shouldBe a [Major]
      }
      "has a FieldType" in {
        fieldWithFigure.getFieldType() should be (FieldType.B_SIDE)
      }
      "has a TUI String representation" in {
        field.toStringTUI(GameState.NEW_GAME, player) should be (FieldType.B_SIDE.toString)
        fieldWithFigure.toStringTUI(GameState.END, player) should be (figure.get.toString)
        fieldWithFigure.toStringTUI(GameState.NEW_GAME, Player("TestPlayerB", FieldType.A_SIDE)) should be ("[??]")
        fieldWithFigure.toStringTUI(GameState.NEW_GAME, player) should be (figure.get.toString)
      }
      "has a GUI String representation" in {
        field.toStringGUI(GameState.NEW_GAME, player) should be ("")
        fieldWithFigure.toStringGUI(GameState.END, player) should be ("Major_TestPlayer")
        fieldWithFigure.toStringGUI(GameState.NEW_GAME, Player("TestPlayerB", FieldType.A_SIDE)) should be ("backside_TestPlayer")
        fieldWithFigure.toStringGUI(GameState.NEW_GAME, player) should be ("Major_TestPlayer")
      }
    }
    "A Field" when {
      val fieldType = FieldType.EMPTY_FIELD
      val field = Field(fieldType, None)
      "applied should accept the arguments" in {
        Field.apply(fieldType, None) should be(field)
      }
    }
    "A Field" when {
      val fieldType = FieldType.EMPTY_FIELD
      val field = Field(fieldType, None)
      "unapplied should have arguments" in {
        Field.unapply(field).get should be((fieldType, None))
      }
    }
  }
}