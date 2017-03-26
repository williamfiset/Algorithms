/**
 * Tests whether a number is a prime number or not
 * @author Micah Stairs
 **/

public class IsPrime {

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
    
    System.out.println(isPrime(5));
    System.out.println(isPrime(31));
    System.out.println(isPrime(1433));

  }

}