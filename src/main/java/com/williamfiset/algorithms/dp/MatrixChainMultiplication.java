// Java Program to implement Matrix Chain Multiplication using DP
import java.util.*;

class MatrixChainMultiplication
{
	/*A function to find minimum number of multiplications required
	for the given chain of matrices*/
	static int findMultiplications(int arr[], int n)
	{
		/*Declaring a DP array where the number of multiplications 
		will keep on geeting updated*/
		//Will be ignoring the first row and first column value
		int dp[][] = new int[n][n];

		// cost will be zero when we multiply one matrix
		for (int i = 1; i < n; i++)
			dp[i][i] = 0;

		// len is matrix chain length
		for (int len = 2; len < n; len++)
		{
			for (int i = 1; i < n - len + 1; i++)
			{
				int j = i + len - 1;
				if (j == n)
					continue;
				dp[i][j] = Integer.MAX_VALUE;
				for (int k = i; k <= j - 1; k++)
				{
					int min = dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j];
					if (min < dp[i][j])
						dp[i][j] = min;
				}
			}
		}

		return dp[1][n - 1]; //This will contain the value of the minimum number of matrix chain multiplications
	}

	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter size\n");
		int n = sc.nextInt();
		int arr[] = new int[n];		//Declare an array of the given size
		System.out.println("\nEnter values\n");
		for(int i = 0; i < n; i++){
			arr[i] = sc.nextInt();
		}
		System.out.println("Minimum number of multiplications needed for the given matrix is "+findMultiplications(arr, n));
	}
}
