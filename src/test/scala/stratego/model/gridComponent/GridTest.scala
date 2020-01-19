package stratego.model.gridComponent

import org.scalatest.{Matchers, WordSpec}
import stratego.model.engineComponent.GameState
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
      "have a String representation for TUI" in {
        val player = Player("name", FieldType.B_SIDE)
        val gameState = GameState.SET_FIGURES
        grid.toStringTUI(gameState, player) should be (
          "\n[  ][  ][  ][  ][  ][  ][  ][  ][  ][  ] 0"
          + "\n[  ][  ][  ][  ][  ][  ][  ][  ][  ][  ] 1"
          + "\n[  ][  ][  ][  ][  ][  ][  ][  ][  ][  ] 2"
          + "\n[  ][  ][  ][  ][  ][  ][  ][  ][  ][  ] 3"
          + "\n[  ][  ][  ][  ][  ][  ][  ][  ][  ][  ] 4"
          + "\n[  ][  ][  ][  ][  ][  ][  ][  ][  ][  ] 5"
          + "\n[  ][  ][  ][  ][  ][  ][  ][  ][  ][  ] 6"
          + "\n[  ][  ][  ][  ][  ][  ][  ][  ][  ][  ] 7"
          + "\n[  ][  ][  ][  ][  ][  ][  ][  ][  ][  ] 8"
          + "\n[  ][  ][  ][  ][  ][  ][  ][  ][  ][  ] 9"
          + "\n- A - B - C - D - E - F - G - H - I - J")
      }
    }
    "When a new grid created" should {
      var grid: GridInterface = Grid()
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
        var grid: GridInterface = Grid()
        grid = grid.createNewGrid()
        val figure = Some(Bomb(Player("name", FieldType.B_SIDE)))
        grid = grid.assignField(Position(1, 9), figure)

        "have the figure set in the given field" in {
          grid.getField(Position(1, 9)).getFigure should be(figure)
        }
      }
      "move a Figure" should {
        var grid: GridInterface = Grid()
        grid = grid.createNewGrid()
        val figure = Some(Bomb(Player("name", FieldType.B_SIDE)))
        val from = Position(1, 9)
        val to = Position(1, 8)
        grid = grid.assignField(from, figure)
        grid = grid.move(from, to)
        "have no figure set in field from" in {
          grid.getField(from).getFigure should be(None)
        }
        "have the figure set in the given field" in {
          grid.getField(to).getFigure should be(figure)
        }
      }
    }
  }
}
