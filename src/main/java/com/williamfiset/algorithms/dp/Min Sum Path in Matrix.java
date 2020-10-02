public class Solution {
   public int minPathSum(int[][] arr) {
		int[] dp = new int[arr[0].length + 1];

		for (int i = arr.length - 1; i >= 0; i--) {

			for (int j = arr[0].length - 1; j >= 0; j--) {

				if (i == arr.length - 1) {
					dp[j] = arr[i][j] + dp[j + 1];
				}

				else if (j == arr[0].length - 1) {
					dp[j] += arr[i][j];
				}

				else {
					dp[j] = Math.min(dp[j + 1], dp[j]) + arr[i][j];
				}

			}

		}

		return dp[0];

	}

}
