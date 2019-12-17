package stratego.model.engineComponent

import stratego.model.gridComponent.{FigureSet, Position}
import stratego.model.gridComponent.FigureType.FigureType

import scala.swing.Publisher
import scala.swing.event.Event

trait GameEngineInterface extends Publisher {
  def startNewGame: Unit
  def gridToString: String
  def getGameState: String
  def quitGame:Unit
  def startBattle: Unit
  def changePlayer:Unit
  def setFigure(figureType: FigureType, position: Position):Unit
  def getFigureSetActivePlayer: FigureSet

}

case class GameChanged() extends Event
case class GameQuit() extends Event

