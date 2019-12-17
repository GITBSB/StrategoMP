package stratego.model.gridComponent

import scala.stratego.model.gridComponent.Matrix
import scala.math.sqrt

case class Grid (matrix: Matrix[Field]) extends GridInterface {
  def this() = this(new Matrix[Field](10, Field(FieldType.EMPTY_FIELD, None)))

  val size: Int = matrix.size
  val sizeRowCol: Int = sqrt(size).toInt
  def field(position: Position): Field = matrix.field(position.row, position.col)

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

  def assignField(position: Position, figure: Option[Figure]): GridInterface= {
    val field = this.matrix.field(position.row, position.col)
    copy(this.matrix.replaceField(position.row, position.col, field.copy(figure = figure)))
  }

  def move(from: Position, to: Position): GridInterface = {
    val figure = field(from).figure
    assignField(from, None).assignField(to, figure)
  }

  override def toString: String = {
    var stringGrid = "\n"
    for {
      row <- 0 until 10
      col <- 0 until 10
    } {
      stringGrid += field(Position(row, col)).toString
      if (col == 9) stringGrid += " " + row +"\n"
    }
    stringGrid + "- A - B - C - D - E - F - G - H - I - J";
  }

  def createNewGrid(): GridInterface = {
    new Grid().createPlayableGrid()
  }
}
