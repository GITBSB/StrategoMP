package scala.stratego.model.gridComponent

case class Matrix[T](grid: Vector[Vector[T]]) {
  def this(size: Int, filling: T) =
    this(Vector.tabulate(size, size) {(row, col) => filling})

  val size: Int = grid.size

  def field(row: Int, col: Int): T = grid(row) (col)

  def replaceField(row: Int, col: Int, field: T): Matrix[T] =
    copy(grid.updated(row, grid(row).updated(col, field)))
}
