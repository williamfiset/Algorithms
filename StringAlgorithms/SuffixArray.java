
import static java.lang.Math.*;
import java.util.*;
import java.io.*;

public class SuffixArrayFast {

  final char [] T;
  final int N, MAX_N;
  int [] RA, SA, tmpRA, tmpSA, C, LCP; 

  public SuffixArrayFast(String s) {
    this(s.toCharArray());
  }

  public SuffixArrayFast(char[] text) {
    N = text.length;
    MAX_N = Math.max(300, N + 300);
    C = new int[MAX_N];
    RA = new int[MAX_N];
    SA = new int[MAX_N];
    tmpRA = new int[MAX_N];
    tmpSA = new int[MAX_N];
    T = new char[MAX_N];
    for(int i = 0; i < N; i++) {
      RA[i] = T[i] = text[i];
      SA[i] = i;
    }
    construct();
  }

  private void construct() {
    int i = 0, r = 0;
    for(int k = 1; k < N; k <<= 1) {
      countingSort(k);
      countingSort(0);
      tmpRA[SA[0]] = r = 0;
      for(i = 1; i < N; i++) {
        tmpRA[SA[i]] = (RA[SA[i]] == RA[SA[i-1]] &&
                      RA[SA[i]+k] == RA[SA[i-1]+k] ) ? r : ++r;
      }
      for(i = 0; i < N; i++) RA[i] = tmpRA[i];
      if (RA[SA[N-1]] == N-1) break;
    }
  }

  private void countingSort(int k) {
    int i, sum, maxi = Math.max(300, N);
    for(i = 0; i < maxi; i++) C[i] = 0;
    for(i = 0; i < N; i++) C[i + k < N ? RA[i+k] : 0]++;
    for(i = sum = 0; i < maxi; i++) {
      int tmp = C[i]; C[i] = sum; sum += tmp;
    }
    for(i = 0; i < N; i++) tmpSA[C[SA[i]+k < N ? RA[SA[i]+k] : 0]++] = SA[i];
    for(i = 0; i < N; i++) SA[i] = tmpSA[i];
  }

  // Constructs the LCP (longest common prefix) array in linear time - O(n)
  private void kasai() {

    LCP = new int[N];
    
    // Compute inverse index values
    int [] inv = new int[N];
    for (int i = 0; i < N; i++) inv[SA[i]] = i;
    
    int len = 0;
    for (int i = 0; i < N; i++) {
      if (inv[i] > 0) {
        int k = SA[inv[i]-1];
        while( (i + len < N) && (k + len < N) && T[i+len] == T[k+len] ) len++;
        LCP[inv[i]-1] = len;
        if (len > 0) len--;
      }
    }

  }

  // Runs on O(mlog(n)) where m is the length of the substring
  // and n is the length of the text.
  // NOTE: This is the naive implementation. There exists an
  // implementation which runs in O(m + log(n)) time
  public boolean contains(String substr) {

    if (substr == null) return false;
    if (substr.equals("")) return true;

    String suffix_str;
    int lo = 0, hi = N - 1;
    int substr_len = substr.length();

    while( lo <= hi ) {

      int mid = (lo + hi) / 2;
      int suffix_index = SA[mid];
      int suffix_len = N - suffix_index;

      // Extract part of the suffix we need to compare
      if (suffix_len <= substr_len) suffix_str = new String(T, suffix_index, suffix_len);
      else suffix_str = new String(T, suffix_index, substr_len);
       
      int cmp = suffix_str.compareTo(substr);

      // Found a match
      if ( cmp == 0 ) {
        return true;
      } else if (cmp < 0) {
        lo = mid + 1;
      } else { hi = mid - 1; }
    }
    return false;
  }

}













