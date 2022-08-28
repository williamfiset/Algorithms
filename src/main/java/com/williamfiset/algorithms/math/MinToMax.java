package main.java.com.williamfiset.algorithms.math;
import java.util.*;

public class MinToMax {
    /*
     * Functionality to get the 
     * smallest, median and largest value 
     * from an array
     */
    public static int MaxResult(int[] array){
        int n = array[0];

        for(int i = 1; i < array.length; ++i){
            if(array[i] > n){
                n = array[i];
            }
        }
        return n;
    }
    public static float MedianResult(int[] array){
        float A = array[0];
        for(int i = 1; i < array.length; i++){
            A = A + array[i];
        }
        return (A/array.length);
    }
    public static int MinResult(int[] array){
        int n = array[0];

        for(int i = 1; i < array.length; ++i){
            if(array[i] < n){
                n = array[i];
            }
        }
        return n;
    }
    public static void main(String[] args){
        int[] array1 = {1,2,3,4,8};
        int[] array2 = {3,2,5};
        int[] array3 = {4,3,5,7};
        int[] array4 = {-1,0,-3,-9};
        System.out.println(MaxResult(array1)); //8
        System.out.println(MaxResult(array2)); //5 
        System.out.println(MaxResult(array3)); //7
        System.out.println(MaxResult(array4)); //0
        System.out.println(MedianResult(array1)); //3.6
        System.out.println(MedianResult(array2)); //3.3 
        System.out.println(MedianResult(array3)); //4.75
        System.out.println(MedianResult(array4)); //-3.25
        System.out.println(MinResult(array1)); //1
        System.out.println(MinResult(array2)); //2 
        System.out.println(MinResult(array3)); //3
        System.out.println(MinResult(array4)); //-9
    }
}
