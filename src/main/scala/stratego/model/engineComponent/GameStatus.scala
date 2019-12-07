package stratego.model.engineComponent

object GameStatus extends Enumeration {
    type GameStatus = Value
    val WRONG_INPUT = Value("Falsche Eingabe")
    val NO_FIGURES_LEFT = Value("Nicht genügend Figuren dieser Art")
    val WRONG_SIDE = Value("Falsche Seite")
}
