package stratego.model.gridComponent

import org.scalatest.{Matchers, WordSpec}

class PositionTest extends WordSpec with Matchers {
  "A Position" when {
    "new" should {
      val position = Position(2, 3)
      "have a row" in {
        position.row should be (2)
      }
      "have a column" in {
        position.col should be (3)
      }
    }
  }
  "A Position" when {
    val row = 1
    val col = 2
    val position: Position = Position(row, col)

    "applied should accept the arguments" in {
      Position.apply(row, col) should be(position)
    }
  }
  "A Position" when {
    val row = 1
    val col = 2
    val position: Position = Position(row, col)
    "unapplied should have arguments" in {
      Position.unapply(position).get should be((row, col))
    }
  }
}
