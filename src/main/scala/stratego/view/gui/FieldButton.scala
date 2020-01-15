package stratego.view.gui

import java.awt.image.BufferedImage

import javax.swing.ImageIcon

import scala.swing.{Button, Dimension}

class FieldButton (row: Int, column: Int) extends Button{
  preferredSize_=(new Dimension(50, 50))
  icon_=(new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB)))
  opaque = false
  contentAreaFilled = false

  def setImage(figureId: String) {
    icon_=(new StretchIcon(getClass.getResource("/StrategoImages/" + figureId + ".jpg")))
    //icon_=(new ImageIcon(new ImageIcon("./img/phasen.jpg").getImage().getScaledInstance(100, 120, java.awt.Image.SCALE_SMOOTH)))
  }
}
