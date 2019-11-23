package stratego.model

import org.scalatest.{Matchers, WordSpec}
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType

class PlayerTest extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Name", FieldType.B_SIDE)
      "has a name" in {
        player.name should be("Name")
      }
      "has a String representation" in {
        player.toString should be("Name")
      }
    }
  }
}
