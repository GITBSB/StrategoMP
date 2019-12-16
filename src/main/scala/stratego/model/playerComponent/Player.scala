package stratego.model.playerComponent

import stratego.model.gridComponent.FigureSet
import stratego.model.gridComponent.FieldType.FieldType
import stratego.model.playerComponent.PlayerType.PlayerType

case class Player(name: String, figureSet: FigureSet, playerType: PlayerType,  fieldType: FieldType) { //TODO: Remove FieldType from Player - Player does not have to know about field details
  override def toString: String = name;
}
