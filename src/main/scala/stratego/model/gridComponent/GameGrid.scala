package stratego.model.gridComponent

import stratego.model
import stratego.model.gridComponent
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.{FieldType, Matrix}
import scala.math.sqrt

case class GameGrid(grid: Matrix[Field]) extends GridInterface{
  def this(size: Int) = this(new Matrix[Field](size, Field(FieldType.EMPTY_FIELD, new Figure.NoFigure(new Player("game")))))

  val size: Int = grid.size
  val sizeRowCol: Int = sqrt(size).toInt
  def field(row: Int, col: Int): Field = grid.field(row, col)

  def createPlayableGrid(): GameGrid = {
    var gameGrid = this
    for {
      row <- 0 until 4
      col <- 0 until 10
    } gameGrid = copy(grid.replaceField(row, col, Field(FieldType.A_SIDE, new Figure.NoFigure(new Player("game")))))
    for {
      row <- 6 until 10
      col <- 0 until 10
    } gameGrid = copy(grid.replaceField(row, col, Field(FieldType.B_SIDE, new Figure.NoFigure(new Player("game")))))

    gameGrid = copy(grid.replaceField(4, 2, Field(FieldType.NO_FIELD, new Figure.NoFigure(new Player("game")))))
    gameGrid = copy(grid.replaceField(4, 3, Field(FieldType.NO_FIELD, new Figure.NoFigure(new Player("game")))))
    gameGrid = copy(grid.replaceField(5, 2, Field(FieldType.NO_FIELD, new Figure.NoFigure(new Player("game")))))
    gameGrid = copy(grid.replaceField(5, 3, Field(FieldType.NO_FIELD, new Figure.NoFigure(new Player("game")))))
    gameGrid = copy(grid.replaceField(4, 6, Field(FieldType.NO_FIELD, new Figure.NoFigure(new Player("game")))))
    gameGrid = copy(grid.replaceField(4, 7, Field(FieldType.NO_FIELD, new Figure.NoFigure(new Player("game")))))
    gameGrid = copy(grid.replaceField(5, 6, Field(FieldType.NO_FIELD, new Figure.NoFigure(new Player("game")))))
    gameGrid = copy(grid.replaceField(5, 7, Field(FieldType.NO_FIELD, new Figure.NoFigure(new Player("game")))))
    gameGrid
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
