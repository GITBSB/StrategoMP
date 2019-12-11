package stratego.model.gridComponent

import org.scalatest.{Matchers, WordSpec}
import stratego.gameEngine.GameStatus
import stratego.model.gridComponent.Figure.Major
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType

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
      "has a String representation" in {
        field.toStringTUI(GameStatus.NEW_GAME, player) should be (FieldType.B_SIDE.toString)
        fieldWithFigure.toStringTUI(GameStatus.END, player) should be (figure.get.toString)
        fieldWithFigure.toStringTUI(GameStatus.NEW_GAME, Player("TestPlayerB", FieldType.A_SIDE)) should be ("[??]")
        fieldWithFigure.toStringTUI(GameStatus.NEW_GAME, player) should be (figure.get.toString)
      }
    }
  }
}