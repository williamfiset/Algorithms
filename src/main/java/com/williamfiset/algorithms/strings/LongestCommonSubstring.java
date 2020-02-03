/**
 * An implementation of the k Longest Common Substring problem.
 *
 * <p>Video: https://youtu.be/Ic80xQFWevc Time complexity: O(nlog^2(n))
 *
 * <p>Run on command line:
 *
 * <p>Compile: $ javac -d src/main/java
 * src/main/java/com/williamfiset/algorithms/strings/LongestCommonSubstring.java
 *
 * <p>Run: $ java -cp src/main/java com/williamfiset/algorithms/strings/LongestCommonSubstring
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.strings;

import java.util.*;

public class LongestCommonSubstring {

  public static void main(String[] args) {
    String[] strings = new String[] {"abcde", "habcab", "ghabcdf"};

    // Display suffix array
    List<Integer> sentinelIndexes = new ArrayList<>();
    String t = addSentinels(strings, sentinelIndexes);
    SuffixArray sa = new SuffixArrayImpl(t);
    sa.toString(sentinelIndexes);

    // LcsSolver example.
    int k = 2;
    LcsSolver solver = new LcsSolver(strings);
    System.out.println("Longest common substrings: " + solver.getLongestCommonSubstrings(k));
  }

  // Method to add unique sentinels. Works for low number of strings. Only used for debugging.
  private static String addSentinels(String[] s, List<Integer> sentinelIndexes) {
    int token = 35; // starts at '#'
    String t = "";
    for (String string : s) {
      t += string;
      t += (char) token;
      token++;
      if (sentinelIndexes != null) sentinelIndexes.add(t.length());
    }
    return t;
  }

  public abstract static class SuffixArray {

    // Length of the suffix array
    protected final int N;

    // T is the text
    protected int[] T;

    // The sorted suffix array values.
    protected int[] sa;

    // Longest Common Prefix array
    protected int[] lcp;

    private boolean constructedSa = false;
    private boolean constructedLcpArray = false;

    public SuffixArray(int[] text) {
      if (text == null) throw new IllegalArgumentException("Text cannot be null.");
      this.T = text;
      this.N = text.length;
    }

    public int getTextLength() {
      return T.length;
    }

    // Returns the suffix array.
    public int[] getSa() {
      buildSuffixArray();
      return sa;
    }

    // Returns the LCP array.
    public int[] getLcpArray() {
      buildLcpArray();
      return lcp;
    }

    // Builds the suffix array by calling the construct() method.
    protected void buildSuffixArray() {
      if (constructedSa) return;
      construct();
      constructedSa = true;
    }

    // Builds the LCP array by first creating the SA and then running the kasai algorithm.
    protected void buildLcpArray() {
      if (constructedLcpArray) return;
      buildSuffixArray();
      kasai();
      constructedLcpArray = true;
    }

    protected static int[] toIntArray(String s) {
      if (s == null) return null;
      int[] t = new int[s.length()];
      for (int i = 0; i < s.length(); i++) t[i] = s.charAt(i);
      return t;
    }

    // The suffix array construction algorithm is left undefined
    // as there are multiple ways to do this.
    protected abstract void construct();

    // Use Kasai algorithm to build LCP array
    // http://www.mi.fu-berlin.de/wiki/pub/ABI/RnaSeqP4/suffix-array.pdf
    private void kasai() {
      lcp = new int[N];
      int[] inv = new int[N];
      for (int i = 0; i < N; i++) inv[sa[i]] = i;
      for (int i = 0, len = 0; i < N; i++) {
        if (inv[i] > 0) {
          int k = sa[inv[i] - 1];
          while ((i + len < N) && (k + len < N) && T[i + len] == T[k + len]) len++;
          lcp[inv[i]] = len;
          if (len > 0) len--;
        }
      }
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("------i------SA------LCP--------Suffix\n");

      for (int i = 0; i < N; i++) {
        int suffixLen = N - sa[i];
        char[] string = new char[suffixLen];
        for (int j = sa[i], k = 0; j < N; j++, k++) string[k] = (char) (T[j]);
        String suffix = new String(string);
        String formattedStr = String.format("% 7d % 7d % 7d %s\n", i, sa[i], lcp[i], suffix);
        sb.append(formattedStr);
      }
      return sb.toString();
    }

    // Lazy way of finding color of suffix is by comparing against all sentinel positions
    private static Color findColorFromPos(int pos, List<Integer> sentinelIndexes) {
      Color[] colors = {
        Color.GREEN,
        Color.RED,
        Color.BLUE,
        Color.YELLOW,
        Color.MAGENTA,
        Color.CYAN,
        Color.WHITE,
        Color.BLACK_BACKGROUND_BRIGHT
      };
      int colorIndex = 0;
      for (int tokenIndex : sentinelIndexes) {
        if (tokenIndex <= pos) colorIndex++;
      }
      if (colorIndex >= colors.length) {
        throw new IllegalStateException("Too many strings, not enough terminal colors :/");
      }
      return colors[colorIndex];
    }

    // Display an augmented colored SA for debugging LCS problem.
    public void toString(List<Integer> sentinelIndexes) {
      System.out.println("------i------SA------LCP--------Suffix");
      buildLcpArray();
      for (int i = 0; i < N; i++) {
        int suffixLen = N - sa[i];
        char[] string = new char[suffixLen];
        for (int j = sa[i], k = 0; j < N; j++, k++) string[k] = (char) (T[j]);
        String suffix = new String(string);

        System.out.print(findColorFromPos(sa[i], sentinelIndexes));
        String formattedStr = String.format("% 7d % 7d % 7d %s", i, sa[i], lcp[i], suffix);
        System.out.println(formattedStr + Color.RESET);
      }
    }

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    //
    // Usage:
    // System.out.println(Color.CYAN + "Hello World" + Color.RESET);
    private enum Color {
      RESET("\033[0m"),

      BLACK("\033[0;30m"),
      RED("\033[0;31m"),
      GREEN("\033[0;32m"),
      YELLOW("\033[0;33m"),
      BLUE("\033[0;34m"),
      MAGENTA("\033[0;35m"),
      CYAN("\033[0;36m"),
      WHITE("\033[0;37m"),
      BLACK_BACKGROUND_BRIGHT("\033[0;100m");

      private final String code;

      Color(String code) {
        this.code = code;
      }

      @Override
      public String toString() {
        return code;
      }
    }
  }

  static class SuffixArrayImpl extends SuffixArray {

    // Wrapper class to help sort suffix ranks
    static class SuffixRankTuple implements Comparable<SuffixRankTuple> {
      int firstHalf, secondHalf, originalIndex;

      // Sort Suffix ranks first on the first half then the second half
      @Override
      public int compareTo(SuffixRankTuple other) {
        int cmp = Integer.compare(firstHalf, other.firstHalf);
        if (cmp == 0) return Integer.compare(secondHalf, other.secondHalf);
        return cmp;
      }
    }

    public SuffixArrayImpl(String text) {
      super(toIntArray(text));
    }

    public SuffixArrayImpl(int[] text) {
      super(text);
    }

    // Construct a suffix array in O(nlog^2(n))
    @Override
    protected void construct() {
      sa = new int[N];

      // Maintain suffix ranks in both a matrix with two rows containing the
      // current and last rank information as well as some sortable rank objects
      int[][] suffixRanks = new int[2][N];
      SuffixRankTuple[] ranks = new SuffixRankTuple[N];

      // Assign a numerical value to each character in the text
      for (int i = 0; i < N; i++) {
        suffixRanks[0][i] = T[i];
        ranks[i] = new SuffixRankTuple();
      }

      // O(log(n))
      for (int pos = 1; pos < N; pos *= 2) {
        for (int i = 0; i < N; i++) {
          SuffixRankTuple suffixRank = ranks[i];
          suffixRank.firstHalf = suffixRanks[0][i];
          suffixRank.secondHalf = i + pos < N ? suffixRanks[0][i + pos] : -1;
          suffixRank.originalIndex = i;
        }

        // O(nlog(n))
        java.util.Arrays.sort(ranks);

        int newRank = 0;
        suffixRanks[1][ranks[0].originalIndex] = 0;

        for (int i = 1; i < N; i++) {
          SuffixRankTuple lastSuffixRank = ranks[i - 1];
          SuffixRankTuple currSuffixRank = ranks[i];

          // If the first half differs from the second half
          if (currSuffixRank.firstHalf != lastSuffixRank.firstHalf
              || currSuffixRank.secondHalf != lastSuffixRank.secondHalf) newRank++;

          suffixRanks[1][currSuffixRank.originalIndex] = newRank;
        }

        // Place top row (current row) to be the last row
        suffixRanks[0] = suffixRanks[1];

        // Optimization to stop early
        if (newRank == N - 1) break;
      }

      // Fill suffix array
      for (int i = 0; i < N; i++) {
        sa[i] = ranks[i].originalIndex;
        ranks[i] = null;
      }

      // Cleanup
      suffixRanks[0] = suffixRanks[1] = null;
      suffixRanks = null;
      ranks = null;
    }
  }

  public static class LcsSolver {

    // Inputs
    int k, numSentinels, textLength;
    String[] strings;

    // Internal
    int shift, lcsLen;
    int lowestAsciiValue;
    int highestAsciiValue;
    int[] imap, text, sa, lcp;

    // Output
    TreeSet<String> lcss;

    private static final boolean DEBUG_MODE = false;

    // TODO(williamfiset): support LCS with strings as int arrays for larger alphabet sizes.
    public LcsSolver(String[] strings) {
      if (strings == null || strings.length <= 1)
        throw new IllegalArgumentException("Invalid strings array provided.");
      this.strings = strings;
    }

    private void init() {
      shift = lcsLen = 0;
      lowestAsciiValue = Integer.MAX_VALUE;
      highestAsciiValue = Integer.MIN_VALUE;
      numSentinels = strings.length;
      lcss = new TreeSet<>();
      imap = text = sa = lcp = null;

      computeTextLength(strings);
      buildReverseColorMapping();
      computeShift();
      buildText();
    }

    private void computeTextLength(String[] strings) {
      textLength = 0;
      for (String str : strings) textLength += str.length();
      textLength += numSentinels;
    }

    // Builds a reverse color index map. The reverse color map tells you which
    // color a character is at a given index in the new text.
    private void buildReverseColorMapping() {
      imap = new int[textLength];
      for (int i = 0, k = 0; i < strings.length; i++) {
        String str = strings[i];
        for (int j = 0; j < str.length(); j++) {
          int asciiVal = str.charAt(j);
          if (asciiVal < lowestAsciiValue) lowestAsciiValue = asciiVal;
          if (asciiVal > highestAsciiValue) highestAsciiValue = asciiVal;
          imap[k++] = i;
        }
        // Record that the sentinel belongs to string i
        imap[k++] = i;
      }
    }

    private void verifyMinAndMaxAsciiValues() {
      if (lowestAsciiValue == Integer.MAX_VALUE || highestAsciiValue == Integer.MIN_VALUE)
        throw new IllegalStateException("Must set min/max ascii values!");
    }

    private void computeShift() {
      verifyMinAndMaxAsciiValues();
      shift = numSentinels - lowestAsciiValue;
    }

    // Build text containing sentinels. Must have computed lowest and highest ascii values
    // beforehand.
    // All sentinels values will be in the range [0, numSentinels)
    // All text values will be in the range [numSentinels, numSentinels + highestAsciiValue -
    // lowestAsciiValue]
    private void buildText() {
      verifyMinAndMaxAsciiValues();
      text = new int[textLength];
      int sentinel = 0;
      // Construct the new text with the shifted values and the sentinels
      for (int i = 0, k = 0; i < strings.length; i++) {
        String str = strings[i];
        for (int j = 0; j < str.length(); j++) {
          text[k++] = ((int) str.charAt(j)) + shift;
          if (!(numSentinels <= text[k - 1]
              && text[k - 1] <= (numSentinels + highestAsciiValue - lowestAsciiValue))) {
            throw new IllegalStateException(
                String.format(
                    "Unexpected character range. Was: %d, wanted between [%d, %d]",
                    text[k - 1],
                    numSentinels,
                    (numSentinels + highestAsciiValue - lowestAsciiValue)));
          }
        }
        text[k++] = sentinel++;
        if (!(0 <= text[k - 1] && text[k - 1] < numSentinels)) {
          throw new IllegalStateException(
              String.format(
                  "Unexpected character range. Was: %d, wanted between [%d, %d)",
                  text[k - 1], 0, numSentinels));
        }
      }
    }

    // Counts the number of suffixes of different colors between [lo, hi] and determines
    // if there is enough variety for a LCS candidate.
    private boolean enoughUniqueColorsInWindow(int lo, int hi) {
      // TODO(williamfiset): Avoid initializing a new hash set to count colors every method call.
      Set<Integer> set = new HashSet<>();
      for (int i = lo; i <= hi; i++) {
        set.add(imap[sa[i]]);
      }
      // TODO(williamfiset): Investigate if == can become >=
      return set.size() == k;
    }

    // Retrieves a string from the suffix array given a position and a length.
    private String retrieveString(int i, int len) {
      char[] s = new char[len];
      for (int j = 0; j < len; j++) s[j] = (char) (text[i + j] - shift);
      return new String(s);
    }

    private void log(String s) {
      if (DEBUG_MODE) {
        System.out.println(s);
      }
    }

    private void addLcs(int lo, int hi, int windowLcp) {
      if (hi - lo + 1 < k) {
        log(
            String.format(
                "lo: %d, hi: %d. Too small range. lo: %d, hi: %d, k: %d, hi - lo + 1 < k",
                lo, hi, lo, hi, k));
        return;
      }
      if (windowLcp == 0) {
        log("LCP is 0");
        return;
      }
      if (!enoughUniqueColorsInWindow(lo, hi)) {
        log(
            String.format(
                "lo: %d, hi: %d. Not enough unique colors in range [%d, %d]", lo, hi, lo, hi));
        return;
      }
      if (windowLcp > lcsLen) {
        lcsLen = windowLcp;
        lcss.clear();
      }
      if (windowLcp == lcsLen) {
        lcss.add(retrieveString(sa[lo], windowLcp));
      }
    }

    public TreeSet<String> getLongestCommonSubstrings(int k) {
      if (k < 2) throw new IllegalArgumentException("k must be greater than or equal to 2");
      this.k = k;
      solve();
      return lcss;
    }

    private void solve() {
      init();

      SuffixArray suffixArray = new SuffixArrayImpl(text);
      sa = suffixArray.getSa();
      lcp = suffixArray.getLcpArray();

      // TODO(williamfiset): Replace with SlidingWindowMinimum for speed.
      CompactMinSegmentTree tree = new CompactMinSegmentTree(lcp);

      int lo = numSentinels;
      int hi = numSentinels;

      while (true) {
        // Shrink the window (by increasing lo) if the current interval has enough
        // of the correct suffix colors or hi has reached the end.
        boolean shrinkWindow = (hi == textLength - 1) ? true : enoughUniqueColorsInWindow(lo, hi);

        if (shrinkWindow) {
          lo++;
        } else {
          hi++;
        }

        if (lo == textLength - 1) break;

        // Segment tree queries are right endpoint exclusive: [l, r)
        // so we must be careful to avoid the empty interval case.
        if (lo == hi) continue;

        int windowLcp = tree.query(lo + 1, hi + 1);
        addLcs(lo, hi, windowLcp);
      }
    }
  }

  static class SlidingWindowMinimum {
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
      while (!deque.isEmpty() && values[deque.peekLast()] > values[hi]) deque.removeLast();

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
      while (!deque.isEmpty() && deque.peekFirst() < lo) deque.removeFirst();
    }

    // Query the current minimum value in the window
    public int getMin() {
      if (lo >= hi) throw new IllegalStateException("Make sure lo < hi");
      return values[deque.peekFirst()];
    }
  }

  static class CompactMinSegmentTree {
    private int n;

    // Let UNIQUE be a value which does NOT and will NOT appear in the segment tree.
    private int UNIQUE = 93136074;

    // Segment tree values
    private int[] tree;

    public CompactMinSegmentTree(int size) {
      tree = new int[2 * (n = size)];
      java.util.Arrays.fill(tree, UNIQUE);
    }

    public CompactMinSegmentTree(int[] values) {
      this(values.length);
      for (int i = 0; i < n; i++) modify(i, values[i]);
    }

    // The segment tree function used for queries.
    private int function(int a, int b) {
      if (a == UNIQUE) return b;
      else if (b == UNIQUE) return a;
      return (a < b) ? a : b; // minimum value over a range
    }

    // Adjust point i by a value, O(log(n))
    public void modify(int i, int value) {
      tree[i + n] = function(tree[i + n], value);
      for (i += n; i > 1; i >>= 1) {
        tree[i >> 1] = function(tree[i], tree[i ^ 1]);
      }
    }

    // Query interval [l, r), O(log(n))
    public int query(int l, int r) {
      int res = UNIQUE;
      for (l += n, r += n; l < r; l >>= 1, r >>= 1) {
        if ((l & 1) != 0) res = function(res, tree[l++]);
        if ((r & 1) != 0) res = function(res, tree[--r]);
      }
      if (res == UNIQUE) {
        throw new IllegalStateException("UNIQUE should not be the return value.");
      }
      return res;
    }
  }
}
