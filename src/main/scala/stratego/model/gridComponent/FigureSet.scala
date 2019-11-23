package stratego.model.gridComponent

import stratego.model.gridComponent.Figure._
import stratego.model.playerComponent.Player

case class FigureSet(figures: Map[FigureType.FigureType, List[Figure]], lastFigure: Option[Figure]) {
 def this(player: Player) = this(Map(
   FigureType.BOMB -> List( Bomb(player), Bomb(player), Bomb(player), Bomb(player), Bomb(player), Bomb(player)),
     FigureType.MARSHAL -> List(Marshal(player)),
     FigureType.GENERAL -> List(General(player)),
     FigureType.COLONEL -> List(Colonel(player), Colonel(player), Colonel(player)),
     FigureType.MAJOR -> List(Major(player), Major(player), Major(player)),
     FigureType.CAPTAIN -> List(Captain(player), Captain(player), Captain(player), Captain(player)),
     FigureType.LIEUTENANT -> List(Lieutenant(player), Lieutenant(player), Lieutenant(player), Lieutenant(player)),
     FigureType.SERGEANT -> List(Sergeant(player), Sergeant(player), Sergeant(player), Sergeant(player)),
     FigureType.MINER -> List(Miner(player), Miner(player), Miner(player), Miner(player), Miner(player)),
     FigureType.SCOUT -> List(Scout(player), Scout(player), Scout(player), Scout(player), Scout(player), Scout(player), Scout(player), Scout(player)),
     FigureType.SPY -> List(Spy(player)),
     FigureType.FLAG -> List(Flag(player))
 ), None)

  def removeFigure(figureType: FigureType.FigureType): FigureSet = {
    var figureSet = this
    var figureList = figures.get(figureType).get
    val lastFigure = Some(figureList.head)
    figureList = figureList diff List(lastFigure)
    figureSet = copy(figures.updated(figureType, figureList), lastFigure)
    figureSet
  }

  def addFigure(figure: Figure): FigureSet = {
    var figureSet = this
    val figureList = figure :: figures.get(figure.figureType).get
    figureSet = copy(figures.updated(figure.figureType, figureList), Some(figure))
    figureSet
  }

  def getFigureCount(figureType: FigureType.FigureType): Int = figures.get(figureType).get.size

  def getLastFigure(): Option[Figure] = lastFigure

/* if prev. not working try this option to count
   def this() = this(Map(
    FigureType.BOMB -> 6,
    FigureType.MARSHAL -> 1,
    FigureType.GENERAL -> 1,
    FigureType.COLONEL -> 2,
    FigureType.MAJOR -> 3,
    FigureType.CAPTAIN -> 4,
    FigureType.LIEUTENANT -> 4,
    FigureType.SERGEANT -> 4,
    FigureType.MINER -> 5,
    FigureType.SCOUT -> 8,
    FigureType.SPY -> 1,
    FigureType.FLAG -> 1
  ))
  */

}
