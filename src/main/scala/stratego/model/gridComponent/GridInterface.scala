package stratego.model.gridComponent

trait GridInterface {
  def createNewGrid(): GridInterface
  def createPlayableGrid(): GridInterface
  def field(position: Position): Field
  def size(): Int
  def assignField(position: Position, figure: Option[Figure]): GridInterface
  def move(from: Position, to: Position): GridInterface
}
