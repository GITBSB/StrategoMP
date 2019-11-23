package stratego.model.gridComponent

import org.scalatest.{Matchers, WordSpec}
import stratego.model.gridComponent.Figure.{Bomb, Captain, Colonel, Flag, General, Lieutenant, Major, Marshal, Miner, Scout, Sergeant, Spy}
import stratego.model.playerComponent.Player

import scala.stratego.model.gridComponent.FieldType

class FigureSetTest extends WordSpec with Matchers {
  "A FigureSet" when {
    "new" should {
      val player = Player("TestPlayer", FieldType.A_SIDE)
      val figureSet = new FigureSet(player)
      "have a Map with all figures" in {
        figureSet.figures.exists(x => x._1 == FigureType.BOMB  && x._2 ==  List( Bomb(player), Bomb(player), Bomb(player), Bomb(player), Bomb(player), Bomb(player)))
        figureSet.figures.exists(x => x._1 == FigureType.MARSHAL  && x._2 ==  List(Marshal(player)))
        figureSet.figures.exists(x => x._1 ==  FigureType.GENERAL  && x._2 ==  List(General(player)))
        figureSet.figures.exists(x => x._1 == FigureType.COLONEL  && x._2 == List(Colonel(player), Colonel(player), Colonel(player)))
        figureSet.figures.exists(x => x._1 == FigureType.MAJOR  && x._2 ==  List(Major(player), Major(player), Major(player)))
        figureSet.figures.exists(x => x._1 == FigureType.CAPTAIN  && x._2 ==  List(Captain(player), Captain(player), Captain(player), Captain(player)))
        figureSet.figures.exists(x => x._1 == FigureType.LIEUTENANT  && x._2 ==  List(Lieutenant(player), Lieutenant(player), Lieutenant(player), Lieutenant(player)))
        figureSet.figures.exists(x => x._1 == FigureType.SERGEANT  && x._2 ==  List(Sergeant(player), Sergeant(player), Sergeant(player), Sergeant(player)))
        figureSet.figures.exists(x => x._1 == FigureType.MINER  && x._2 ==  List(Miner(player), Miner(player), Miner(player), Miner(player), Miner(player)))
        figureSet.figures.exists(x => x._1 == FigureType.SCOUT  && x._2 ==  List(Scout(player), Scout(player), Scout(player), Scout(player), Scout(player), Scout(player), Scout(player), Scout(player)))
        figureSet.figures.exists(x => x._1 == FigureType.SPY  && x._2 ==  List(Spy(player)))
        figureSet.figures.exists(x => x._1 == FigureType.FLAG  && x._2 ==  List(Flag(player)))
      }
    }
    "remove figure" should {
      val player = Player("TestPlayer", FieldType.A_SIDE)
      var figureSet = new FigureSet(player)
      "have a specific figure removed" in {
        figureSet.getFigureCount(FigureType.BOMB) should be (6)
        figureSet = figureSet.removeFigure(FigureType.BOMB)
        figureSet.getFigureCount(FigureType.BOMB) should be (5)
      }
      "have lastFigure as the removed figure" in {
        figureSet.getLastFigure().get shouldBe a[Bomb]
      }
    }
    "add figure" should {
      val player = Player("TestPlayer", FieldType.A_SIDE)
      var figureSet = new FigureSet(player)
      "have a specific figure added" in {
        figureSet.getFigureCount(FigureType.BOMB) should be (6)
        figureSet = figureSet.addFigure(Bomb(player))
        figureSet.getFigureCount(FigureType.BOMB) should be (7)
      }
    }
  }
}
