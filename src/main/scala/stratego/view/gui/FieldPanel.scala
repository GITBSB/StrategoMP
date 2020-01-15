package stratego.view.gui

import stratego.model.engineComponent.{GameChanged, GameEngineInterface}

import scala.swing._
import scala.swing.event.MouseClicked

class FieldPanel (gameEngine: GameEngineInterface, row: Int, column: Int) extends Button {



  preferredSize = new Dimension(50, 50)
  border = Swing.BeveledBorder(Swing.Raised)

  update

  listenTo(mouse.clicks)
  reactions += {
    case e: GameChanged => {
      // do something?
      repaint
    }
    case MouseClicked(src, pt, mod, clicks, pops) => {
      println("Clicked on: " + row + " " + column)
      // gameFieldGrid. clickedthisField -> row,col
    }
  }



  def update = {

    repaint
  }

}