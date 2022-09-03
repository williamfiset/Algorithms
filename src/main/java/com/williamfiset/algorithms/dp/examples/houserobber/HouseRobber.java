/**
 * Problem: https://leetcode.com/problems/house-robber
 *
 * <p>Time Complexity: O(n), space Complexity: O(n)
 *
 * <p>Download the code: $ git clone https://github.com/williamfiset/Algorithms
 *
 * <p>Change directory to the root of the Algorithms directory:
 *
 * <p>$ cd Algorithms
 *
 * <p>Build: $ javac -d src/main/java
 * src/main/java/com/williamfiset/algorithms/dp/examples/HouseRobber.java
 *
 * <p>Run: $ java -cp src/main/java com/williamfiset/algorithms/dp/examples/HouseRobber
 */
package com.williamfiset.algorithms.dp.examples.houserobber;

import java.util.*;

public class HouseRobber {

  int[] dp;

  public int rob(int[] houses) {
    int n = houses.length;
    dp = new int[n + 2];
    for (int i = 0, j = 2; i < n; i++, j++) {
      dp[j] = Math.max(dp[j - 1], dp[j - 2] + houses[i]);
      System.out.println(dp[j]);
    }
    return dp[n + 1];
  }

  // Finds a set of optimal houses to rob. This method assumes
  // the rob method was already called and 'dp' is populated.
  public List<Integer> findRobbedHouses(int[] houses) {
    int n = houses.length;
    List<Integer> robbedHouses = new ArrayList<>();
    for (int i = n - 1, j = n + 1; i >= 0; i--, j--) {
      if (dp[j - 2] + houses[i] > dp[j - 1]) {
        robbedHouses.add(i);
      }
    }
    return robbedHouses;
  }

  public static void main(String[] args) {
    HouseRobber robber = new HouseRobber();
    int[] houses = {5, 2, 4, 7, 2, 13, 9, 1, 8, 4};
    int amount = robber.rob(houses);
    System.out.println("Robbed: " + amount + "$");

    List<Integer> robbedHouses = robber.findRobbedHouses(houses);
    int sum = 0;
    for (int houseIndex : robbedHouses) {
      System.out.printf("Robbed house at index %d, for %d$\n", houseIndex, houses[houseIndex]);
      sum += houses[houseIndex];
    }

    if (amount != sum) {
      System.out.println("Oh dear, something is very wrong.");
    }
  }
}
