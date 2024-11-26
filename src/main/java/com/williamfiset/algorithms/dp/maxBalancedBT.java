package dynamicProgaming_17;

public class maxBalancedBT {
	
	private static int mod = 1000000007;

	// recursive
	public static int maxBBTRecu(int h) {
		if(h==1 || h==0)
			return 1;
		
		int x = maxBBTRecu(h-1)%mod;
		int y = maxBBTRecu(h-2)%mod;
		
		return ((x*x)%mod + (2*x*y)%mod)%mod;
	}
	
	// memoization 
	
	public static int maxBBTMem(int h, int ans[]) {
		if(h==1 || h==0) {
			ans[h] = 1;
			return ans[h];
		}
		
		if(ans[h] != 0)
			return ans[h];
		
		int x = maxBBTRecu(h-1);
		int y = maxBBTRecu(h-2);
		
		ans[h] = ((x*x)%mod + (2*x*y)%mod)%mod;
		return ans[h];
		
		
	}
	
	public static int maxBBTMem(int h) {
		int ans[] = new int[h+1];
		
		return maxBBTMem(h, ans);
	}
	
	// dynamic Programming (Iterative)
	
	public static int maxBBTDP(int h) {
		int ans[] = new int[h+1];
		ans[0] = ans[1] = 1;
		
		for(int i=2; i<=h; i++) {
			int x = ans[i-1];
			int y = ans[i-2];
			
			ans[i] = ((x*x)%mod + (2*x*y)%mod)%mod;
		}
		
		return ans[h];
	}
	
	
	public static void main(String[] args) {
		int h=4;
		System.out.println(maxBBTMem(h));
		System.out.println(maxBBTDP(h));
		System.out.println(maxBBTRecu(h));
				
	}

}
