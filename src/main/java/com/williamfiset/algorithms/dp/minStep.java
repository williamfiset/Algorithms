package dynamicProgaming_17;

public class minStep {
	
	// recursive
	public static int minStepRecursive(int n) {
		if(n==1)
			return 0;
		
		int ans1 = minStepRecursive(n-1);
		int ans2, ans3;
		ans2 = ans3 = Integer.MAX_VALUE;
		
		if(n%2==0)
		ans2 = minStepRecursive(n/2); 
		if(n%3==0)
			ans2 = minStepRecursive(n/3);
		return 1+Math.min(ans1, Math.min(ans2, ans3));
	}
	
	// memoization  
	
	public static int minStepMemoization(int n, int ans[]) {	
		if(n==1) {
			ans[n] = 0;
			return ans[n];
		}
		
		if(ans[n] != -1)
			return ans[n];
		
		int ans1, ans2, ans3;
		ans1 = minStepMemoization(n-1, ans);
		
		ans2 = ans3 = Integer.MAX_VALUE;
		if(n%2 == 0)
			ans2 = minStepMemoization(n/2, ans);
		if(n%3 == 0)
			ans3 = minStepMemoization(n/3, ans);
		ans[n] = 1+Math.min(ans1, Math.min(ans2, ans3));
		return ans[n];
	}
	
	public static int minStepMemoization(int n) {
		int ans[] = new int[n+1];
		for(int i=0; i<=n; i++) {
			ans[i] = -1;
		}
		
		return minStepMemoization(n, ans);
	}

	// dynamic programming (Iterative)
	public static int minStepDP(int n) {
		int ans[] = new int[n+1];
		ans[1] = 0;
		
		for(int i=2; i<=n; i++) {
			int ans1, ans2, ans3;
			ans1 = ans[i-1];
			ans2 = ans3 = Integer.MAX_VALUE;
			if(i%2 == 0)
				ans2 = ans[i/2];
			if(i%3 == 0)
				ans2 = ans[i/3];
			ans[i] = 1+Math.min(ans1, Math.min(ans2, ans3));
		}
		
		return ans[n];
	}
	
	public static void main(String[] args) {
		int n=700;
		System.out.println(minStepMemoization(n));
		System.out.println(minStepDP(n));
		System.out.println(minStepRecursive(n));
		
	}

}
