package stratego.model.gridComponent

object FigureType extends Enumeration {
  type FigureType = Value
  val BOMB = Value("Bomb")
  val MARSHAL = Value("Marshal")
  val GENERAL = Value("General")
  val COLONEL = Value("Colonel")
  val MAJOR = Value("Major")
  val CAPTAIN = Value("Captain")
  val LIEUTENANT = Value("Lieutenant")
  val SERGEANT = Value("Sergeant")
  val MINER = Value("Miner")
  val SCOUT = Value("Scout")
  val SPY = Value("Spy")
  val FLAG = Value("Flag")
}