package stratego.model.engineComponent

import scala.swing.event.Event

object GameEvent {
  case object GameStartedEvent extends Event
  case object GameQuitEvent extends Event
  case object FigureSetEvent extends Event
}
