package stratego.model.gridComponent

import org.scalatest.{Matchers, WordSpec}
import stratego.model.gridComponent.Figure.Bomb
import stratego.model.playerComponent.Player

class GridTest extends WordSpec with Matchers {
  "A Grid" when {
    "new" should {
      val grid = new Grid()
      "have a grid" in {
        grid.size should be (10)
        // all of the fields should be
        grid.getField(Position(0, 0)) shouldBe(Field(FieldType.EMPTY_FIELD, None))
      }
    }
    "new grid created" should {
      var grid: GridInterface = new Grid()
      grid = grid.createNewGrid()
      "have a grid" in {
        grid.size should be (10)
      }
      "have Fields with the correct Fieldtypes with no Figure " in {
        for {
          row <- 0 until 4
          col <- 0 until 10
        } grid.getField(Position(row,col)) shouldBe(Field(FieldType.A_SIDE, None))

        for {
          row <- 6 until 10
          col <- 0 until 10
        } grid.getField(Position(row, col)) shouldBe(Field(FieldType.B_SIDE, None))

        grid.getField(Position(4, 2)) shouldBe(Field(FieldType.NO_FIELD, None))
        grid.getField(Position(4, 3)) shouldBe(Field(FieldType.NO_FIELD, None))
        grid.getField(Position(5, 2)) shouldBe(Field(FieldType.NO_FIELD, None))
        grid.getField(Position(5, 3)) shouldBe(Field(FieldType.NO_FIELD, None))
        grid.getField(Position(4, 6)) shouldBe(Field(FieldType.NO_FIELD, None))
        grid.getField(Position(4, 7)) shouldBe(Field(FieldType.NO_FIELD, None))
        grid.getField(Position(5, 6)) shouldBe(Field(FieldType.NO_FIELD, None))
        grid.getField(Position(5, 7)) shouldBe(Field(FieldType.NO_FIELD, None))
        }
      "set a Figure" should {
        var grid: GridInterface = new Grid()
        grid = grid.createNewGrid()
        val figure = Some(Bomb(Player("name", FieldType.B_SIDE)))
        grid = grid.assignField(Position(1, 9), figure)

        "have the figure set in the given field" in {
          grid.getField(Position(1, 9)).getFigure should be(figure)
        }
      }
    }
  }
}
