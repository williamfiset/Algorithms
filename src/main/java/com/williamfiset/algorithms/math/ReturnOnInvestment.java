/**
 * Calculates Return on Investment (ROI) as a percentage.
 *
 * <p>ROI measures the profitability of an investment relative to its cost.
 *
 * <p>Formula: ROI = (Gain - Cost) / Cost × 100
 *
 * <p>Reference: https://www.investopedia.com/terms/r/returnoninvestment.asp
 *
 * @author InukaWijerathna
 */
package com.williamfiset.algorithms.math;

public class ReturnOnInvestment {

  public static double roi(double gainFromInvestment, double costOfInvestment) {
    if (costOfInvestment <= 0) {
      throw new IllegalArgumentException("costOfInvestment must be greater than 0");
    }
    return (gainFromInvestment - costOfInvestment) / costOfInvestment * 100.0;
  }

  public static void main(String[] args) {
    System.out.println(roi(1000, 500));  // 100.0
    System.out.println(roi(500, 500));   // 0.0
    System.out.println(roi(200, 500));   // -60.0
    System.out.println(roi(0, 500));     // -100.0
  }
}
