package stratego.model

import org.scalatest.{Matchers, WordSpec}
import stratego.model.gridComponent.FigureSet
import stratego.model.playerComponent.Player

class PlayerTest extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Name", new FigureSet())
      "has a name" in {
        player.name should be("Name")
      }
      "has a String representation" in {
        player.toString should be("Name")
      }
    }
  }
}
