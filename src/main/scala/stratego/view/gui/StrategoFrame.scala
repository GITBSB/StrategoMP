package stratego.view.gui

import java.awt.event.WindowEvent
import java.awt.{Color, Image}

import javax.imageio.ImageIO
import javax.swing.{BorderFactory, WindowConstants}
import stratego.gameEngine.GameStatus
import stratego.model.engineComponent.{GameChanged, GameEngineInterface, GameQuit}
import stratego.model.gridComponent.FigureType

import scala.swing.Swing.LineBorder
import scala.swing.event._
import scala.swing.{Frame, MenuBar, Reactor, _}

class StrategoFrame(gameEngine: GameEngineInterface) extends Frame with Reactor{
  listenTo(gameEngine)
  title = "Stratego"
  preferredSize = new Dimension(800, 800)

  var fieldButtons = Array.ofDim[FieldButton](10, 10)
  var figureButtons = scala.collection.mutable.ListBuffer.empty[Button]
  var selectedFigureButton: AbstractButton = null
  var fieldButtonSelect: FieldButton = null

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
      val fieldButton: FieldButton = new FieldButton(x, y)
      fieldButtons(x)(y) = fieldButton

      // not working why?
      if((x == 4 || x == 5) && (y == 2 || y == 3 || y == 6|| y == 7)) {
        fieldButtons(x)(y).enabled = false
      }
      contents += fieldButton
      listenTo(fieldButton)
    }

    reactions += {
      case event: ButtonClicked => {
        val selectedFieldButton: FieldButton = event.source.asInstanceOf[FieldButton]

        if(true) { // gameEngine.getGameStatus == GameStatus.SET_FIGURES
          if(selectedFigureButton.name == "Delete") {
            // TODO: needs method to delete in GameEngine
            // update all
            updateFigureButtons
          } else {
            val isSet = gameEngine.setFigure(FigureType.withName(selectedFigureButton.name), selectedFieldButton.row, selectedFieldButton.column)
            if(isSet) {
              updateFigureButtons
              selectedFieldButton.setImage(selectedFigureButton.name)
            }
          }

        } else if(gameEngine.getGameStatus == GameStatus.FIGHT) //gameEngine.getGameStatus == GameStatus.FIGHT
          if (fieldButtonSelect != null) {
            fieldButtonSelect.border =  selectedFieldButton.border
            selectedFieldButton.border = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder())
          }
      }
    }
  }

  def figurePanel = new GridPanel(0,1) {
    val deleteButton = new Button {
      preferredSize_=(new Dimension(80, 100))
      name = "Delete"
      text = name
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
        name = e.toString
        text = gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.withName(name)).toString
        foreground = Color.BLACK
        background = Color.LIGHT_GRAY
        border = BorderFactory.createEmptyBorder(2, 2, 2,2)
        horizontalAlignment = Alignment.Left
        opaque = false
        contentAreaFilled = false
      }
      figureButtons += figureButton
      listenTo(figureButton)
      contents += figureButton
    }


    reactions += {
      case event: ButtonClicked => {
        if(selectedFigureButton != null) {
          selectedFigureButton.border = BorderFactory.createEmptyBorder(2, 2, 2,2)
          selectedFigureButton.opaque = false
        }
        selectedFigureButton = event.source
        event.source.opaque = true
        event.source.border = BorderFactory.createRaisedBevelBorder()
        println(selectedFigureButton.name)
        if(event.source.text == "Delete") {

        } else {

        }
      }
    }
  }


  val statusline = new TextField(gameEngine.getGameStatus, 20)

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
    add(figurePanel, BorderPanel.Position.East)
  }


  resizable = false
  visible = true
  updateFieldButtons

  reactions += {
    case event: GameChanged => updateFieldButtons
    case event: GameQuit => peer.dispatchEvent(new WindowEvent(peer, WindowEvent.WINDOW_CLOSING))
  }

  def updateFigureButtons = {
    for(button <- figureButtons) {
       button.text = gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.withName(button.name)).toString
    }
  }

  def updateFieldButtons = {
    for {
      x <- 0 until 10
      y <- 0 until 10
    } fieldButtons(x)(y)
    //statusline.text = gameEngine.statusText
    repaint
  }
}
