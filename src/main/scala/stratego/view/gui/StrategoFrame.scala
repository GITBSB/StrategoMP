package stratego.view.gui

import java.awt.event.WindowEvent
import java.awt.{Color, Image}

import javax.imageio.ImageIO
import javax.swing.{BorderFactory, WindowConstants}
import stratego.model.engineComponent.{GameChanged, GameEngineInterface, GameQuit}
import stratego.model.gridComponent.FigureType

import scala.swing.Swing.LineBorder
import scala.swing.event._
import scala.swing.{Frame, MenuBar, Reactor, _}

class StrategoFrame(gameEngine: GameEngineInterface) extends Frame with Reactor{
  listenTo(gameEngine)
  title = "Stratego"
  preferredSize = new Dimension(800, 800)

  var fieldPanels = Array.ofDim[Button](10, 10)


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

  def gridPanel:Panel = new GridPanel(10, 10) {

    val originalImage = ImageIO.read(getClass().getResource("/StrategoImages/Stratego_Board.jpg"));
    val resizedImage = originalImage.getScaledInstance(700, 700, Image.SCALE_DEFAULT)

    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)
      if (null != resizedImage) g.drawImage(resizedImage, 0, 0, peer)
    }

    border = LineBorder(java.awt.Color.BLACK, 2)

    for {
      x <- 0 until 10
      y <- 0 until 10
    } {
      val fieldButton = new FieldButton(x, y)

      fieldPanels(x)(y) = fieldButton
      contents += fieldButton
      listenTo(fieldButton)
    }
    reactions += {
      case event: ButtonClicked => {
        println("Button " + event.source.getClass)
        event.source.border = BorderFactory.createMatteBorder(
          1, 1, 1, 1, Color.green)
        event.source.asInstanceOf[FieldButton].setImage("[AA]")
      }
    }
  }

  def figurePanel = new GridPanel(0,1) {
    val deleteButton = new Button {
      preferredSize_=(new Dimension(80, 100))
      text = "Delete"
      foreground = Color.BLACK
      opaque = false
      contentAreaFilled = false
    }
    listenTo(deleteButton)
    contents += deleteButton
    for(e <- FigureType.values) {
      val figureButton = new Button {
        preferredSize_=(new Dimension(80, 100))
        icon_=(new StretchIcon(getClass.getResource("/StrategoImages/" + e + ".jpg")))
        text = "1"
        foreground = Color.BLACK
        horizontalAlignment = Alignment.Right
        opaque = false
        contentAreaFilled = false
      }
      listenTo(figureButton)
      contents += figureButton
    }


    reactions += {
      case event: ButtonClicked => {
        event.source.foreground = (Color.BLUE);
        event.source.border = BorderFactory.createMatteBorder(
          1, 1, 1, 1, Color.green)
      }
    }

  }



  val statusline = new TextField(gameEngine.getGameStatus, 20)
  val fieldButton1 = new Button {
    preferredSize_=(new Dimension(50,50))
    name = "Hello"
  }
  listenTo(fieldButton1)

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(fieldButton1, BorderPanel.Position.South)
    add(figurePanel, BorderPanel.Position.East)
  }


  resizable = false
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
    } fieldPanels(x)(y)
    //statusline.text = gameEngine.statusText
    repaint
  }
}
