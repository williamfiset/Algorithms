/**
 * Range queries in O(sqrt(n)) and point updates in O(1). Currently this implementation supports
 * summing over the entries in the array, but it can be modified to support many different
 * operations.
 *
 * <p>Time Complexity: Point update in O(1) and query in O(sqrt(n))
 *
 * @author Thomas Finn Lidbetter
 */
package com.williamfiset.algorithms.other;

public class SquareRootDecomposition {

  long[] arr, blocks;
  int blockSize, nBlocks;

  // Creates an empty range [0,n]
  public SquareRootDecomposition(int size) {

    blockSize = (int) Math.sqrt(size);
    nBlocks = (size / blockSize) + 1;
    blocks = new long[nBlocks];
    arr = new long[size];
  }

  public SquareRootDecomposition(int[] values) {
    this(values.length);
    for (int i = 0; i < values.length; i++) set(i, values[i]);
  }

  public int blockID(int index) {
    return index / blockSize;
  }

  // Sets [index, index] = val
  public void set(int index, int val) {
    blocks[blockID(index)] -= arr[index];
    blocks[blockID(index)] += val;
    arr[index] = val;
  }

  // Get sum query from [lo, hi] in O(sqrt(n))
  public long query(int lo, int hi) {

    long sum = 0;
    int loId = blockID(lo);
    int hiId = blockID(hi);
    for (int i = loId + 1; i < hiId; i++) sum += blocks[i];

    if (loId == hiId) {
      for (int i = lo; i <= hi; i++) sum += arr[i];
      return sum;
    }

    int loMax = (((lo / blockSize) + 1) * blockSize) - 1;
    int hiMin = (hi / blockSize) * blockSize;
    for (int i = lo; i <= loMax; i++) sum += arr[i];
    for (int i = hiMin; i <= hi; i++) sum += arr[i];

    return sum;
  }

  // Square root decomposition range query example
  public static void main(String[] args) {

    int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    SquareRootDecomposition range = new SquareRootDecomposition(values);

    // Prints: The sum from [0,8] is: 45
    System.out.printf("The sum from [%d,%d] is: %d\n", 0, 8, range.query(0, 8));

    // Prints: The sum from [2,2] is: 3
    System.out.printf("The sum from [%d,%d] is: %d\n", 2, 2, range.query(2, 2));

    // Prints: The sum from [3,4] is: 9
    System.out.printf("The sum from [%d,%d] is: %d\n", 3, 4, range.query(3, 4));

    // Prints: The sum from [1,6] is: 27
    System.out.printf("The sum from [%d,%d] is: %d\n", 1, 6, range.query(1, 6));
  }
}
