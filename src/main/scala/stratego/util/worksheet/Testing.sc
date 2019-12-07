val x= Map("x" -> 1 , "y" -> 2)
val bl = x.reduce{(a,b)=>a._1 + b._1}