package stratego.model.gridComponent

import org.scalatest.{Matchers, WordSpec}
import stratego.model.gridComponent.FigureType.FigureType

class FigureSetTest extends WordSpec with Matchers {
  "A FigureSet" when {
    "new" should {
      val figureSet: FigureSet = new FigureSet
      "have a set of figures" in {
        figureSet.figures.nonEmpty
      }
    }
    "no spies left" should {
      val figureSet: FigureSet = FigureSet(Map[FigureType, Int]())
    }
  }
}
