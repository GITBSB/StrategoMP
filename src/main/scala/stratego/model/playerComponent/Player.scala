package stratego.model.playerComponent

import stratego.model.gridComponent.FigureSet
import stratego.model.gridComponent.FieldType.FieldType

case class Player(name: String, figureSet: FigureSet, fieldType: FieldType) {
  override def toString: String = name;
}
