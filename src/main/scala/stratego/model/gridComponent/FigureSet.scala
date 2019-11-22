package stratego.model.gridComponent

import stratego.model.gridComponent.Figure.{Bomb, Captain, Colonel, Flag, General, Lieutenant, Major, Marshal, Miner, Scout, Sergeant, Spy}

case class FigureSet(val figures: Map[FigureType.FigureType, Int]) {
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
    var figureSet = this
    figureSet = copy(figures.updated(figureType, getFigureCount(figureType) + 1))
    figureSet
  }

  def deleteFromFigure(figureType: FigureType.FigureType): FigureSet = {
    var figureSet = this
    figureSet = copy(figures.updated(figureType, getFigureCount(figureType) - 1))
    figureSet
  }

  // TODO better?
  private val figuresL = Map(
    FigureType.BOMB -> List(Bomb, Bomb, Bomb, Bomb, Bomb, Bomb),
    FigureType.MARSHAL -> List(Marshal),
    FigureType.GENERAL -> List(General),
    FigureType.COLONEL -> List(Colonel, Colonel, Colonel),
    FigureType.MAJOR -> List(Major, Major, Major),
    FigureType.CAPTAIN -> List(Captain, Captain, Captain, Captain),
    FigureType.LIEUTENANT -> List(Lieutenant, Lieutenant, Lieutenant, Lieutenant),
    FigureType.SERGEANT -> List(Sergeant, Sergeant, Sergeant, Sergeant),
    FigureType.MINER -> List(Miner, Miner, Miner, Miner, Miner),
    FigureType.SCOUT -> List(Scout, Scout, Scout, Scout, Scout, Scout, Scout, Scout),
    FigureType.SPY -> List(Spy),
    FigureType.FLAG -> List(Flag),
  )
}
