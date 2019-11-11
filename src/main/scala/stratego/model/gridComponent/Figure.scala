package stratego.model.gridComponent

import stratego.model.playerComponent.Player

abstract class Figure(val name: String, val stringOut: String, val strength: Int, val player: Player) {
  override def toString: String = stringOut
}

object Figure {
  class Scout(player: Player) extends Figure("Scout", "[02]",  2, player)
  class Marshal(player: Player) extends Figure("Marshal", "[10]",  10, player)
  class General(player: Player) extends Figure("General", "[09]",  9, player)
  class Colonel(player: Player) extends Figure("Colonel", "[08]",  8, player)
  class Major(player: Player) extends Figure("Major", "[07]",  7, player)
  class Captain(player: Player) extends Figure("Captain", "[06]",  6, player)
  class Lieutenant(player: Player) extends Figure("Lieutenant", "[05]",  5, player)
  class Sergeant(player: Player) extends Figure("Sergeant", "[04]",  4, player)
  class Miner(player: Player) extends Figure("Miner", "[M3]",  3, player)
  class Flag(player: Player) extends Figure("Flag", "[FL]",  0, player)
  class Spy(player: Player) extends Figure("Spy", "[Sp]",  1, player)
  class Bomb(player: Player) extends Figure("Bomb", "[BO]",  100, player)
  class NoFigure(player: Player) extends Figure("XX", "[  ]",  0, player)

}
