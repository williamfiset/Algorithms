
fun f1(count: Int) {
  if (count > 5) {
    return
  }
  println("Hello World!")
  f1(count + 1)
}

fun countingDownToZero(n: Int) {
  if (n <= 0) {
    println("Done counting down")
    return
  }
  println("At $n")
  countingDownToZero(n - 1)
}

// Assignment?
fun countingUp(n: Int, maxValue: Int) {
  if (n >= maxValue) {
    println("Done counting up. At or past $maxValue")
    return
  }
  println(n)
  countingUp(n+1, maxValue)
}

// Prints all the even numbers in the range [i, j]
fun printEvenNumbers(i:Int, j:Int) {
  if (i > j) {
    return
  }
  if (i % 2 == 0) {
    println("$i is even!")
  }
  printEvenNumbers(i+1, j)
}

fun main() {
  printEvenNumbers(5, 12)
}
