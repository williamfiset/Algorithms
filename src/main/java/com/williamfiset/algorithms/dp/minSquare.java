package dynamicProgaming_17;

public class minSquare {
	
	// recursive
	public static int minSquareRecursive(int n) {
		if(n==1) {
			return 1;
		}
		
		int ans=Integer.MAX_VALUE;
		for(int i=1; i*i<n; i++) {
			ans = Math.min(ans, minSquareRecursive(n-i*i));
		}
		return ans+1;
	}
	

	// memoization 
	
	public static int minSquareMem(int n, int ans[]) {
		if(n==1) {
			ans[n] = 1;
			return ans[n];
		}
		
		if(ans[n] != 0) {
			return ans[n];
		}
		
		int value = Integer.MAX_VALUE;
		for(int i=1; i*i<n; i++) {
			value = Math.min(value, minSquareMem(n-i*i,ans));
		}
		ans[n] = 1+value;
		return ans[n];
	}

	public static int minSquareMem(int n) {
		int ans[] = new int[n+1];
		return minSquareMem(n, ans);
	}
	
	// dynamic programming (Iterative)

	public static int minSquareDP(int n) {
		int ans[] = new int[n+1];
		ans[1] = 1;
		
		for(int i=2; i<=n; i++) {
			int value=Integer.MAX_VALUE;
			for(int j=1; j*j<i; j++) {
				value = Math.min(value, ans[i-j*j]);
			}
			ans[i] = 1+value;
		}
		
		return ans[n];
	}
	

	public static void main(String[] args) {
		int n = 60;
		System.out.println(minSquareMem(n));
		System.out.println(minSquareDP(n));
		System.out.println(minSquareRecursive(n));
	}

}
