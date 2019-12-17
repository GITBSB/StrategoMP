package stratego.view.gui

import java.awt.event.WindowEvent

import javax.swing.WindowConstants
import stratego.model.engineComponent.{GameChanged, GameEngineInterface, GameQuit}

import scala.swing._
import scala.swing.event._
import scala.swing.{Frame, MenuBar, Reactor}

class StrategoFrame(gameEngine: GameEngineInterface) extends Frame with Reactor{
  listenTo(gameEngine)

  title = "Stratego"

  override def closeOperation(): Unit = gameEngine.quitGame
  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)


  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        gameEngine.startNewGame
        mnemonic = Key.N
      })
      contents += new MenuItem(Action("Quit") {
        gameEngine.quitGame
        mnemonic = Key.Q
      })
    }
    contents += new Menu("Player") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Rename Player") {
        // TODO: nice to have
      })
    }
  }

  visible = true
  updateGame

  def updateGame = {

  }

  reactions += {
    case event: GameChanged => updateGame
    case event: GameQuit => peer.dispatchEvent(new WindowEvent(peer, WindowEvent.WINDOW_CLOSING))
  }
}
