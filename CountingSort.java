
public class CountingSort {

  // Sorts values in the range of [minVal, maxVal] in O(n+maxVal-maxVal)
  public static void counting_sort(int[] ar, int minVal, int maxVal) {
    int sz = maxVal - minVal + 1;
    int[] B = new int[sz];
    for (int i = 0; i < ar.length; i++) B[ar[i] - minVal]++;
    for (int i = 0, k = 0; i < sz; i++)
      while (B[i]-- > 0) ar[k++] = i + minVal;
  }

}
