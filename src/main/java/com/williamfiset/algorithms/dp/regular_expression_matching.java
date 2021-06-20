public class Solution {
public static int isMatch(final String a, final String b) {

		boolean[] dp = new boolean[b.length() + 1];
		boolean diag = false, prev = false;

		for (int i = a.length(); i >= 0; i--) {

			for (int j = b.length(); j >= 0; j--) {

				prev = dp[j];

				if (i == a.length() && j == b.length()) {
					dp[j] = true;
				}

				else if (i == a.length() || j == b.length()) {
					if (j == b.length())
						dp[j] = false;

					else if (b.charAt(j) == '*' && dp[j + 1])
						dp[j] = true;
				}

				else {
					char c1 = a.charAt(i);
					char c2 = b.charAt(j);

					boolean ans = false;

					if (c1 == c2 || c2 == '?') {
						ans = diag;
					}

					else if (c2 == '*') {
						ans = dp[j] || dp[j + 1] || diag;
					}

					dp[j] = ans;

				}

				diag = prev;

			}

		}
		return dp[0] ? 1 : 0;
	}

}
