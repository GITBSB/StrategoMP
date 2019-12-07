package stratego.model.gridComponent

case class FigureSet(figures: Map[FigureType.FigureType, Int]) {
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

  def getFigureCount(figureType: FigureType.FigureType): Int= {
    figures.getOrElse(figureType, 0)
  }

  def addToFigure(figureType: FigureType.FigureType): FigureSet = {
    copy(figures.updated(figureType, getFigureCount(figureType) + 1))
  }

  def deleteFromFigure(figureType: FigureType.FigureType): FigureSet = {
    copy(figures.updated(figureType, getFigureCount(figureType) - 1))
  }

  def getFigureCount(): Int = {
   for (k,v <- figures) {v}
  }
}
