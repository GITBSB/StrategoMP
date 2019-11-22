package stratego.model.playerComponent

import stratego.model.gridComponent.FigureSet

case class Player(name: String, figureSet: FigureSet) {
  override def toString: String = name;
}
