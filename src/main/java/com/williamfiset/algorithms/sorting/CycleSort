import java.util.Arrays;
//  time complexity O(n^2)
// in place and stable
// useful when only distinct elements are present
// used when swaps are costly as it has minimum number of swaps
public class CycleSort {
    public static void main(String[] args) {
        int[] array = new int[]{60,44,11,9,80,23,-3};
        cycleSortDistinct(array);
        System.out.println(Arrays.toString(array));
    }
    public static void cycleSortDistinct(int[] arr){
        int index = 0;
        while (index<arr.length){
            int countMin = findMin(arr,arr[index]);
            if(countMin!=index)
                swap(arr,index,countMin);
            else
                index++;
        }
    }
    private static int findMin(int [] arr,int ele){
        int count = 0;
        for(int i:arr)if(i<ele)count++;
        return count;
    }
    private static void swap(int[] arr,int j,int i){
        int temp = arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}
