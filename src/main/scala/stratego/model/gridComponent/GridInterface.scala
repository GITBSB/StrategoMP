package stratego.model.gridComponent

trait GridInterface {
  def createNewGrid(): GridInterface
  def createPlayableGrid(): GridInterface
  def field(row: Int, col: Int): Field
  def size(): Int
  def assignField(row: Int, col: Int, figure: Figure): GridInterface
}
