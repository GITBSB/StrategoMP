package stratego.model.gridComponent


import com.typesafe.scalalogging.LazyLogging
import stratego.gameEngine.GameStatus.GameStatus
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.{FieldType, Matrix}

case class Grid (matrix: Matrix[Field]) extends GridInterface with LazyLogging {
  def this() = this(new Matrix[Field](10, Field(FieldType.EMPTY_FIELD, None)))
  val size: Int = matrix.size

  def createNewGrid(): GridInterface = {
    var grid: GridInterface = new Grid()
    grid = grid.createPlayableGrid()
    grid
  }

  def createPlayableGrid(): GridInterface = {
    var newMatrix = matrix
    for {
      row <- 0 until 4
      col <- 0 until 10
    } newMatrix = newMatrix.replaceField(row, col, Field(FieldType.A_SIDE, None))
    for {
      row <- 6 until 10
      col <- 0 until 10
    } newMatrix = newMatrix.replaceField(row, col, Field(FieldType.B_SIDE, None))

    copy(newMatrix.replaceField(4, 2, Field(FieldType.NO_FIELD, None))
      .replaceField(4, 3, Field(FieldType.NO_FIELD, None))
      .replaceField(5, 2, Field(FieldType.NO_FIELD, None))
      .replaceField(5, 3, Field(FieldType.NO_FIELD, None))
      .replaceField(4, 6, Field(FieldType.NO_FIELD, None))
      .replaceField(4, 7, Field(FieldType.NO_FIELD, None))
      .replaceField(5, 6, Field(FieldType.NO_FIELD, None))
      .replaceField(5, 7, Field(FieldType.NO_FIELD, None)))
  }

  def getField(row: Int, col: Int): FieldInterface = matrix.field(row, col)

  def setFigure(row: Int, col: Int, figure: Option[Figure]): GridInterface = {
    var field = matrix.field(row, col)
    field = field.setFigure(figure)
    copy(matrix.replaceField(row, col, field))
  }

  // private def noFieldAssignment(row: Int, col: Int, grid: GridInterface): GridInterface = {var grid = this; copy(grid.matrix.replaceField(row, col, Field(FieldType.NO_FIELD, None)))}

  def toStringTUI(gameStatus: GameStatus, activePlayer: Player): String = {
    var stringGrid = "\n"
    for {
      row <- 0 until 10
      col <- 0 until 10
    } {
      stringGrid = stringGrid + getField(row, col).toStringTUI(gameStatus, activePlayer)
      if (col == 9) stringGrid = stringGrid + " " + row +"\n"
    }
    stringGrid + "- A - B - C - D - E - F - G - H - I - J";
  }

}
