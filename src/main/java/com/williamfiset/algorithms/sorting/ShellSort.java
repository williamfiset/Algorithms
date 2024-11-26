/**
 * Shell sort implementation
 *
 * <p>Run with:
 *
 * <p>$ ./gradlew run -Palgorithm=sorting.ShellSort
 *
 * @author Tan Karageldi, tankarageldi@gmail.com
 */
package com.williamfiset.algorithms.sorting;

public class ShellSort implements InplaceSort{

    @Override
    public void sort(int[] values) {
        ShellSort.shellSort(values);
    }

    // The idea of shell sort is sorting the elements that are far away from each other
    // defined by the interval (inter) and progressively reducing the gap between elements, 
    // to do an insertion sort when the gap is 1.
    
    public static void shellSort(int[] array){
        int n = array.length;
        for(int inter = n/2 ; inter > 0 ; inter /= 2){
            for(int i = inter; i< n; i+= 1){
                int temp = array[i];
                int j;
                for(j = i; j >= inter && array[j - inter] > temp; j -= inter){
                    array[j] = array[j - inter];
                }
                array[j] = temp;
            }
        }

    }

    public static void main(String[] args) {
        InplaceSort sorter = new ShellSort();
        int[] array = {10, 4, 6, 8, -13, 2, 3};
        sorter.sort(array);
        // Prints:
        // [-13, 2, 3, 4, 6, 8, 10]
        System.out.println(java.util.Arrays.toString(array));
      }
    
}
