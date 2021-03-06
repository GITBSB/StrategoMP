package stratego.view.gui

import java.awt.event.WindowEvent
import java.awt.image.BufferedImage
import java.awt.{Color, Image}

import javax.imageio.ImageIO
import javax.swing.{BorderFactory, UIManager, WindowConstants}
import stratego.model.engineComponent._
import stratego.model.gridComponent.{FigureType, Position}
import stratego.view.gui.util.StretchIcon

import scala.swing.event._
import scala.swing.{Frame, MenuBar, Reactor, _}

class StrategoFrame(gameEngine: GameEngineInterface) extends Frame with Reactor{
  listenTo(gameEngine)
  title = "Stratego"
  val windowHeight = 800
  val windowWidth = 800
  preferredSize = new Dimension(windowWidth, windowHeight)

  var fieldButtons = Array.ofDim[FieldButton](10, 10)
  var figureButtons = scala.collection.mutable.ListBuffer.empty[Button]
  var selectedFigureButton: Option[AbstractButton] = None
  var fieldButtonSelect: Option[FieldButton] = None

  override def closeOperation(): Unit = gameEngine.quitGame
  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  menuBar = new MenuBar {
    contents += new Menu("Game") {
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

  def gridPanel:Panel = new GridPanel(10, 10) {
    val originalImage: BufferedImage = ImageIO.read(getClass().getResource("/StrategoImages/Stratego_Board.jpg"));
    val resizedImage = originalImage.getScaledInstance(windowWidth - 101, windowHeight - 87, Image.SCALE_DEFAULT) //749 763

    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)
      if (null != resizedImage) g.drawImage(resizedImage, 0, 0, peer)
    }
    val compoundBorder = BorderFactory.createCompoundBorder(
      BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());
    border = compoundBorder;

    for {
      x <- 0 until 10
      y <- 0 until 10
    } {
      val fieldButton: FieldButton = new FieldButton(x, y)
      fieldButtons(x)(y) = fieldButton

      if((x == 4 || x == 5) && (y == 2 || y == 3 || y == 6|| y == 7))
        fieldButtons(x)(y).enabled = false
      contents += fieldButton
      listenTo(fieldButton)
    }

    reactions += {
      case event: ButtonClicked => {
        val selectedFieldButton: FieldButton = event.source.asInstanceOf[FieldButton]

        if(gameEngine.getGameState == GameState.SET_FIGURES) {
          if (selectedFigureButton.isDefined) {
            if (selectedFigureButton.get.name == "Delete") {
              gameEngine.deleteFigure(Position(selectedFieldButton.row, selectedFieldButton.column))
            } else {
              gameEngine.setFigure(FigureType.withName(selectedFigureButton.get.name), Position(selectedFieldButton.row, selectedFieldButton.column))
            }
          }
        } else if(gameEngine.getGameState == GameState.FIGHT)
          if (fieldButtonSelect.isDefined) {
            fieldButtonSelect.get.border = UIManager.getBorder("Button.border")
            gameEngine.moveFigure(Position(fieldButtonSelect.get.row, fieldButtonSelect.get.column), Position(selectedFieldButton.row, selectedFieldButton.column))
            fieldButtonSelect = None
          } else {
            selectedFieldButton.border = BorderFactory.createLineBorder(Color.GREEN, 1)

            fieldButtonSelect = Option(selectedFieldButton)
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
        if(selectedFigureButton.isDefined) {
          selectedFigureButton.get.border = BorderFactory.createEmptyBorder(2, 2, 2,2)
          selectedFigureButton.get.opaque = false
        }
        selectedFigureButton = Some(event.source)
        event.source.opaque = true
        event.source.border = BorderFactory.createRaisedBevelBorder()
      }
    }
  }

  val statusLine = new Label{
    text = "Start a new Game."
    font = new Font("Ariel", java.awt.Font.ITALIC, 18)
  }

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(statusLine, BorderPanel.Position.South)
    add(figurePanel, BorderPanel.Position.East)
  }

  resizable = false
  visible = true

  reactions += {
    case event: GameQuitEvent => peer.dispatchEvent(new WindowEvent(peer, WindowEvent.WINDOW_CLOSING))
    case event: GameStartedEvent => clearField; updateStatusLine(event.gameEngine); updateFieldButtons(event.gameEngine)
    case event: FigureSetEvent => updateStatusLine(event.gameEngine); updateFieldButtons(event.gameEngine); updateFigureButtons(event.gameEngine)
    case event: MoveFigureEvent => updateStatusLine(event.gameEngine); updateFieldButtons(event.gameEngine); updateFigureButtons(event.gameEngine)
    case event: InvalidMoveEvent => updateStatusLine(event.gameEngine);
    case event: AttackEvent => updateStatusLine(event.gameEngine); updateFieldButtons(event.gameEngine)
    case event: WinnerEvent => printWinner(event.gameEngine); updateFieldButtons(event.gameEngine)
    case event: FigureDeletedEvent => updateStatusLine(event.gameEngine); updateFieldButtons(event.gameEngine); updateFigureButtons(event.gameEngine)
  }

  def printWinner(gameEngine: GameEngineInterface): Unit = {
    val statusLineGUI = new StringBuilder
    statusLineGUI.append(gameEngine.getStatusLine + "  -  Player " + gameEngine.getWinner.toString + " wins the game!")
    statusLine.text = statusLineGUI.toString()
  }

  def clearField(): Unit = {
    for {
      x <- 0 until 10
      y <- 0 until 10
    } fieldButtons(x)(y).clearImage
  }

  def updateStatusLine(gameEngine: GameEngineInterface): Unit = {
    val statusLineGUI = new StringBuilder
    statusLineGUI.append(gameEngine.getActivePlayer.name + ": " +
                         gameEngine.getGameState + "  -  " +
                         gameEngine.getStatusLine)
    statusLine.text = statusLineGUI.toString()
  }

  def updateFieldButtons(gameEngine: GameEngineInterface): Unit = {
    for {
      x <- 0 until 10
      y <- 0 until 10
    } {
      val imageId = gameEngine.getFieldStringGUI(Position(x, y))
      if(imageId.length == 0) fieldButtons(x)(y).clearImage
      else  fieldButtons(x)(y).setImage(imageId)
    }
  }

  def updateFigureButtons(gameEngine: GameEngineInterface): Unit = {
    figureButtons.foreach(b => b.text =
      gameEngine.getFigureSetActivePlayer.getFigureCount(FigureType.withName(b.name)).toString)
  }
}
