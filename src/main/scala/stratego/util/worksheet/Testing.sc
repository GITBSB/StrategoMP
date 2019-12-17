<<<<<<< HEAD
val x= Map("x" -> 1 , "y" -> 2)
val bl = x.reduce{(a,b)=>a._1 + b._1}
=======
import scala.util.matching.Regex

val pa : Regex = ("[A-J],[1-9]").r

"B,33".matches("[A-J],[1-9]")
"A.2".matches("[A-J],[1-9]")
'D' - 'A'
>>>>>>> master
