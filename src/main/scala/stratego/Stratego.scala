package stratego

import java.io.BufferedReader

import com.google.inject.{Guice, Injector}
import stratego.controller.ControllerInterface
import stratego.view.Tui

object Stratego {

  val injector: Injector = Guice.createInjector(new StrategoModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui: Tui = new Tui(controller)
  controller.createNewGrid

  def main(args: Array[String]): Unit = {
    tui.processInput(new BufferedReader(Console.in))
  }
}