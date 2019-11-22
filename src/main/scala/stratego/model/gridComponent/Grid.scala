package stratego.model.gridComponent


import com.typesafe.scalalogging.LazyLogging

import scala.math.sqrt
import scala.stratego.model.gridComponent.{FieldType, Matrix}

case class Grid (matrix: Matrix[Field]) extends GridInterface with LazyLogging {
  def this() = this(new Matrix[Field](10, Field(FieldType.EMPTY_FIELD, None)))

  val size: Int = matrix.size
  val sizeRowCol: Int = sqrt(size).toInt
  def field(row: Int, col: Int): Field = matrix.field(row, col)

  def createPlayableGrid(): GridInterface = {
    var grid = this
    for {
      row <- 0 until 4
      col <- 0 until 10
    } grid = copy(grid.matrix.replaceField(row, col, Field(FieldType.A_SIDE, None)))

    for {
      row <- 6 until 10
      col <- 0 until 10
    } grid = copy(grid.matrix.replaceField(row, col, Field(FieldType.B_SIDE, None)))

    grid = copy(grid.matrix.replaceField(4, 2, Field(FieldType.NO_FIELD, None)))
    grid = copy(grid.matrix.replaceField(4, 3, Field(FieldType.NO_FIELD, None)))
    grid = copy(grid.matrix.replaceField(5, 2, Field(FieldType.NO_FIELD, None)))
    grid = copy(grid.matrix.replaceField(5, 3, Field(FieldType.NO_FIELD, None)))
    grid = copy(grid.matrix.replaceField(4, 6, Field(FieldType.NO_FIELD, None)))
    grid = copy(grid.matrix.replaceField(4, 7, Field(FieldType.NO_FIELD, None)))
    grid = copy(grid.matrix.replaceField(5, 6, Field(FieldType.NO_FIELD, None)))
    copy(grid.matrix.replaceField(5, 7, Field(FieldType.NO_FIELD, None)))
  }


  private def fieldAssignment(row: Int, col: Int, grid: GridInterface) = {var grid = this; grid = copy(grid.matrix.replaceField(4, 2, Field(FieldType.NO_FIELD, None))); grid}

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
