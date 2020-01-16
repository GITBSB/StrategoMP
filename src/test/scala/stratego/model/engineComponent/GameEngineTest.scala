package stratego.model.engineComponent

import org.scalatest.{Matchers, WordSpec}
import stratego.gameEngine.GameStatus
import stratego.model.gridComponent.Figure.Scout
import stratego.model.gridComponent.{FigureType, Grid, Position}

class GameEngineTest extends WordSpec with Matchers{
  "A GameEngine" when {
    "new" should {
      val gameEngine = GameEngine()
      "have GameStatus NOT_STARTED" in {
         gameEngine.gameState shouldEqual GameState.NOT_STARTED
      }
    }
    "started" should {
      val gameEngine = GameEngine()
      val newGame = gameEngine.startNewGame()
      "setup a new game" in {

      }
      "indicate the player who is allowed to do first move" in {

      }
    }
    "changed state" should {
      "notify observers" in {

      }
    }
    "placing a figure" should {

    }
    "moving a figure to an empty field" should {

    }
    "moving a figure to an opponent's field" should {

    }
    "moving a figure to an own field" should {

    }
    "moving a figure to an unknown field" should {

    }
    "moving a figure" should {
     val engine = GameEngine(Grid().createPlayableGrid(), GameState.FIGHT)
      val figureType = FigureType.values.find(_.toString == "Scout")
      engine.setFigure(figureType.get, Position(1,1))
      "set this figure to the new field" in {
        engine.moveFigure(Position(1,1), Position(1,2))
        engine.getFigure(Position(1,2)).get should matchPattern { case Scout(_) => }
        engine.statusLine shouldEqual GameStatus.IDLE
      }
    }
  }
}
