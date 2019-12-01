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
    var newMatrix = matrix
    for {
      row <- 0 until 4
      col <- 0 until 10
    } newMatrix = newMatrix.replaceField(row, col, Field(FieldType.A_SIDE, NoFigure(Player("Dummy"))))

    for {
      row <- 6 until 10
      col <- 0 until 10
    } newMatrix = newMatrix.replaceField(row, col, Field(FieldType.B_SIDE, NoFigure(Player("Dummy"))))

    copy(newMatrix.replaceField(4, 2, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy"))))
      .replaceField(4, 3, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy"))))
      .replaceField(5, 2, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy"))))
      .replaceField(5, 3, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy"))))
      .replaceField(4, 6, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy"))))
      .replaceField(4, 7, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy"))))
      .replaceField(5, 6, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy"))))
      .replaceField(5, 7, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy")))))
  }

  private def fieldAssignment(row: Int, col: Int, grid: GridInterface) = {
    copy(this.matrix.replaceField(4, 2, Field(FieldType.NO_FIELD, NoFigure(Player("Dummy")))));
   }

  override def toString: String = {
    var stringGrid = "\n"
    for {
      row <- 0 until 10
      col <- 0 until 10
    } {
      stringGrid += field(row, col).toString
      if (col == 9) stringGrid += " " + row +"\n"
    }
    stringGrid + "- A - B - C - D - E - F - G - H - I - J";
  }

  def createNewGrid(): GridInterface = {
    new Grid().createPlayableGrid()
  }
}
