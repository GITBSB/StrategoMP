package stratego.view.gui

import java.awt.event.WindowEvent

import javax.swing.WindowConstants
import stratego.model.engineComponent.{GameChanged, GameEngineInterface, GameQuit}

import scala.swing._
import scala.swing.event._
import scala.swing.{Frame, MenuBar, Reactor}
import scala.swing.Swing.LineBorder

class StrategoFrame(gameEngine: GameEngineInterface) extends Frame with Reactor{
  listenTo(gameEngine)
  title = "Stratego"
  preferredSize = new Dimension(500, 500)
  var fieldPanels = Array.ofDim[FieldPanel](10, 10)

  override def closeOperation(): Unit = gameEngine.quitGame
  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)


  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        gameEngine.createNewGrid
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

  def gridPanel = new GridPanel(10, 10) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.WHITE
    for {
      x <- 0 until 10
      y <- 0 until 10
    } {
      val fieldPanel = new FieldPanel(gameEngine, x, y)
      border = LineBorder(java.awt.Color.BLACK, 2)
      fieldPanels(x)(y) = fieldPanel
      contents += fieldPanel
      listenTo(fieldPanel)
    }
  }


  val statusline = new TextField(gameEngine.getGameStatus, 20)

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    //add(statusline, BorderPanel.Position.South)
  }


  visible = true
  updateFieldPanels


  reactions += {
    case event: GameChanged => updateFieldPanels
    case event: GameQuit => peer.dispatchEvent(new WindowEvent(peer, WindowEvent.WINDOW_CLOSING))
  }

  def updateFieldPanels = {
    for {
      x <- 0 until 10
      y <- 0 until 10
    } fieldPanels(x)(y).update
    //statusline.text = gameEngine.statusText
    resizable = false
    resizable = true
    repaint
  }
}
