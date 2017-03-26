/** 
 * This snippet multiplies 2 polynomials with NON NEGATIVE coefficients
 * very efficiently using the Fast Fourier Transform. NOTE: This code only 
 * works for polynomials with coefficients in the range [0, 2013265920].
 * 
 * Copyright 2014 David Eisenstat <eisenstatdavid@gmail.com>
 * Released under http://opensource.org/licenses/MIT
 * simplertimes August 2014 version
 **/

class FFT {

  public static final int LOG_MAX_LENGTH = 27;
  public static final int MODULUS = 2013265921;
  private static final int PRIMITIVE_ROOT = 137;
  private static final int PRIMITIVE_ROOT_INVERSE = 749463956;

  static int addMultiply(int x, int y, int z) {
    return (int)((x + y * (long) z) % MODULUS);
  }

  private static int[] transform(int[] a, int logN, int primitiveRoot) {
    int[] tA = new int[1 << logN];
    for (int j = 0; j < a.length; j++) {
      int k = j << (32 - logN);
      k = ((k >>> 1) & 0x55555555) | ((k & 0x55555555) << 1);
      k = ((k >>> 2) & 0x33333333) | ((k & 0x33333333) << 2);
      k = ((k >>> 4) & 0x0f0f0f0f) | ((k & 0x0f0f0f0f) << 4);
      k = ((k >>> 8) & 0x00ff00ff) | ((k & 0x00ff00ff) << 8);
      tA[(k >>> 16) | (k << 16)] = a[j];
    }
    int[] root = new int[LOG_MAX_LENGTH];
    root[root.length - 1] = primitiveRoot;
    for (int i = root.length - 1; i > 0; i--) {
      root[i - 1] = addMultiply(0, root[i], root[i]);
    }
    for (int i = 0; i < logN; i++) {
      int twiddle = 1;
      for (int j = 0; j < (1 << i); j++) {
        for (int k = j; k < tA.length; k += 2 << i) {
          int x = tA[k];
          int y = tA[k + (1 << i)];
          tA[k] = addMultiply(x, twiddle, y);
          tA[k + (1 << i)] = addMultiply(x, MODULUS - twiddle, y);
        }
        twiddle = addMultiply(0, root[i], twiddle);
      }
    }
    return tA;
  }

  public static int[] multiply(int[] a, int[] b) {
    int minN = a.length - 1 + b.length;
    int logN = 0;
    while ((1 << logN) < minN) {
      logN++;
    }
    int[] tA = transform(a, logN, PRIMITIVE_ROOT);
    int[] tB = transform(b, logN, PRIMITIVE_ROOT);
    int[] tC = tA;
    for (int j = 0; j < tC.length; j++) {
      tC[j] = addMultiply(0, tA[j], tB[j]);
    }
    int[] nC = transform(tC, logN, PRIMITIVE_ROOT_INVERSE);
    int[] c = new int[minN];
    int nInverse = MODULUS - ((MODULUS - 1) >>> logN);
    for (int j = 0; j < c.length; j++) {
      c[j] = addMultiply(0, nInverse, nC[j]);
    }
    return c;
  }

}

public class FastFourierTransform {

  public static void main(String[] args) {
    
    // 1*x^0 + 5*x^1 + 3*x^2 + 2*x^3
    int[] polynomial1 = { 1, 5, 3, 2 };

    // 0*x^0 + 0*x^1 + 6*x^2 + 2*x^3 + 5*x^4
    int[] polynomial2 = { 0, 0, 6, 2, 5};

    // Multiply the polynomials using the FFT algorithm
    int[] result = FFT.multiply(polynomial1, polynomial2);

    // Prints [0, 0, 6, 32, 33, 43, 19, 10] or equivalently
    // 6*x^2 + 32*x^3 + 33*x^4 + 43*x^5 + 19*x^6 + 10*x^7
    System.out.println(java.util.Arrays.toString(result));

  }

}

