package stratego.view.gui

import swing._
import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO
import stratego.model.engineComponent.GameChanged

import scala.swing.event.MouseClicked

class FieldPanel (gameFieldGrid: GameFieldGrid, row: Int, column: Int) extends BoxPanel(Orientation.Vertical) {
  private var _imagePath = ""
  private var bufferedImage:BufferedImage = null

  preferredSize = new Dimension(51, 51)

  listenTo(mouse.clicks)
  reactions += {
    case e: GameChanged => {
      // do something?
      repaint
    }
    case MouseClicked(src, pt, mod, clicks, pops) => {
      println("Clicked on: " + row +" " +column)
      // gameFieldGrid. clickedthisField -> row,col
      repaint
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
}

object ImagePanel
{
  //def apply(row: Int, column: Int) = new FieldPanel(row, column)
}