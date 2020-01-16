package stratego.gameEngine

object GameStatus extends Enumeration {
    type GameStatus = Value
    val WRONG_INPUT = Value("Falsche Eingabe")
    val NO_FIGURES_LEFT = Value("Nicht genügend Figuren dieser Art")
    val INVALID_POSITION = Value("Figure kann hier nicht plaziert werden")
    val FIGURE_SET = Value("Figur ... wurde erfolgreich gesetzt")
    val IDLE = Value("Idle")
    val NO_VALID_FIGURE = Value("Keine valide Figur für einen Zug gefunden")
    val PLAYERA_WINS = Value("Spieler A gewinnt")
    val PLAYERB_WINS = Value("Spieler B gewinnt")
    val MOVE_FIGURE = Value("Player ... moves figure ... to field ...") //TODO: Pass missing arguments
}
