
package margesortalgo;


public class MergeSort 
{
    
    public void mergeSort(int[] arr, int lo, int hi) 
    {
        if(lo < hi)
        {
            int mid = (lo+hi) / 2;
            mergeSort(arr, lo, mid);
            mergeSort(arr, mid+1, hi);
            merge(arr, lo, mid, hi);
        }
    }
    
    public void merge(int[] arr, int low, int mid, int high) 
    {
        int temp[] = new int[high - low +1];
        int i = low;
        int j = mid+1;
        int k = 0;
        
        while(i<= mid && j <= high) 
        {
            if(arr[i] < arr[j]) 
            {
                temp[k] = arr[i];
                i++;
                k++;
            } 
            else 
            {
                temp[k] = arr[j];
                k++;
                j++;
            }       
        }
        
        while(i <= mid) 
        {
            temp[k] = arr[i];
            i++;
            k++;
        }
        
        while(j <= high) 
        {
            temp[k] = arr[j];
            j++;
            k++;
        }
        
        int t = 0;
        for(i = low; i<=high; i++)
        {
            arr[i] = temp[t];
            t++;
        }
        
        
        
        
    }


}
