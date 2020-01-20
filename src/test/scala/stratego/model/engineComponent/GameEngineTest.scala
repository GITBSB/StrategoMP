package stratego.model.engineComponent

import org.scalatest.{Matchers, WordSpec}
import stratego.gameEngine.GameStatus
import stratego.gameEngine.GameStatus.NO_VALID_FIGURE
import stratego.model.gridComponent.{FigureType, Position}

class GameEngineTest extends WordSpec with Matchers{
  "A GameEngine" when {
    "new" should {
      val gameEngine = GameEngine()
      "have GameStatus NOT_STARTED" in {
         gameEngine.gameState shouldEqual GameState.NOT_STARTED
      }
      "no Winner" in {
        gameEngine.getWinner should be (None)
      }
      "have figureSet with all figures for Active Player" in {
        gameEngine.getFigureSetActivePlayer.figures.size should be (12)
      }
      "get String GUI represantation for field" in {
        gameEngine.getFieldStringGUI(Position(0, 0)) should be ("")
      }
      "get String TUI represantation for field" in {
        gameEngine.gridToString should be (
          "\n[AA][AA][AA][AA][AA][AA][AA][AA][AA][AA] 0"
            + "\n[AA][AA][AA][AA][AA][AA][AA][AA][AA][AA] 1"
            + "\n[AA][AA][AA][AA][AA][AA][AA][AA][AA][AA] 2"
            + "\n[AA][AA][AA][AA][AA][AA][AA][AA][AA][AA] 3"
            + "\n[  ][  ][--][--][  ][  ][--][--][  ][  ] 4"
            + "\n[  ][  ][--][--][  ][  ][--][--][  ][  ] 5"
            + "\n[BB][BB][BB][BB][BB][BB][BB][BB][BB][BB] 6"
            + "\n[BB][BB][BB][BB][BB][BB][BB][BB][BB][BB] 7"
            + "\n[BB][BB][BB][BB][BB][BB][BB][BB][BB][BB] 8"
            + "\n[BB][BB][BB][BB][BB][BB][BB][BB][BB][BB] 9"
            + "\n- A - B - C - D - E - F - G - H - I - J")
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
    "quit game" should {
      val gameEngine = GameEngine()
      val newGame = gameEngine.quitGame()
      "have GameStatus NOT_STARTED" in {
        newGame.getGameState shouldEqual GameState.END
      }
    }
    "changed state" should {
      "notify observers" in {

      }
    }
    "placing a figure" should {
      val gameEngine = GameEngine()
      "have set a figure when placing on correct site" in {
        val position = Position(2, 2)
        val game = gameEngine.setFigure(FigureType.FLAG, position)
        game.getStatusLine should be (GameStatus.FIGURE_SET)
        game.getGameState should be (GameState.SET_FIGURES)
      }
      "have set no figure when placing on wrong site" in {
        val position = Position(9, 9)
        val game = gameEngine.setFigure(FigureType.FLAG, position)
        game.getStatusLine should be (GameStatus.INVALID_POSITION)
      }
      "have replacing figure when field has figure" in {
        val position = Position(2, 2)
        var game = gameEngine.setFigure(FigureType.FLAG, position)
        game = game.setFigure(FigureType.GENERAL, position)
        val figure = game.getFigure(position)
        figure.get.figureType should be (FigureType.GENERAL)
      }
      "have not set figure when no figures of type left" in {
        val position = Position(2, 2)
        var game = gameEngine.setFigure(FigureType.FLAG, position)
        game = game.setFigure(FigureType.FLAG, position)
        game.getStatusLine should be (GameStatus.NO_FIGURES_LEFT)
      }
      "and no figures left should start the battle" in {
        val nGame = setUpPlaceAllFigures
        nGame.getStatusLine should be (GameStatus.BATTLE_START)
      }
    }
    "delete a figure from field" should {
      val gameEngine = GameEngine()
      "have deleted the figure from field" in {
        val position = Position(2, 2)
        val nGame = gameEngine.setFigure(FigureType.FLAG, position)
        val newGame = nGame.deleteFigure(position)
        newGame.getFigure(position) should be (None)
      }
    }
    "moving a figure to an empty valid field" should {
      val gameEngine = GameEngine()
      "have moved the figure" in {
        val from = Position(2, 2)
        val to = Position(2, 3)
        val nGame = gameEngine.setFigure(FigureType.SERGEANT, from)
        val figureToMove = nGame.getFigure(from)
        val newGame = nGame.moveFigure(from, to)
        newGame.getFigure(to) should be (figureToMove)
      }
    }
    "moving a non movable figure" should {
      val gameEngine = GameEngine()
      "not moved the figure" in {
        val from = Position(2, 2)
        val to = Position(2, 3)
        val nGame = gameEngine.setFigure(FigureType.FLAG, from)
        val figureToMove = nGame.getFigure(from)
        val newGame = nGame.moveFigure(from, to)
        newGame.getStatusLine should be (NO_VALID_FIGURE)
      }
    }
    "moving a figure to an empty non valid field" should {
      val gameEngine = GameEngine()
      "have moved the figure" in {
        val from = Position(2, 2)
        val to = Position(2, 4)
        val nGame = gameEngine.setFigure(FigureType.SERGEANT, from)
        val newGame = nGame.moveFigure(from, to)
        newGame.getFigure(to) should be(None)
        newGame.getStatusLine should be(GameStatus.INVALID_POSITION)
      }
    }
    "starting battle" should {
      val gameEngine = GameEngine()
      val nGame = gameEngine.startBattle
      "have correct GameState" in {
        nGame.getGameState should be (GameState.FIGHT)
      }
    }
    "attacking opponent with same figure" should {
      val gameEngine = GameEngine()
      "kill both figures" in {
        var nGame = gameEngine.setFigure(FigureType.SERGEANT, Position(3, 9))
        nGame = nGame.changePlayer
        nGame = nGame.setFigure(FigureType.SERGEANT, Position(6, 9))
        nGame = nGame.moveFigure(Position(6, 9), Position(5, 9))
        nGame = nGame.moveFigure(Position(3, 9), Position(4, 9))
        nGame = nGame.moveFigure(Position(5, 9), Position(4, 9))
        nGame.getFigure(Position(5, 9)) should be(None)
        nGame.getFigure(Position(4, 9)) should be(None)
        nGame.getStatusLine should be (GameStatus.ATTACK_DRAW)
      }
    }
    "battle between Marshal and Spy" should {
      val gameEngine = GameEngine()
      "kill the Marshal" in {
        var nGame = gameEngine.setFigure(FigureType.MARSHAL, Position(3, 9))
        nGame = nGame.changePlayer
        nGame = nGame.setFigure(FigureType.SPY, Position(6, 9))
        nGame = nGame.moveFigure(Position(6, 9), Position(5, 9))
        nGame = nGame.moveFigure(Position(3, 9), Position(4, 9))
        nGame = nGame.moveFigure(Position(5, 9), Position(4, 9))
        nGame.getFigure(Position(5, 9)) should be(None)
        nGame.getFigure(Position(4, 9)).get.figureType should be(FigureType.SPY)
        nGame.getStatusLine should be (GameStatus.SPY_ATTACKS_MARSHAL)
      }
    }
    "attacking opponents with stronger figure" should {
      val gameEngine = GameEngine()
      "should kill opponents figure" in {
        var nGame = gameEngine.setFigure(FigureType.GENERAL, Position(3, 9))
        nGame = nGame.changePlayer
        nGame = nGame.setFigure(FigureType.MARSHAL, Position(6, 9))
        nGame = nGame.moveFigure(Position(6, 9), Position(5, 9)) // Marshal moves
        nGame = nGame.moveFigure(Position(3, 9), Position(4, 9)) // General moves
        nGame = nGame.moveFigure(Position(5, 9), Position(4, 9)) // Marshal attacks
        nGame.getFigure(Position(5, 9)) should be(None)
        nGame.getFigure(Position(4, 9)).get.figureType should be(FigureType.MARSHAL)
        nGame.getStatusLine should be (GameStatus.ATTACK_WIN)
      }
    }
    "attacking opponents with weaker figure" should {
      val gameEngine = GameEngine()
      "should kill opponents figure" in {
        var nGame = gameEngine.setFigure(FigureType.MARSHAL, Position(3, 9))
        nGame = nGame.changePlayer
        nGame = nGame.setFigure(FigureType.GENERAL, Position(6, 9))
        nGame = nGame.moveFigure(Position(6, 9), Position(5, 9)) // GENERAL moves
        nGame = nGame.moveFigure(Position(3, 9), Position(4, 9)) // Marshal moves
        nGame = nGame.moveFigure(Position(5, 9), Position(4, 9)) // GENERAL attacks
        nGame.getFigure(Position(5, 9)) should be(None)
        nGame.getFigure(Position(4, 9)).get.figureType should be(FigureType.MARSHAL)
        nGame.getStatusLine should be (GameStatus.ATTACK_LOOSE)
      }
    }
    "attacking Bomb with Miner" should {
      val gameEngine = GameEngine()
      "should defuse Bomb" in {
        var nGame = gameEngine.setFigure(FigureType.BOMB, Position(3, 9))
        nGame = nGame.setFigure(FigureType.MAJOR, Position(3, 8))
        nGame = nGame.changePlayer
        nGame = nGame.setFigure(FigureType.MINER, Position(6, 9))
        nGame = nGame.moveFigure(Position(6, 9), Position(5, 9)) // Miner moves
        nGame = nGame.moveFigure(Position(3, 8), Position(4, 8))
        nGame = nGame.moveFigure(Position(5, 9), Position(4, 9)) // Miner moves
        nGame = nGame.moveFigure(Position(4, 8), Position(5, 8))
        nGame = nGame.moveFigure(Position(4, 9), Position(3, 9)) // Miner defuses Bomb
        nGame.getFigure(Position(4, 9)) should be(None)
        nGame.getFigure(Position(3, 9)).get.figureType should be(FigureType.MINER)
        nGame.getStatusLine should be (GameStatus.MINER_ATTACKS_BOMB)
      }
    }
    "attacking Bomb with figure except Miner" should {
      val gameEngine = GameEngine()
      "should kill figure" in {
        var nGame = gameEngine.setFigure(FigureType.BOMB, Position(3, 9))
        nGame = nGame.setFigure(FigureType.MAJOR, Position(3, 8))
        nGame = nGame.changePlayer
        nGame = nGame.setFigure(FigureType.MARSHAL, Position(6, 9))
        nGame = nGame.moveFigure(Position(6, 9), Position(5, 9)) // Marshal moves
        nGame = nGame.moveFigure(Position(3, 8), Position(4, 8))
        nGame = nGame.moveFigure(Position(5, 9), Position(4, 9)) // Marshal moves
        nGame = nGame.moveFigure(Position(4, 8), Position(5, 8))
        nGame = nGame.moveFigure(Position(4, 9), Position(3, 9)) // Marshal attacks Bomb
        nGame.getFigure(Position(4, 9)) should be(None)
        nGame.getFigure(Position(3, 9)).get.figureType should be(FigureType.BOMB)
        nGame.getStatusLine should be (GameStatus.BOMB_EXPLODES)
      }
    }
    "finding Flag" should {
      val gameEngine = GameEngine()
      "win the game" in {
        var nGame = gameEngine.setFigure(FigureType.FLAG, Position(3, 9))
        nGame = nGame.setFigure(FigureType.MAJOR, Position(3, 8))
        nGame = nGame.changePlayer
        nGame = nGame.setFigure(FigureType.GENERAL, Position(6, 9))
        nGame = nGame.moveFigure(Position(6, 9), Position(5, 9)) // General moves
        nGame = nGame.moveFigure(Position(3, 8), Position(4, 8))
        nGame = nGame.moveFigure(Position(5, 9), Position(4, 9)) // General moves
        nGame = nGame.moveFigure(Position(4, 8), Position(5, 8))
        nGame = nGame.moveFigure(Position(4, 9), Position(3, 9)) // General finds flag
        nGame.getStatusLine should be(GameStatus.FLAG_FOUND)
        nGame.getGameState should be(GameState.END)
      }
      "changing player" should {
        val gameEngine = GameEngine()
        val activePlayer = gameEngine.getActivePlayer
        val nGame = gameEngine.changePlayer
        "have not previous active player" in {
          nGame.getActivePlayer shouldNot be(activePlayer)
        }
      }
    }
  }

  def setUpPlaceAllFigures: GameEngineInterface = {
    var gameEngine: GameEngineInterface = GameEngine()
    var figureTypeList = createFigureTypeList
    for {
      row <- 0 until 4
      col <- 0 until 10
    } {
      val lastFigureType = figureTypeList.head
      figureTypeList = figureTypeList diff List(lastFigureType)
      gameEngine = gameEngine.setFigure(lastFigureType, Position(row, col))
    }

    figureTypeList = createFigureTypeList
    for {
      row <- 6 until 10
      col <- 0 until 10
    } {
      val lastFigureType = figureTypeList.head
      figureTypeList = figureTypeList diff List(lastFigureType)
      gameEngine = gameEngine.setFigure(lastFigureType, Position(row, col))
    }
    gameEngine
  }

  def createFigureTypeList= {
    List(FigureType.BOMB, FigureType.BOMB, FigureType.BOMB, FigureType.BOMB, FigureType.BOMB, FigureType.BOMB,  FigureType.MARSHAL,  FigureType.GENERAL,
      FigureType.COLONEL, FigureType.COLONEL, FigureType.MAJOR, FigureType.MAJOR, FigureType.MAJOR, FigureType.CAPTAIN, FigureType.CAPTAIN, FigureType.CAPTAIN, FigureType.CAPTAIN,
      FigureType.LIEUTENANT, FigureType.LIEUTENANT, FigureType.LIEUTENANT, FigureType.LIEUTENANT,
      FigureType.SERGEANT, FigureType.SERGEANT, FigureType.SERGEANT, FigureType.SERGEANT,
      FigureType.MINER, FigureType.MINER, FigureType.MINER, FigureType.MINER, FigureType.MINER,  FigureType.SPY, FigureType.FLAG,
      FigureType.SCOUT, FigureType.SCOUT, FigureType.SCOUT, FigureType.SCOUT, FigureType.SCOUT, FigureType.SCOUT, FigureType.SCOUT, FigureType.SCOUT)
  }
}
