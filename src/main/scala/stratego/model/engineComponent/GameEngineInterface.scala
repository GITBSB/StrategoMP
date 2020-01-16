package stratego.model.engineComponent

import stratego.gameEngine.GameStatus.GameStatus
import stratego.model.engineComponent.GameState.GameState
import stratego.model.gridComponent.{Figure, FigureSet, GridInterface, Position}
import stratego.model.gridComponent.FigureType.FigureType
import stratego.model.playerComponent.Player

import scala.swing.Publisher
import scala.swing.event.Event

trait GameEngineInterface extends Publisher {
  def startNewGame: GameEngineInterface
  def gridToString: String
  def quitGame: GameEngineInterface
  def startBattle: GameEngineInterface
  def changePlayer: GameEngineInterface
  def setFigure(figureType: FigureType, position: Position): GameEngineInterface
  def moveFigure(from: Position, to: Position): GameEngineInterface
  def getFigureSetActivePlayer: FigureSet
  def getFigure(position: Position): Option[Figure]
  def getGrid: GridInterface
  def getGameState: GameState
  def getActivePlayer: Player
  def getWinner: Option[Player]
  def getStatusLine: GameStatus
}

case class GameStartedEvent() extends Event
case class GameQuitEvent() extends Event
case class WinnerEvent() extends Event
case class MoveFigureEvent() extends Event
case class FigureSetEvent() extends Event
case class AttackEvent() extends Event
case class InvalidMoveEvent() extends Event
case class ChangePlayerEvent() extends Event
case class Init() extends Event

case class GameQuit() extends Event
case class GameChanged() extends Event
