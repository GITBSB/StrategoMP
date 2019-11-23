package stratego.model.playerComponent

import org.scalatest.{Matchers, WordSpec}

import scala.stratego.model.gridComponent.FieldType

class PlayerTest extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Name", FieldType.B_SIDE)
      "has a name" in {
        player.name should be("Name")
      }
      "has a FieldType" in {
        player.fieldType should be (FieldType.B_SIDE)
      }
      "has a String representation" in {
        player.toString should be("Name")
      }
    }
  }
}