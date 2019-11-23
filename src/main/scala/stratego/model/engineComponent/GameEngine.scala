package stratego.model.engineComponent

import com.google.inject.Inject
import com.typesafe.scalalogging.LazyLogging
import stratego.controller.GameStatus._
import stratego.model.gridComponent.FigureType.FigureType
import stratego.model.gridComponent.{FigureSet, GridInterface}
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType

class GameEngine @Inject()(var grid: GridInterface) extends GameEngineInterface with LazyLogging {
  val playerA: Player = Player("PlayerA", FieldType.A_SIDE)
  val playerB: Player = Player("PlayerB", FieldType.B_SIDE)
  private var figureSet = Map(playerA -> new FigureSet(playerA),
                      playerB -> new FigureSet(playerB))

  var activePlayer = playerA
  var gameStatus: GameStatus = INACTIVE

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
      grid = grid.setFigure(row, col, figureSet(activePlayer).getLastFigure())

    }else {
      // TODO statusline
      logger.info("Not enough figures of " + figureType + " left to place!")
    }
    gameStatus = SET_FIGURES
    publish(new GameChanged)
  }


  def changePlayer():Unit = if(activePlayer == playerA) activePlayer = playerB else activePlayer = playerA // evtl. wegen Änderungen auf playerturn playerA = playerturn else playerB = playerTurn

  def getActivePlayer(): Player = activePlayer

  def gridToString: String = grid.toStringTUI(gameStatus, activePlayer)

  def getGameStatus: String = gameStatus.toString

}
