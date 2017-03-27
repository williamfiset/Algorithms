public class ModularInverse {
  
  // This function performs the extended euclidean algorithm on two numbers a and b.
  // The function returns the gcd(a,b) as well as the numbers x and y such 
  // that ax + by = gcd(a,b). This calculation is important in number theory 
  // and can be used for several things such as finding modular inverses and 
  // solutions to linear Diophantine equations.    
  static long[] egcd(long a, long b) {
    if (b == 0)
      return new long[] { a, 1, 0 };
    else {
      long[] ret = egcd(b, a % b);
      long tmp = ret[1] - ret[2] * (a / b);
      ret[1] = ret[2];
      ret[2] = tmp;
      return ret;
    }
  }

  // Returns the modular inverse of 'a' mod 'm' if it exists.
  // Make sure m > 0 and 'a' & 'm' are relatively prime.
  static Long modInv(long a, long m) {

    long[] ret = egcd(a, m);
    long gcf = ret[0];
    long x = ret[1];
    if (gcf == 1)
      return (x % m) + m;
    // throw new ArithmeticException("m <= 0, or 'a' has no multiplicative inverse mod m");
    return null;

  }  

  public static void main(String[] args) {
    
    // Prints 3 since 2*3 mod 5 = 1
    System.out.println(modInv(2, 5));

  }

}