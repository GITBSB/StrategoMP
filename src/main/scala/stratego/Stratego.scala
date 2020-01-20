package stratego

import java.io.BufferedReader

import com.google.inject.{Guice, Injector}
import stratego.gameEngine.ConsoleController
import stratego.model.engineComponent.{GameEngineInterface}
import stratego.view.gui.StrategoFrame
import stratego.view.tui.{ConsoleOutput, ConsoleView}

object Stratego {

  val injector: Injector = Guice.createInjector(new StrategoModule)
  val gameEngine = injector.getInstance(classOf[GameEngineInterface])
  val consoleView = new ConsoleOutput(new ConsoleView)
  val consoleController = new ConsoleController(gameEngine)

  consoleView.listenTo(gameEngine)
  consoleView.printInitialMenu
  new StrategoFrame(gameEngine)

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
