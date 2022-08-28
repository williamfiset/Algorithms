val R = 7
val C = 10
var matrix: Array<IntArray> = arrayOf<IntArray>(
  intArrayOf(0,0,0,0,0,0,0,0,0,0),
  intArrayOf(1,0,0,1,1,1,0,1,0,0),
  intArrayOf(0,1,0,0,0,0,1,1,0,0),
  intArrayOf(0,0,1,1,1,0,0,0,0,0),
  intArrayOf(0,0,1,0,1,1,0,1,1,1),
  intArrayOf(0,0,1,1,0,0,1,1,0,0),
  intArrayOf(0,0,1,0,0,0,0,1,0,0),
);

fun fill(r: Int, c: Int) {
  if (r < 0 || r >= R || c < 0 || c >= C) {
    return
  }
  if (matrix[r][c] > 0) {
    return
  }
  // Mark cell as visited
  matrix[r][c] = 2;
  fill(r-1, c)
  fill(r+1, c)
  fill(r, c-1)
  fill(r, c+1)
}

fun main() {
  var components = 0
  for (r in 0..R-1) {
    for (c in 0..C-1) {
      if (matrix[r][c] == 0) {
        components++
        fill(r, c)
      }
    }
  }
  println("Components = $components")
}
