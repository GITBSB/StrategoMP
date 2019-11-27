package stratego.model

import org.scalatest.{Matchers, WordSpec}
import stratego.model.playerComponent.Player

class PlayerTest extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Name")
      "has a name" in {
        player.name should be("Name")
      }
      "has a String representation" in {
        player.toString should be("Name")
      }
    }
  }
}
