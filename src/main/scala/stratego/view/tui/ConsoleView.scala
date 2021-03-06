package stratego.view.tui

import stratego.model.engineComponent._
import stratego.model.gridComponent.FigureType

class ConsoleView {

  def gameMenuToString: String = {
    "\nWelcome to Stratego" +
      "\n\nOptions:" +
      "\n\"n\": Start a new game" +
      "\n\"d\": Start game with default setup" +
      "\n\"q\": Quit the game"
  }

  def gameStartToString(gameEngine: GameEngineInterface): String = {
    "New game started:\n" +
      gridToString(gameEngine) +
      "\nPlayer " + gameEngine.getActivePlayer.toString + " starts the game" +
      figureSetToString(gameEngine) +
      "\nOptions:" +
      "\n\"s [n] [a,b]\": Set a figure[n] to field[a,b]"
  }

  def figureSetResultToString(gameEngine: GameEngineInterface): String = {
    gameEngine.getGameState match {
      case GameState.FIGHT => {
        activePlayerToString(gameEngine) +
          "All figures set!\nMatch beginns...\n\n" +
          "Options:" +
          "\n\"m [a,y] [x,y]\": Move figure from field [a,b] to field [x,y]" +
          gridToString(gameEngine)
      }
      case GameState.SET_FIGURES => {
        gameStatusToString(gameEngine) +
          "Active player: " + gameEngine.getActivePlayer +
          figureSetToString(gameEngine) +
          gridToString(gameEngine)
      }
    }
  }

  def moveResultToString(gameEngine: GameEngineInterface): String = {
    gameStatusToString(gameEngine) +
      activePlayerToString(gameEngine) +
      gridToString(gameEngine) +
      "Options:" +
      "\n\"m [a,y] [x,y]\": Move figure from field [a,b] to field [x,y]" +
      "\nExample: 'm B,2 B,3'"
  }

  def gameStateToString(gameEngine: GameEngineInterface): String = {
    "Current game state: " + gameEngine.getGameState
  }

  def gameStatusToString(gameEngine: GameEngineInterface): String = {
    "Current game status: " + gameEngine.getStatusLine
  }

  def activePlayerToString(gameEngine: GameEngineInterface): String = {
    "Active player: " + gameEngine.getActivePlayer
  }

  def figureSetToString(gameEngine: GameEngineInterface): String = {
    "\nChose which figure to place on field:\n" +
      "\n1 ->" + FigureType.BOMB + " | " +  gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.BOMB) +
      "\n2 ->" + FigureType.MARSHAL + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.MARSHAL) +
      "\n3 ->" + FigureType.GENERAL + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.GENERAL) +
      "\n4 ->" + FigureType.COLONEL + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.COLONEL) +
      "\n5 ->" + FigureType.MAJOR + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.MAJOR) +
      "\n6 ->" + FigureType.CAPTAIN + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.CAPTAIN) +
      "\n7 ->" + FigureType.LIEUTENANT + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.LIEUTENANT) +
      "\n8 ->" + FigureType.SERGEANT + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.SERGEANT) +
      "\n9 ->" + FigureType.MINER + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.MINER) +
      "\n10 ->" + FigureType.SCOUT + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.SCOUT) +
      "\n11 ->" + FigureType.SPY + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.SPY) +
      "\n12 ->" + FigureType.FLAG + " | " + gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.FLAG) +
      " Example: 's 2 B,3'"
  }

  def gridToString(gameEngine: GameEngineInterface): String = {
    gameEngine.gridToString
  }

  def winnerToString(gameEngine: GameEngineInterface): String = {
    "Player " + gameEngine.getWinner.toString + " wins the game!"
  }

  def gameQuitToString(gameEngine: GameEngineInterface): String = {
    "Quitting..."
  }
}
