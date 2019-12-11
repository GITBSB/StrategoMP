package stratego

import java.io.BufferedReader

import com.google.inject.{Guice, Injector}
import stratego.gameEngine.{ConsolegameEngine}
import stratego.model.engineComponent.GameEngineInterface
import stratego.view.Tui

object Stratego {

  val injector: Injector = Guice.createInjector(new StrategoModule)
  val gameEngine: GameEngineInterface = injector.getInstance(classOf[GameEngineInterface])
  val tui: Tui = new Tui(gameEngine)
  val consolegameEngine = new ConsolegameEngine(gameEngine)


  def main(args: Array[String]): Unit = {
    processInput(new BufferedReader(Console.in), consolegameEngine)
  }

  def processInput(input: BufferedReader, consolegameEngine: ConsolegameEngine) = {
    var stopProcessingInput = false
    while (!stopProcessingInput) {
      if (input.ready()) {
        val line = input.readLine()
          stopProcessingInput = consolegameEngine.processInputLine(line) //TODO: Propage world state back up instead of having mutable state in GameEngine
      } else {
        Thread.sleep(199) // don't waste cpu cycles if no input is given
      }
    }
  }

}
