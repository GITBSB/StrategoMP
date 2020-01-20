package stratego

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import stratego.model.engineComponent.{GameEngine, GameEngineInterface, GameEngineProxy}

class StrategoModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
   val gameEngine = new GameEngineProxy(GameEngine())
   bind[GameEngineInterface].toInstance(gameEngine)
  }
}
