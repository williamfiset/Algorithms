package org.example;

public class Bhaskara {
  public static void calculateRoots(double a, double b, double c) {

    // Calculate the discriminant
    double discriminant = b * b - 4 * a * c;
    

    // Check if the discriminant is negative, zero, or positive
    if (discriminant < 0) {
      // No real roots
      System.out.println("There are no real roots.");

    } else if (discriminant == 0) {
      // One real root
      double x = -b / (2 * a);
      System.out.println("There is one real root: " + x);

    } else {
      // Two real roots
      double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
      double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
      System.out.println("There are two real roots: x1 = " + x1 + ", x2 = " + x2);
    }
  }

  public static void main(String[] args) {

    // Example using formula ax²+bx+c

    double a = 2;
    double b = 4;
    double c = 2;
    calculateRoots(a, b, c); // Calling the Bhaskara method
  }
}
