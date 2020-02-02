/**
 * This snippet multiplies 2 complex polynomials very efficiently using the Fast Fourier Transform.
 *
 * <p>Time Complexity: O(nlogn)
 */
package com.williamfiset.algorithms.math;

public class FastFourierTransformComplexNumbers {

  public static Complex[] fft(Complex[] x) {
    int n = x.length;
    if (n == 1) return new Complex[] {x[0]};
    Complex[] arr = new Complex[n / 2];
    for (int k = 0; k < n / 2; k++) arr[k] = x[2 * k];
    Complex[] q = fft(arr);
    for (int k = 0; k < n / 2; k++) arr[k] = x[2 * k + 1];
    Complex[] r = fft(arr);
    Complex[] y = new Complex[n];
    for (int k = 0; k < n / 2; k++) {
      double kth = -2 * k * Math.PI / n;
      Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
      y[k] = q[k].plus(wk.times(r[k]));
      y[k + n / 2] = q[k].minus(wk.times(r[k]));
    }
    return y;
  }

  private static Complex[] polyMult(Complex[] a, Complex[] b) {
    int exp = 32 - Integer.numberOfLeadingZeros(a.length + b.length - 2);
    int length = (int) Math.pow(2, exp);
    a = pad(a, length);
    b = pad(b, length);
    Complex[] c = fft(a), d = fft(b), e = new Complex[length];
    for (int i = 0; i < length; i++) e[i] = c[i].times(d[i]);
    return ifft(e);
  }

  private static Complex[] pad(Complex[] arr, int n) {
    Complex[] padded = new Complex[n];
    for (int i = 0; i < n; i++) padded[i] = (i < arr.length) ? arr[i] : new Complex(0, 0);
    return padded;
  }

  private static Complex[] ifft(Complex[] x) {
    int n = x.length;
    Complex[] y = new Complex[n];
    for (int i = 0; i < n; i++) y[i] = x[i].conjugate();
    y = fft(y);
    for (int i = 0; i < n; i++) y[i] = y[i].conjugate();
    for (int i = 0; i < n; i++) y[i] = y[i].scalar(1.0 / n);
    return y;
  }

  public static class Complex {
    double r, i;

    public Complex(double real, double imag) {
      r = real;
      i = imag;
    }

    public Complex plus(Complex b) {
      return new Complex(r + b.r, i + b.i);
    }

    public Complex minus(Complex b) {
      return new Complex(r - b.r, i - b.i);
    }

    public Complex times(Complex b) {
      return new Complex(r * b.r - i * b.i, r * b.i + i * b.r);
    }

    public Complex scalar(double s) {
      return new Complex(r * s, i * s);
    }

    public Complex conjugate() {
      return new Complex(r, -i);
    }
  }
}
