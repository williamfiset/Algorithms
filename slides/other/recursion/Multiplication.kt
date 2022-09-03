import kotlin.math.*

// Best way to do multiplication:
fun mul(a: Int, b : Int): Int {
  if (a == 0) {
    return 0
  }
  if (a < 0) {
    return mul(a + 1, b) - b
  }
  return mul(a - 1, b) + b;
}

fun mul2(a: Int, b: Int): Int {
  // Assumes a >= 0
  fun mul3(a: Int, b: Int): Int {
    if (a == 0) {
      return 0;
    }
    return mul3(a - 1, b) + b;
  }

  if (a < 0 && b >= 0 || a >= 0 && b < 0) {
    return -mul3(abs(a), abs(b))
  } else {
    return mul3(abs(a), abs(b))
  }
}

fun main() {
  for (i in -3..3) {
    for (j in -3..3) {
      println(i*j == mul2(i,j))
    }
  }
}
