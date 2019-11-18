package stratego

import java.lang.ModuleLayer.Controller

import com.google.inject.{AbstractModule, Binder, Module}
import stratego.controller.{Controller, ControllerInterface}
import net.codingwell.scalaguice.{ScalaModule, ScalaPrivateModule}
import stratego.model.gridComponent.GridInterface
import stratego.model.gridComponent.Grid

class StrategoModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[GridInterface].to[Grid]
    bind[ControllerInterface].to[controller.Controller]
  }
}
