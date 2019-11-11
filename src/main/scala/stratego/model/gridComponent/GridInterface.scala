package stratego.model.gridComponent

trait GridInterface {

  def createNewGameGrid(): GridInterface
  def createPlayableGrid(): GridInterface
  def field(row: Int, col: Int): FieldInterface
  def size: Int
}
