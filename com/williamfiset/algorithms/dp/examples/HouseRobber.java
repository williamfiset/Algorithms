/**
 * Problem: https://leetcode.com/problems/house-robber
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 *
 * Download the code: 
 * $ git clone https://github.com/williamfiset/Algorithms
 *
 * Change directory to the root of the Algorithms directory:
 * $ cd Algorithms
 *
 * Build:
 * $ javac com/williamfiset/algorithms/dp/examples/HouseRobber.java
 *
 * Run:
 * $ java com/williamfiset/algorithms/dp/examples/HouseRobber
 */

package com.williamfiset.algorithms.dp.examples;

public class HouseRobber {
    public int rob(int[] houses) {
      int n = houses.length;
      int[] dp = new int[n+2];
      for (int i = 0, j = 2; i < n; i++, j++) {
        dp[j] = Math.max(dp[j-1], dp[j-2] + houses[i]);
        System.out.println(dp[i]);
      }
      return dp[n+1];
    }

    public static void main(String[] args) {
      HouseRobber robber = new HouseRobber();
      int[] houses = {5,2,4,7,2,13,9,1,8,4};
      int amount = robber.rob(houses);
      System.out.println("Robbed: " + amount + "$");
    }
}