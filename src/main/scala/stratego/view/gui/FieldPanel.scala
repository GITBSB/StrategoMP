package stratego.view.gui

import swing._
import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO
import stratego.model.engineComponent.{GameChanged, GameEngineInterface}

import scala.swing.event.MouseClicked

class FieldPanel (gameEngine: GameEngineInterface, row: Int, column: Int) extends BoxPanel(Orientation.Vertical)  {
  private var _imagePath = ""
  private var bufferedImage:BufferedImage = null

  val color1 = new Color(200, 200, 255)
  val color2 = new Color(224, 224, 255)


  preferredSize = new Dimension(50, 50)
  background = color1
  border = Swing.BeveledBorder(Swing.Raised)

  listenTo(mouse.clicks)
  reactions += {
    case e: GameChanged => {
      // do something?
      repaint
    }
    case MouseClicked(src, pt, mod, clicks, pops) => {
      println("Clicked on: " + row + " " + column)
      // gameFieldGrid. clickedthisField -> row,col
      background = color1
    }
  }


  def imagePath = _imagePath

  def imagePath_=(value:String)
  {
    _imagePath = value
    bufferedImage = ImageIO.read(new File(_imagePath))
  }


  override def paintComponent(g:Graphics2D) =
  {
    if (null != bufferedImage) g.drawImage(bufferedImage, 0, 0, null)
  }

  def update = {

    repaint
  }

}