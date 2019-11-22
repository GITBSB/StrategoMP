package stratego.model.playerComponent

import stratego.model.gridComponent.FigureSet

case class Player(name: String) {
  override def toString: String = name;
}
