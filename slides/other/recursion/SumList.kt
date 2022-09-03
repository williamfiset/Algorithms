

fun sum(ar: IntArray): Int {
  return sum(0, ar)
}

fun sum(i: Int, ar: IntArray): Int {
  if (i >= ar.size) {
    return 0;
  }
  return ar[i] + sum(i+1, ar)
}

fun divideAndConquer(ar: List<Int>): Int {
  if (ar.size == 0) {
    return 0
  }
  return divideAndConquer(0, ar.size - 1, ar)
}

fun divideAndConquer(i:Int, j:Int, ar: List<Int>): Int {
  if (i == j) {
    return ar[i]
  }
  val mid = (i+j) / 2
  return divideAndConquer(i, mid, ar) + divideAndConquer(mid+1, j, ar)
}

fun main() {
  for (n in 0..20) {
    val ar = List(n, {1})
    println(ar)
    println(divideAndConquer(ar))
  }
}
