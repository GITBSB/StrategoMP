package stratego

import java.io.BufferedReader

import com.google.inject.{Guice, Injector}
import stratego.controller.ConsoleController
import stratego.model.engineComponent.GameEngine
import stratego.model.gridComponent.Grid
import stratego.view.Tui

object Stratego {

  def main(args: Array[String]): Unit = {
    //Initialize Game
    val injector: Injector = Guice.createInjector(new StrategoModule)
    val tui: Tui = new Tui()
    val gameState = new Grid()
    val consoleController = new ConsoleController(new GameEngine(gameState))

    processInput(new BufferedReader(Console.in), consoleController)
  }

  def processInput(input: BufferedReader, controller: ConsoleController) = {
    var stopProcessingInput = false
    while (!stopProcessingInput) {
      if (input.ready()) {
        val line = input.readLine()
        stopProcessingInput = controller.processInputLine(line) //TODO: Propage world state back up instead of having mutable state in GameEngine
      } else {
        Thread.sleep(199) // don't waste cpu cycles if no input is given
      }
    }
  }
}
