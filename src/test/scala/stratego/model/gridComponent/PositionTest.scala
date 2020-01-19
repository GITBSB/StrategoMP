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
}
