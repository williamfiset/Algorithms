/**
 * Fundamental bit manipulation operations you must know
 * Time Complexity: O(1)
 * @author Micah Stairs
 **/

public class BitManipulations {

  // Sets the i'th bit to 1
  public int setBit(int set, int i) {
    return set | (1 << i);
  }

  // Checks if the i'th is set
  public boolean isSet(int set, int i) {
    return (set & (1 << i)) != 0;
  }

  // Sets the i'th bit to zero
  public int clearBit(int set, int i) {
    return set & ~(1 << i);
  }

  // Toggles the i'th bit from 0 -> 1 or 1 -> 0
  public int toggleBit(int set, int i) {
    return set ^ (1 << i);
  }

  // Returns a number with the first n bits set to 1
  public int setAll(int n) {
    return (1 << n) - 1;
  }

  // Verifies if a number n is a power of two
  public boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
  }

}