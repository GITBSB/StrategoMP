package stratego

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import stratego.model.engineComponent.{GameEngine, GameEngineInterface}
import stratego.model.gridComponent.{Grid, GridInterface}

class StrategoModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
   // bind[GridInterface].to[Grid]
   // bind[GameEngineInterface].to[GameEngine]
  }
}
