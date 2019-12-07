package stratego

import java.io.BufferedReader

import com.google.inject.{Guice, Injector}
import stratego.controller.{ConsoleController}
import stratego.model.engineComponent.GameEngineInterface
import stratego.view.Tui

object Stratego {

  val injector: Injector = Guice.createInjector(new StrategoModule)
  val gameEngine: GameEngineInterface = injector.getInstance(classOf[GameEngineInterface])
  val tui: Tui = new Tui(gameEngine)
  val consoleController = new ConsoleController(gameEngine)

  def main(args: Array[String]): Unit = {
    processInput(new BufferedReader(Console.in), consoleController)
  }

  def processInput(input: BufferedReader, consoleController: ConsoleController) = {
    var stopProcessingInput = false
    while (!stopProcessingInput) {
      if (input.ready()) {
        val line = input.readLine()
          stopProcessingInput = consoleController.processInputLine(line)
      } else {
        Thread.sleep(199) // don't waste cpu cycles if no input is given
      }
    }
  }

}
