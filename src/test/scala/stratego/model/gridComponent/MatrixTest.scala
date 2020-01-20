package stratego.model.gridComponent

import org.scalatest.{Matchers, WordSpec}

import scala.stratego.model.gridComponent.Matrix

class MatrixTest extends WordSpec with Matchers{
  "A Matrix" when {
    val size = 10
    val field = Field(FieldType.EMPTY_FIELD, None)
    val matrix = new Matrix[Field](size, field)

    "applied should accept the arguments" in {
      Matrix.apply(Vector.tabulate(size, size) {(row, col) => field}) should be(matrix)
    }
  }

  "A Matrix" when {
    val size = 10
    val field = Field(FieldType.EMPTY_FIELD, None)
    val matrix = new Matrix[Field](size, field)
    "unapplied should have arguments" in {
      Matrix.unapply(matrix).get should be((Vector.tabulate(size, size) {(row, col) => field}))
    }
  }
}
