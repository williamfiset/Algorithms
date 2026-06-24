package com.williamfiset.algorithms.sorting;

import java.util.Scanner;

public class CombSort
{

    // Function to sort list using Comb Sort
    public void combsort(int sortlist[],int gapnumber)
    {
        int n = sortlist.length;

        // initialize gap to length of the list
        int gap = n;

        // Initialize swapflag
        int swapflag = 1;

        // Keep running while gap is more than 1 and last
        // iteration caused a swap
        while (gap != 1 || swapflag == 1)
        {
            // Find next gap
            // To find gap between elements
             gap = (gap)/gapnumber;
             if (gap < 1){
                 gap=1;
             }
             System.out.println(gap);
            // Initialize swapflag
            swapflag = 0;

            // Compare all elements with current gap
            for (int i=0; i<n-gap; i++)
            {
                if (sortlist[i] > sortlist[i+gap])
                {
                    int temp = sortlist[i];
                    sortlist[i] = sortlist[i+gap];
                    sortlist[i+gap] = temp;
                    // Set swapped
                    swapflag = 1;
                }
            }
        }
    }

    /**main method, allow users enter number the want to sort, and then scan the number into a int list,
     * the program will use comb sort to sort the int list and print it out for users
    */
    public static void main(String args[])
    {
        CombSort combSort = new CombSort();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of the numbers you want to sort");
        int n = scan.nextInt();
        System.out.println("Enter number of gap you want to use in the sort");
        int n1 = scan.nextInt();
        System.out.println("Enter the numbers:");
        int[] arr = new int[n];
        for (int i=0;i<n;i++)
            arr[i]=scan.nextInt();
        combSort.combsort(arr,n1);
        System.out.println("sorted array");
        for (int i=0; i<arr.length; ++i)
            System.out.print(arr[i] + " ");

    }
}