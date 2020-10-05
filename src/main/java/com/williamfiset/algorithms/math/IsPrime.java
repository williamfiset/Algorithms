/**
 * Tests whether a number is a prime number or not Time Complexity: O(sqrt(n))
 *
 * @author Micah Stairs, William Fiset
 */
import java.util.Scanner;
package com.williamfiset.algorithms.math;

public class IsPrime {
  static int temp=0;

  public static boolean isPrime(final long n) {
    if (n < 2) return false;
    if (n == 2 || n == 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;

    long limit = (long) Math.sqrt(n);

    for (long i = 5; i <= limit; i += 6) {
      if (n % i == 0 || n % (i + 2) == 0) {
        return false;
      }
    }
    return true;
  }
  
  public static void checkPrime(final long n){
    if(n===0 || n==1){
      System.out.println(n+" is not a prime number");
    } else{
      for(int i=2;i<=n-1;i++){
        if(n%i==0){
          temp +=temp;
        }
      }
      if(temp==0){
        System.out.println(n+" is a prime number");
      } else{
        System.out.println(n+" is not a prime number");
      }
    }
  }
    

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
     System.out.println("Enter the number");
     checkPrime(scn.nextInt()); 
     System.out.println(isPrime(5));
     System.out.println(isPrime(31));
     System.out.println(isPrime(1433));
     System.out.println(isPrime(8763857775536878331L));
  }
}
