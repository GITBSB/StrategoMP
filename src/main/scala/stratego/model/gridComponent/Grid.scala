package stratego.model.gridComponent


import stratego.model.gridComponent.Figure.NoFigure
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.{FieldType, Matrix}
import scala.math.sqrt
import com.typesafe.scalalogging.LazyLogging

case class Grid (matrix: Matrix[Field]) extends GridInterface with LazyLogging {
  def this() = this(new Matrix[Field](10, Field(FieldType.EMPTY_FIELD, NoFigure(Player("Dummy")))))

  val size: Int = matrix.size
  val sizeRowCol: Int = sqrt(size).toInt
  def field(row: Int, col: Int): Field = matrix.field(row, col)

  def createPlayableGrid(): GridInterface = {
  var grid = this
  for {
    row <- 0 until 4
    col <- 0 until 10
  } grid = copy(grid.matrix.replaceField(row, col, Field(FieldType.A_SIDE, NoFigure(Player("Dummy")))))

  for {
    row <- 6 until 10
    col <- 0 until 10
  } grid = copy(grid.matrix.replaceField(row, col, Field(FieldType.B_SIDE, NoFigure(Player("Dummy")))))

  grid = copy(grid.matrix.replaceField(4, 2, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy")))))
  grid = copy(grid.matrix.replaceField(4, 3, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy")))))
  grid = copy(grid.matrix.replaceField(5, 2, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy")))))
  grid = copy(grid.matrix.replaceField(5, 3, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy")))))
  grid = copy(grid.matrix.replaceField(4, 6, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy")))))
  grid = copy(grid.matrix.replaceField(4, 7, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy")))))
  grid = copy(grid.matrix.replaceField(5, 6, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy")))))
  grid = copy(grid.matrix.replaceField(5, 7, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy")))))
  grid
}

  private def fieldAssignment(row: Int, col: Int, grid: GridInterface) = {var grid = this; grid = copy(grid.matrix.replaceField(4, 2, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy"))))); grid}

  override def toString: String = {
  logger.info("gridtostring")
  var stringGrid = "\n"
  for {
    row <- 0 until 10
    col <- 0 until 10
  } {
    stringGrid = stringGrid + field(row, col).toString
    if (col == 9) stringGrid = stringGrid + " " + row +"\n"
  }
  stringGrid += "- A - B - C - D - E - F - G - H - I - J";
  stringGrid
  }

  def createNewGrid(): GridInterface = {
  var grid: GridInterface = new Grid()
  grid = grid.createPlayableGrid()
  grid
  }
}
