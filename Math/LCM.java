public class LCM {
  
  // Finds the greatest common divisor of a and b
  static long gcd(long a, long b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  // Finds the least common multiple of a and b
  static long lcm(long a, long b) {
    return (a / gcd(a, b)) * b;
  }

  public static void main(String[] args) {
    System.out.println(lcm(12, 18)); // 36
  }

}