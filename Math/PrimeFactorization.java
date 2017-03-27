
import java.util.*;

public class PrimeFactorization {

  static ArrayList<Long> primeFactorization(long n) {
    ArrayList<Long> factors = new ArrayList<Long>();
    if (n <= 0) throw new IllegalArgumentException();
    else if (n == 1) return factors;
    PriorityQueue<Long> divisorQueue = new PriorityQueue<Long>();
    divisorQueue.add(n);
    while (!divisorQueue.isEmpty()) {
      long divisor = divisorQueue.remove();
      if (isPrime(divisor)) {
        factors.add(divisor);
        continue;
      }
      long next_divisor = pollardRho(divisor);
      if (next_divisor == divisor) {
        divisorQueue.add(divisor);
      } else {
        divisorQueue.add(next_divisor);
        divisorQueue.add(divisor / next_divisor);
      }
    }
    return factors;
  }

  static long pollardRho(long n) {
    if (n % 2 == 0) return 2;
    // Get a number in the range [2, 10^6]
    long x = 2 + (long)(999999 * Math.random());
    long c = 2 + (long)(999999 * Math.random());
    long y = x;
    long d = 1;
    while (d == 1) {
      x = (x * x + c) % n;
      y = (y * y + c) % n;
      y = (y * y + c) % n;
      d = gcf(Math.abs(x - y), n);
      if (d == n) break;
    }
    return d;
  }

  static long gcf(long a, long b) {
    return b == 0 ? a : gcf(b, a % b);
  }

  static boolean isPrime(long n) {

    if (n < 2)
      return false;
    if (n == 2 || n == 3)
      return true;
    if (n % 2 == 0 || n % 3 == 0)
      return false;

    int limit = (int) Math.sqrt(n);

    for (int i = 5; i <= limit; i += 6)
      if (n % i == 0 || n % (i + 2) == 0)
        return false;

    return true;

  }

  public static void main(String[] args) {
    
    System.out.println(primeFactorization(7));         // [7]
    System.out.println(primeFactorization(100));       // [2,2,5,5]
    System.out.println(primeFactorization(666));       // [2,3,3,37]
    System.out.println(primeFactorization(872342345)); // [5, 7, 7, 67, 19, 2797]

  }

}
