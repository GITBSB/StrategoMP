package stratego.view.gui

import java.awt.image.BufferedImage

import javax.swing.ImageIcon

import scala.swing.{Button, Dimension}

class FieldButton (val row: Int, val column: Int) extends Button{
  preferredSize_=(new Dimension(100, 100))
  clearImage
  opaque = false
  contentAreaFilled = false

  def setImage(figureId: String) {
    icon_=(new StretchIcon(getClass.getResource("/StrategoImages/" + figureId + ".jpg")))
  }

  def clearImage: Unit = {
    icon_=(new ImageIcon(new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB)))
  }
}
