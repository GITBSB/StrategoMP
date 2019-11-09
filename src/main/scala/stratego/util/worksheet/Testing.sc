import scala.math.sqrt

val sizeRowCol = sqrt(81).toInt

val line = ("H" * sizeRowCol) + "\n"

var stringGrid = ""


for {
  row <- 0 until 10
  col <- 0 until 10
} {
  stringGrid = stringGrid + "["+row+col+"]"
  if (col == 9) stringGrid = stringGrid + " " + row +"\n"
}
stringGrid += "\n- A - B - C - D - E - F - G - H - I - J";
println(stringGrid)