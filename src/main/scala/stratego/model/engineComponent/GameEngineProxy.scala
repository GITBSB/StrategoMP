package stratego.model.engineComponent

import stratego.gameEngine.GameStatus.GameStatus
import stratego.model.engineComponent.GameState.GameState
import stratego.model.gridComponent.FigureType.FigureType
import stratego.model.gridComponent.{Figure, FigureSet, GridInterface, Position}
import stratego.model.playerComponent.Player

import scala.swing.Publisher

class GameEngineProxy(var gameEngine: GameEngineInterface) extends GameEngineInterface {

  //propagate events
  listenTo(gameEngine)
  deafTo(this)
  reactions += {
    case event => publish(event)
  }

  def startNewGame: GameEngineInterface =  {
    gameEngine = gameEngine.startNewGame
    gameEngine
  }

  def gridToString: String = gameEngine.gridToString

  def quitGame: GameEngineInterface = {
    gameEngine = gameEngine.quitGame
    gameEngine
  }

  def startBattle: GameEngineInterface = {
    gameEngine = gameEngine.startBattle
    gameEngine
  }

  def changePlayer: GameEngineInterface = {
    gameEngine = gameEngine.changePlayer
    gameEngine
  }

  def setFigure(figureType: FigureType, position: Position): GameEngineInterface = {
    gameEngine = gameEngine.setFigure(figureType, position)
    gameEngine
  }

  def moveFigure(from: Position, to: Position): GameEngineInterface = {
    gameEngine = gameEngine.moveFigure(from, to)
    gameEngine
  }

  def getFigureSetActivePlayer: FigureSet = gameEngine.getFigureSetActivePlayer

  def getFigure(position: Position): Option[Figure] = gameEngine.getFigure(position)

  def getGrid: GridInterface = gameEngine.getGrid

  def getGameState: GameState = gameEngine.getGameState

  def getActivePlayer: Player = gameEngine.getActivePlayer

  def getWinner: Option[Player] = gameEngine.getWinner

  def getStatusLine: GameStatus = gameEngine.getStatusLine
}
