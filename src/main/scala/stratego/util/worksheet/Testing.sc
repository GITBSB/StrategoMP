import scala.swing.event.Event
import scala.swing.{Publisher, Reactor}

class Ev extends Event

class Pub(a:Int) extends Publisher {
  def pupp = publish(new Ev)
}


class Sub(publisher: Publisher) extends Reactor {
  listenTo(publisher)
  reactions += {
    case event: Ev => println("got event")
  }
}

val p = new Pub(7)
p.pupp
