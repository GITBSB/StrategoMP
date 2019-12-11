package stratego.gameEngine

object GameStatus extends Enumeration {

  type GameStatus = Value
  val INACTIVE = Value("")
  val NEW_GAME = Value("New Game created")
  val SET_FIGURES = Value("Set Figures")
  val FIGHT = Value("Fight")
  val END = Value("End of the game")
  val PLAYER_CHANGE = Value("Player change")

}
