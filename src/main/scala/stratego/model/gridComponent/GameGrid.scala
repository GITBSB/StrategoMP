package stratego.model.gridComponent

import stratego.model.playerComponent.Player
import com.google.inject.Inject
import scala.stratego.model.gridComponent.{FieldType, Matrix}
import scala.math.sqrt

case class GameGrid @Inject() (grid: Matrix[Field]) extends GridInterface{
  def this(size: Int) = this(new Matrix[Field](size, Field(FieldType.EMPTY_FIELD, None)))

  val size: Int = grid.size
  val sizeRowCol: Int = sqrt(size).toInt

  def field(row: Int, col: Int): Field = grid.field(row, col)

  def createPlayableGrid(): GameGrid = {//TODO: GridInterface instead of GameGrid?
    var gameGrid = this
    for {
      row <- 0 until 4
      col <- 0 until 10
    } gameGrid = copy(grid.replaceField(row, col, Field(FieldType.A_SIDE, None)))
    for {
      row <- 6 until 10
      col <- 0 until 10
    } gameGrid = copy(grid.replaceField(row, col, Field(FieldType.B_SIDE, None)))
    gameGrid = copy(grid.replaceField(4, 2, Field(FieldType.NO_FIELD, None)))
    gameGrid = copy(grid.replaceField(4, 3, Field(FieldType.NO_FIELD, None)))
    gameGrid = copy(grid.replaceField(5, 2, Field(FieldType.NO_FIELD, None)))
    gameGrid = copy(grid.replaceField(5, 3, Field(FieldType.NO_FIELD, None)))
    gameGrid = copy(grid.replaceField(4, 6, Field(FieldType.NO_FIELD, None)))
    gameGrid = copy(grid.replaceField(4, 7, Field(FieldType.NO_FIELD, None)))
    gameGrid = copy(grid.replaceField(5, 6, Field(FieldType.NO_FIELD, None)))
    gameGrid = copy(grid.replaceField(5, 7, Field(FieldType.NO_FIELD, None)))
    gameGrid
  }

  def createNewGameGrid(): GridInterface = {
    var grid: GridInterface = new GameGrid(10)
    grid = grid.createPlayableGrid()
    grid
  }

  override def toString: String = {
    var stringGrid = ""
    for {
      row <- 0 until 10
      col <- 0 until 10
    } {
      stringGrid = stringGrid + "[" + field(row, col).toString + "]"
      if (col == 9) stringGrid = stringGrid + " " + row +"\n"
    }
    stringGrid += "\n- A - B - C - D - E - F - G - H - I - J";
    stringGrid
  }
}
