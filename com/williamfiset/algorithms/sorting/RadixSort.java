
public class radixSort
{

        static int[] radix(int []a)
        {

            // Counting sort is also the part of radix sort. So, you should have the knowledge about it.

            int b[] = new int[10];
            int sorted[] = new int[a.length];
            int max = a[0];
            int divisor = 1;

            for(int i = 0; i < sorted.length; i++)  // copy contents of a[] so that the original array is not changed
            {
                sorted[i] = a[i];
            }

            for(int i = 0; i < sorted.length; i++)  // finding max to implement counting sort. (Range of elements)
            {
                if(sorted[i] > max)
                {
                    max = sorted[i];
                }
            }


            while((max / divisor) > 0)
            {
                int count[] = new int[10];   // A single digit can be in range 0 - 9, making 10 digits in total.

                for(int i = 0; i < sorted.length; i++)   // Counting the number of digits at unit, 10, 100's etc at each iteration of while respectively.
                {
                    count[ (sorted[i] / divisor) % 10 ]++;
                }

                for(int i = 1; i < 10; i++) // Cumulative frequency
                {
                    count[i] += count[i-1];
                }

                for(int i = sorted.length - 1; i >= 0; i--) // Sorting the elements
                {
                    b[ --count[ (sorted[i] / divisor) % 10 ] ] = sorted[i];
                }

                for(int i = 0; i < sorted.length; i++) // Manipulating the array after each iteration of while loop, for one radix.
                {
                    sorted[i] = b[i];
                }

                divisor *= 10;  //To get tens, hundreds etc
            }

            return sorted;
        }


        public static void main(String[] args)
        {
            int a[] = {10, 3, 1, 44, 100, 33, 3};

            int sort[] = radix(a);

            for(int i = 0; i < sort.length; i++)
            {
                System.out.println(sort[i]);
            }
        }


}
