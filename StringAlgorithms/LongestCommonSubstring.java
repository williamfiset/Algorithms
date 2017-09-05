/**
 * An implementation of finding the longest common 
 * substring(s) between a set of strings. 
 *
 * Time complexity: O(nlogn)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
 
import java.util.*;

public class LongestCommonSubstring {

  // Example usages
  public static void main(String[] args) {

    int k = 2;
    String[] strs = { "abcde", "habcab", "ghabcdf" };
    Set <String> set = lcs(strs, k);
    System.out.printf("LCS(s) of %s with k = %d equals = %s\n", Arrays.toString(strs), k, set);
    // LCS(s) of [abcde, habcab, ghabcdf] with k = 2 equals = [abcd, habc]

    k = 3;
    strs = new String[]{ "AAGAAGC", "AGAAGT", "CGAAGC" };
    set = lcs(strs, k);
    System.out.printf("LCS(s) of %s with k = %d equals = %s\n", Arrays.toString(strs), k, set);
    // LCS(s) of [AAGAAGC, AGAAGT, CGAAGC] with k = 3 equals = [GAAG]

    k = 2;
    strs = new String[]{ "AABC", "BCDC", "BCDE", "CDED", "CDCABC" };
    set = lcs(strs, k);
    System.out.printf("LCS(s) of %s with k = %d equals = %s\n", Arrays.toString(strs), k, set);
    // LCS(s) of [AABC, BCDC, BCDE, CDED, CDCABC] with k = 2 equals = [ABC, BCD, CDC, CDE]

  }

  // Fill inverse lookup index map to map which original string a particular
  // suffix came from. While constructing the index map also keep track of
  // the lowest ascii value and return this value.
  private static int fillIndexMap(String[] strings, int[] indexMap) {
    
    int lowestAsciiValue = Integer.MAX_VALUE;

    // Find the lowest ASCII value within the strings.
    // Also construct the index map to know which original 
    // string a given suffix belongs to.
    for (int i = 0, k = 0; i < strings.length; i++) {
      
      String str = strings[i];
      
      for (int j = 0; j < str.length(); j++) {
        int asciiVal = str.charAt(j);
        if (asciiVal < lowestAsciiValue) 
          lowestAsciiValue = asciiVal;
        indexMap[k++] = i;
      }

      // Record that the sentinel belongs to string i
      indexMap[k++] = i;

    }
    return lowestAsciiValue;

  }

  private static int[] constructText(String[] strings, final int TEXT_LENGTH, final int SHIFT) {
    
    int sentinel = 0;
    int[] T = new int[TEXT_LENGTH];

    // Construct the new text with the shifted values and the sentinels
    for(int i = 0, k = 0; i < strings.length; i++) {
      String str = strings[i];
      for (int j = 0; j < str.length(); j++)
        T[k++] = ((int)str.charAt(j)) + SHIFT;
      T[k++] = sentinel++;
    }

    return T;

  }

  private static int computeTextLength(String[] strings) {
    int textLength = 0;
    for(String str : strings) 
      textLength += str.length();
    return textLength;
  }


  private static int removeColor(Map<Integer,Integer> windowColorCountMap, int windowColorCount, int lastColor) {
    boolean removedAColor = false;
    Integer colorCount = windowColorCountMap.get(lastColor);
    if (colorCount == 1) removedAColor = true;
    windowColorCountMap.put(lastColor, colorCount - 1);
    return removedAColor ? windowColorCount - 1 : windowColorCount;
  }

  private static int addColor(Map<Integer,Integer> windowColorCountMap, int windowColorCount, int nextColor) {
    boolean addedNewColor = false;
    Integer colorCount = windowColorCountMap.get(nextColor);
    if (colorCount == null) colorCount = 0;
    if (colorCount == 0) addedNewColor = true;
    windowColorCountMap.put(nextColor, colorCount + 1);
    return addedNewColor ? windowColorCount + 1 : windowColorCount;
  }

  /**
   * Finds the Longest Common Substring (LCS) between a group of strings.
   * The current implementation takes O(nlog(n)) bounded by the suffix array construction.
   * @param strs - The strings you wish to find the longest common substring(s) between
   * @param K - The minimum number of strings to find the LCS between. K must be at least 2.
   **/
  public static TreeSet <String> lcs(String [] strings, final int K) {

    if (K <= 1) throw new IllegalArgumentException("K must be greater than or equal to 2!");

    TreeSet <String> lcss = new TreeSet<>();
    if (strings == null || strings.length <= 1) return lcss;

    // TEXT_LENGTH is the concatenated length of all the strings and the sentinels
    final int NUM_SENTINELS = strings.length;
    final int TEXT_LENGTH = computeTextLength(strings) + NUM_SENTINELS;

    int[] indexMap = new int[TEXT_LENGTH];

    final int LOWEST_ASCII = fillIndexMap(strings, indexMap);
    final int SHIFT = LOWEST_ASCII + NUM_SENTINELS + 1;

    int[] T = constructText(strings, TEXT_LENGTH, SHIFT);

    // Build suffix array and get sorted suffix indexes and lcp array
    SuffixArray suffixArray = new SuffixArray(T);
    int[] sa  = suffixArray.sa;
    int[] lcp = suffixArray.lcp;

    SlidingWindowMinimum window = new SlidingWindowMinimum(lcp);

    // Start the sliding window at the number of sentinels because those
    // all get sorted first and we want to ignore them

    // start at the baseline for the LCP values. Add +1 because one based 
    window.lo = NUM_SENTINELS + 1;
    window.hi = NUM_SENTINELS + 1;

    // Assign each string a color and maintain the color count within the window
    Map<Integer, Integer> windowColorCountMap = new HashMap<>();

    int bestLCSLength = 0;

    // Add the first color
    int firstColor = indexMap[sa[window.hi-1]];
    int windowColorCount = 1;
    windowColorCountMap.put(firstColor, 1);


    window.advance();


    // Maintain a sliding window between lo and hi
    while(window.hi-1 < TEXT_LENGTH) { // change <= ?

      // Attempt to update the LCS if we have the 
      // right amount of colors in our window
      if (windowColorCount >= K) {

        int windowLCP = window.getMin();

        if (windowLCP > 0) {

          if (bestLCSLength < windowLCP) {
            bestLCSLength = windowLCP;
            lcss.clear();
          }

          if (bestLCSLength == windowLCP) {

            // Construct the current LCS within the window interval
            int pos = sa[window.lo];
            char[] lcs = new char[windowLCP];
            for (int i = 0; i < windowLCP; i++) 
              lcs[i] = (char)(T[pos+i] - SHIFT);

            lcss.add(new String(lcs));
          }
        }


        // Update the colors in our window
        int lastColor = indexMap[sa[window.lo-1]];
        windowColorCount = removeColor(windowColorCountMap, windowColorCount, lastColor);

        window.shrink();

      // Increase the window size because we don't have enough colors
      } else {
        
        // Update the colors in our window
        int nextColor = indexMap[sa[window.hi-1]];
        windowColorCount = addColor(windowColorCountMap, windowColorCount, nextColor);

        window.advance();

      }

    }

    return lcss;

  }

  static class SuffixArray {

    // ALPHABET_SZ is the default alphabet size, this may need to be much
    // larger if you're using the LCS method with multiple sentinels
    int ALPHABET_SZ = 256, N;
    int[] T, lcp, sa, sa2, rank, tmp, c;

    public SuffixArray(String str) {    
      this(toIntArray(str));    
    }
     
    private static int[] toIntArray(String s) {   
      int[] text = new int[s.length()];   
      for(int i=0;i<s.length();i++)text[i] = s.charAt(i);   
      return text;    
    }

    // Designated constructor
    public SuffixArray(int[] text) {
      T = text;
      N = text.length;
      sa = new int[N];
      sa2 = new int[N];
      rank = new int[N];
      c = new int[Math.max(ALPHABET_SZ, N)];
      construct();
      kasai();
    }

    private void construct() {
      int i, p, r;
      for (i=0; i<N; ++i) c[rank[i] = T[i]]++;
      for (i=1; i<ALPHABET_SZ; ++i) c[i] += c[i-1];
      for (i=N-1; i>=0; --i) sa[--c[T[i]]] = i;
      for (p=1; p<N; p <<= 1) {
        for (r=0, i=N-p; i<N; ++i) sa2[r++] = i;
        for (i=0; i<N; ++i) if (sa[i] >= p) sa2[r++] = sa[i] - p;
        Arrays.fill(c, 0, ALPHABET_SZ, 0);
        for (i=0; i<N; ++i) c[rank[i]]++;
        for (i=1; i<ALPHABET_SZ; ++i) c[i] += c[i-1];
        for (i=N-1; i>=0; --i) sa[--c[rank[sa2[i]]]] = sa2[i];
        for (sa2[sa[0]] = r = 0, i=1; i<N; ++i) {
            if (!(rank[sa[i-1]] == rank[sa[i]] &&
                sa[i-1]+p < N && sa[i]+p < N &&
                rank[sa[i-1]+p] == rank[sa[i]+p])) r++;
            sa2[sa[i]] = r;
        } tmp = rank; rank = sa2; sa2 = tmp;
        if (r == N-1) break; ALPHABET_SZ = r + 1;
      }
    }

    // Use Kasai algorithm to build LCP array
    private void kasai() {
      lcp = new int[N];
      int [] inv = new int[N];
      for (int i = 0; i < N; i++) inv[sa[i]] = i;
      for (int i = 0, len = 0; i < N; i++) {
        if (inv[i] > 0) {
          int k = sa[inv[i]-1];
          while( (i + len < N) && (k + len < N) && T[i+len] == T[k+len] ) len++;
          lcp[inv[i]-1] = len;
          if (len > 0) len--;
        }
      }
    }

    public void display() {
      System.out.printf("-----i-----SA-----LCP---Suffix\n");
      for(int i = 0; i < N; i++) {
        int suffixLen = N - sa[i];
        String suffix = new String(T, sa[i], suffixLen);
        System.out.printf("% 7d % 7d % 7d %s\n", i, sa[i],lcp[i], suffix );
      }
    }

  }

  private static class SlidingWindowMinimum {

    int[] values;
    int N, lo, hi;

    Deque<Integer> deque = new ArrayDeque<>();

    public SlidingWindowMinimum(int[] values) {
      if (values == null) throw new IllegalArgumentException();
      this.values = values;
      N = values.length;
    }

    // Advances the front of the window by one unit
    public void advance() {

      // Remove all the worse values in the back of the deque
      while(!deque.isEmpty() && values[deque.peekLast()] > values[hi])
        deque.removeLast();

      // Add the next index to the back of the deque
      deque.addLast(hi);

      // Increase the window size
      hi++;

    }

    // Retracks the back of the window by one unit
    public void shrink() {

      // Decrease window size by pushing it forward
      lo++;

      // Remove elements in the front of the queue whom are no longer
      // valid in the reduced window.
      while(!deque.isEmpty() && deque.peekFirst() < lo)
        deque.removeFirst();

    }

    // Query the current minimum value in the window
    public int getMin() {
      if (lo >= hi) throw new IllegalStateException("Make sure lo < hi");
      return values[deque.peekFirst()];
    }

  }


}



