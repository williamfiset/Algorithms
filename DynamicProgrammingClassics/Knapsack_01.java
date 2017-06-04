/**
 * This file contains a dynamic programming solutions to the classic 0/1
 * knapsack problem where are you are trying to maximize the total profit of
 * items selected without exceeding the capacity of your knapsack.
 * 
 * Version 1:
 * Time Complexity: O(nW)
 * Version 1 Space Complexity: O(nW)
 *
 * Tested code against:
 * https://open.kattis.com/problems/knapsack
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
public class Knapsack_01 {
	
	/**
	 * @param maxWeight - The maximum weight of the knapsack
	 * @param W - The weights of the items
	 * @param V - The values of the items
	 * @return The maximum achievable profit of selecting a subset of
	 * the elements such that the capacity of the knapsack is not exceeded
	 **/
	public static int knapsack(int maxWeight, int [] W, int [] V) {
		
		if (W == null || V == null || W.length != V.length || maxWeight < 0) 
      throw new IllegalArgumentException("Invalid input");
		
		final int N = W.length;
		
		// Initialize a table where individual rows represent items 
		// and columns represent the weight of the knapsack
		int[][] DP = new int[N+1][maxWeight+1];
		
		for (int i = 1; i <= N; i++) {
			
			// Get the value and weight of the item
			int w = W[i-1], v = V[i-1];
			
			for (int sz = 1; sz <= maxWeight; sz++) {
				
				// Consider not picking this element
				DP[i][sz] = DP[i-1][sz];
				
				// Consider including the current element and
				// see if this would be more profitable
				if (sz >= w && DP[i-1][sz-w] + v > DP[i][sz])
					DP[i][sz] = DP[i-1][sz-w] + v;
				
			}
			
		}
		
		int sz = maxWeight;
		java.util.List <Integer> itemsSelected = new java.util.ArrayList<>();
		
		// Using the information inside the table we can backtrack and determine
		// which items wereselected during the dynamic programming phase. The idea
		// is that if DP[i][sz] != DP[i-1][sz] then the item on row i was selected
		for (int i = N; i > 0; i--) {
			if (DP[i][sz] != DP[i-1][sz]) {
				int itemIndex = i-1;
				itemsSelected.add(itemIndex);
				sz -= W[itemIndex];
			}
		}
		
		// Return the items that were selected
		// java.util.Collections.reverse(itemsSelected);
		// return itemsSelected;
		
		// Return the maximum profit
		return DP[N][maxWeight];
		
	}

	public static void main(String[] args) {
		int[] W = {3, 3, 5, 6};
		int[] V = {1, 4, 8, 5};
		System.out.println(knapsack(10,W,V));
	}
	
}














