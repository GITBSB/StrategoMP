package stratego.model.playerComponent

import org.scalatest.{Matchers, WordSpec}

class PlayerTypeTest extends WordSpec with Matchers {
  "A PlayerType" when {
    "Player A" should {
      val playerType = PlayerType.PLAYER_A
      "have a Value name" in {
        playerType.toString should be ("PlayerA")
      }
    }
    "Player B" should {
      val playerType = PlayerType.PLAYER_B
      "have a Value name" in {
        playerType.toString should be ("PlayerB")
      }
    }
    "A PlayerType B" when {
      val playerType = PlayerType.PLAYER_B
      "applied should accept the arguments" in {
        PlayerType.apply(1) should be(playerType)
      }
    }
    "A PlayerType A" when {
      val playerType = PlayerType.PLAYER_A
      "applied should accept the arguments" in {
        PlayerType.apply(0) should be(playerType)
      }
    }
  }
}
