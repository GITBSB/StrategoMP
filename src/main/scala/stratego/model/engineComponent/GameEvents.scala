package stratego.model.engineComponent

import scala.swing.event.Event

object GameEvents {

  case object GameStartedEvent extends Event
  case object GameQuitEvent extends Event
}
