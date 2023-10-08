package dynamicProgaming_17;

public class minCostPath {
	
	// recursive
	public static int minCostRecu(int path[][], int n, int m) {
		if(n==0 && m==0) {
			return path[n][m];
		}
		if(n<0 || m<0)
			return Integer.MAX_VALUE;
		 
		
		int x = minCostRecu(path, n-1, m);
		int y = minCostRecu(path, n, m-1);
		int z = minCostRecu(path, n-1, m-1);
		
		return path[n][m]+Math.min(x, Math.min(y, z));
	}
	
	// memoization
	
	
	public static int minCostMem(int path[][], int n, int m, int ans[][]) {
		if(n==0 && m==0) {
			ans[n][m] = path[n][m];
			return ans[n][m];
		}
		if(n<0 || m<0)
			return Integer.MAX_VALUE;
		
		if(ans[n][m] != -1)
			return ans[n][m];
		
		int x = minCostMem(path, n-1, m, ans);
		int y = minCostMem(path, n, m-1, ans);
		int z = minCostMem(path, n-1, m-1, ans);
		
		ans[n][m] = path[n][m]+Math.min(x, Math.min(y, z));
		return ans[n][m];
		
	}
	public static int minCostMem(int path[][], int n, int m) {
		int ans[][] = new int[n+1][m+1];
		for(int i=0; i<=n; i++) {
			for(int j=0; j<=m; j++) {
				ans[i][j] = -1;
			}
		}
		
		return minCostMem(path, n, m, ans);
		
	}
	
	// dynamic programming (Iterative)
	public static int minCostDP(int path[][]) {
		int n = path.length;
		int m = path[0].length;
		
		int ans[][] = new int[n][m];
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				ans[i][j] = -1;
			}
		}
		
		ans[0][0] = path[0][0];
		
		for(int i=1; i<n; i++) {
			ans[i][0] = ans[i-1][0]+path[i][0];
		}
		for(int j=1; j<m; j++) {
			ans[0][j] = ans[0][j-1]+path[0][j];
		}
		
		for(int i=1; i<n; i++) {
			for(int j=1; j<m; j++) {
				int x = ans[i][j-1];
				int y = ans[i-1][j];
				int z = ans[i-1][j-1];
				ans[i][j] = path[i][j]+Math.min(x, Math.min(y, z));
			}
		}
		
		return ans[n-1][m-1];
	}
	
	public static void main(String[] args) {
		int mat[][] = { 
				       {1, 2, 4, 8},
				       {9, 5, 1, 6},
				       {3, 2, 4, 5}
		                            };
		
		System.out.println(minCostMem(mat, 2, 3));
		System.out.println(minCostDP(mat));
		System.out.println(minCostRecu(mat, 2, 3));
	}

}
