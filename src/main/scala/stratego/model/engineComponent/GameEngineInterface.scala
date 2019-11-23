package stratego.model.engineComponent

import stratego.model.gridComponent.FigureSet
import stratego.model.gridComponent.FigureType.FigureType

import scala.swing.Publisher
import scala.swing.event.Event

trait GameEngineInterface extends Publisher {
  def createNewGrid: Unit
  def gridToString: String
  def getGameStatus: String
  def quitGame:Unit
  def startBattle: Unit
  def changePlayer:Unit
  def setFigure(figureType: FigureType, row: Int, col: Int):Unit
  def getFigureSetActivePlayer: FigureSet

}

case class GameChanged() extends Event
case class GameQuit() extends Event

