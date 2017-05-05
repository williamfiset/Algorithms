public class GCD {

  static long gcd(long a, long b) {
    return b == 0 ? (a < 0 ? -a : a) : gcd(b, a % b);
  }

  public static void main(String[] args) {

    System.out.println(gcd(12, 18));  // 6
    System.out.println(gcd(-12, 18)); // 6
    System.out.println(gcd(12, -18)); // 6
    System.out.println(gcd(-12,-18)); // 6

  }

}