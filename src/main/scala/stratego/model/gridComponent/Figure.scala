package stratego.model.gridComponent

import stratego.model.playerComponent.Player

abstract class Figure(val name: String, val stringOut: String, val strength: Int, val player: Player) {
  override def toString: String = stringOut
}

object Figure {
  case class Scout(override val player: Player) extends Figure(FigureType.SCOUT.toString, "[02]",  2, player)
  case class Marshal(override val player: Player) extends Figure(FigureType.MARSHAL.toString, "[10]",  10, player)
  case class General(override val player: Player) extends Figure(FigureType.GENERAL.toString, "[09]",  9, player)
  case class Colonel(override val player: Player) extends Figure(FigureType.COLONEL.toString, "[08]",  8, player)
  case class Major(override val player: Player) extends Figure(FigureType.MAJOR.toString, "[07]",  7, player)
  case class Captain(override val player: Player) extends Figure(FigureType.CAPTAIN.toString, "[06]",  6, player)
  case class Lieutenant(override val player: Player) extends Figure(FigureType.LIEUTENANT.toString, "[05]",  5, player)
  case class Sergeant(override val player: Player) extends Figure(FigureType.SERGEANT.toString, "[04]",  4, player)
  case class Miner(override val player: Player) extends Figure(FigureType.MINER.toString, "[M3]",  3, player)
  case class Flag(override val player: Player) extends Figure(FigureType.FLAG.toString, "[FL]",  0, player)
  case class Spy(override val player: Player) extends Figure(FigureType.SPY.toString, "[Sp]",  1, player)
  case class Bomb(override val player: Player) extends Figure(FigureType.BOMB.toString, "[BO]",  100, player)
  case class NoFigure(override val player: Player) extends Figure(FigureType.NOFIGURE.toString, "[  ]",  0, player)

}

object FigureType extends Enumeration {
  type FigureType = Value
  val SCOUT = Value("Scout")
  val MARSHAL = Value("Marshal")
  val GENERAL = Value("General")
  val COLONEL = Value("Colonel")
  val MAJOR = Value("Major")
  val CAPTAIN = Value("Captain")
  val LIEUTENANT = Value("Lieutenant")
  val SERGEANT = Value("Sergeant")
  val MINER = Value("Miner")
  val FLAG = Value("Flag")
  val SPY = Value("Spy")
  val BOMB = Value("Bomb")
  val NOFIGURE = Value("Empty")
}



