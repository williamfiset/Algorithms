package com.williamfiset.algorithms.utils;

import java.util.*;

public final class TestUtils {

  // Generates an array of random values where every number is between
  // [min, max) and there are possible repeats.
  public static int[] randomIntegerArray(int sz, int min, int max) {
    int[] ar = new int[sz];
    for (int i = 0; i < sz; i++) ar[i] = randValue(min, max);
    return ar;
  }

  // Generates an array of random values where every number is between
  // [min, max) and there are possible repeats.
  public static long[] randomLongArray(int sz, long min, long max) {
    long[] ar = new long[sz];
    for (int i = 0; i < sz; i++) ar[i] = randValue(min, max);
    return ar;
  }

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

  // Generates a random number between [min, max)
  public static long randValue(long min, long max) {
    return min + (long) (Math.random() * ((max - min)));
  }
}
