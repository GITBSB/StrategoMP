package stratego.model.engineComponent

import com.google.inject.Inject
import com.typesafe.scalalogging.LazyLogging
import stratego.gameEngine.GameStatus._
import stratego.model.gridComponent.Figure.{Bomb, Captain, Colonel, Flag, Lieutenant, Major, Marshal, Miner, Scout, Sergeant, Spy}
import stratego.model.gridComponent.FigureType.FigureType
import stratego.model.gridComponent.{Figure, FigureSet, FigureType, GridInterface}
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType

class GameEngine @Inject()(var grid: GridInterface) extends GameEngineInterface with LazyLogging {
  val playerA: Player = Player("PlayerA", FieldType.A_SIDE)
  val playerB: Player = Player("PlayerB", FieldType.B_SIDE)
  var activePlayer = playerA
  var gameStatus: GameStatus = INACTIVE
  private var figureSet = Map(playerA -> new FigureSet(playerA), playerB -> new FigureSet(playerB))

  def createNewGrid: Unit = {
    grid = grid.createNewGrid()
    gameStatus = NEW_GAME
    publish(new GameChanged)
  }

  def quitGame():Unit = {
    publish(new GameQuit)
  }

  def setFigure(figureType: FigureType, row: Int, col: Int):Unit = {
    if(figureSet(activePlayer).getFigureCount(figureType) > 0 && grid.getField(row, col).getFieldType() == activePlayer.fieldType) {
      if(grid.getField(row, col).getFigure() != None) {
        val figureSetTmp = figureSet(activePlayer).addFigure(grid.getField(row, col).getFigure().get)
        figureSet = figureSet.updated(activePlayer, figureSetTmp)
      }
      val figureSetTmp = figureSet(activePlayer).removeFigure(figureType)
      figureSet = figureSet.updated(activePlayer, figureSetTmp)
      figureSet(activePlayer).figures.get(figureType).foreach(println)
      grid = grid.setFigure(row, col, figureSet(activePlayer).getLastFigure())

    }else {
      // TODO statusline
      logger.info("Not enough figures of " + figureType + " left to place!")
    }
    gameStatus = SET_FIGURES
    publish(new GameChanged)
  }

  def startBattle: Unit = {
    gameStatus = FIGHT
    publish(new GameChanged)
  }

  def changePlayer: Unit = {
    if (activePlayer == playerA) activePlayer = playerB else activePlayer = playerA
    publish(new GameChanged)
  }

  def getActivePlayer: Player = activePlayer

  def gridToString: String = grid.toStringTUI(gameStatus, activePlayer)

  def getGameStatus: String = gameStatus.toString

  def getFigureSetActivePlayer: FigureSet = figureSet(activePlayer)

  def resolveFigure(figureName: String): Figure = {
    FigureType.withName(figureName) match { // Throws an exception if string does not match any FigureType!
      case FigureType.SCOUT => Scout(activePlayer)
      case FigureType.MARSHAL => Marshal(activePlayer)
      case FigureType.COLONEL => Colonel(activePlayer)
      case FigureType.MAJOR => Major(activePlayer)
      case FigureType.CAPTAIN => Captain(activePlayer)
      case FigureType.LIEUTENANT => Lieutenant(activePlayer)
      case FigureType.SERGEANT => Sergeant(activePlayer)
      case FigureType.MINER => Miner(activePlayer)
      case FigureType.FLAG => Flag(activePlayer)
      case FigureType.SPY => Spy(activePlayer)
      case FigureType.BOMB => Bomb(activePlayer)
    }
  }
}
