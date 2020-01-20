package stratego.model.playerComponent

import org.scalatest.{Matchers, WordSpec}
import stratego.model.gridComponent.FieldType

class PlayerTest extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Name", FieldType.B_SIDE)
      "has a name" in {
        player.name should be ("Name")
      }
      "has a FieldType" in {
        player.fieldType should be (FieldType.B_SIDE)
      }
      "has a String representation" in {
        player.toString should be ("Name")
      }
    }
    "A Player" when {
      val name = "Name"
      val fieldType = FieldType.A_SIDE
      val player = Player(name, fieldType)
      "applied should accept the arguments" in {
        Player.apply(name, fieldType) should be(player)
      }
    }
    "A Player" when {
      val name = "Name"
      val fieldType = FieldType.A_SIDE
      val player = Player(name, fieldType)
      "unapplied should have arguments" in {
        Player.unapply(player).get should be((name, fieldType))
      }
    }
  }
}