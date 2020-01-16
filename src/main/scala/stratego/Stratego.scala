package stratego

import java.io.BufferedReader

import com.google.inject.{Guice, Injector}
import stratego.gameEngine.ConsoleController
import stratego.model.engineComponent.{GameEngine, GameEngineInterface, GameEngineProxy}
import stratego.view.ConsoleView

object Stratego {

 // val injector: Injector = Guice.createInjector(new StrategoModule)
  val gameEngine = GameEngine()
  val gameEngineProxy = new GameEngineProxy(gameEngine)
  val consoleView: ConsoleView = new ConsoleView(gameEngineProxy)
  val consoleController = new ConsoleController(gameEngineProxy)
  consoleView.printMenu

  def main(args: Array[String]): Unit = {
    processInput(new BufferedReader(Console.in), consoleController)
  }

  def processInput(input: BufferedReader, consoleController: ConsoleController) = {
    var processingInput = true
    while (processingInput) {
      if (input.ready()) {
        val line = input.readLine()
        processingInput = consoleController.processInputLine(line)
      } else {
        Thread.sleep(199) // don't waste cpu cycles if no input is given
      }
    }
  }
}
