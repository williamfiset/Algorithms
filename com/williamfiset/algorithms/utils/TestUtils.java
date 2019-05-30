package com.williamfiset.algorithms.utils;

import java.util.*;

public final class TestUtils {

  // Generates a list of random values where every number is between
  // [min, max) and there are possible repeats.
  public static List<Integer> randomIntegerList(int sz, int min, int max) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(randValue(min, max));
    return lst;
  }

  // Generates a list of shuffled values where every number in the array
  // is in the range of [0, sz)
  public static List<Integer> randomUniformUniqueIntegerList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i);
    Collections.shuffle(lst);
    return lst;
  }

  // Generates a random number between [min, max)
  public static int randValue(int min, int max) {
    return min + (int) (Math.random() * ((max - min)));
  }
}
