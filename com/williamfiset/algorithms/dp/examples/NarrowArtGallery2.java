// Copyright (C) William Fiset - All Rights Reserved
// Unauthorized copying of this file, via any medium is strictly prohibited
// This software is proprietary and confidential
// Written by William Fiset <william.alexandre.fiset@gmail.com>

import static java.util.Collections.*;
import static java.lang.System.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;
import java.awt.geom.*;
import java.math.*;
import java.util.*;
import java.io.*;

public class NarrowArtGallery2 {
  
  static InputReader in = new InputReader();
  static StringBuilder sb = new StringBuilder();
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

  static final int INF = 10000;

  static int min(int... values) {
    int m = Integer.MAX_VALUE;
    for (int v : values)
      if (v < m)
        m = v;
    return m;
  }

  public static void main(String[] Fiset) throws IOException {
    try {

    while(true) {
      int N = in.readInt();
      int K = in.readInt();

      if (N == 0 && K == 0) break;

      int[][] gallery = new int[N][2];

      int sum = 0;
      for(int i = 0; i < N; i++) {
        gallery[i][0] = in.readInt();
        gallery[i][1] = in.readInt();
        sum += gallery[i][0] + gallery[i][1];
      }

      int[][][] DP = new int[K+1][N+2][2];

      // Fill first two columns with infinity.
      for(int k = 1; k < K+1; k++) {
        fill(DP[k][0], INF);
        fill(DP[k][1], INF);
      }

      // Fill first row with zeros.
      // for(int i = 0; i < N+2; i++) 
        // fill(DP[0][i], 0);

      for (int k = 1; k < K+1; k++) {
        for (int i = 2; i < N+2; i++) {
          int left  = gallery[i-2][0];
          int right = gallery[i-2][1];
          int twoRowsAgo = min(DP[k-1][i-2][0], DP[k-1][i-2][1]);
          DP[k][i][0] = min(
            twoRowsAgo + left,
            DP[k-1][i-1][0] + left,
            DP[k][i-1][0]
          );
          DP[k][i][1] = min(
            twoRowsAgo + right,
            DP[k-1][i-1][1] + right,
            DP[k][i-1][1]
          );
        }
      }

      System.out.printf("%d\n", sum - min(DP[K][N+1][0], DP[K][N+1][1]));
      printMatrix(DP);
    }

    } catch (InputMismatchException e) { }
    finally { bw.flush(); }
  }

  static void printMatrix(int[][][] dp) {
    StringBuilder sb = new StringBuilder();
    for (int k = 0; k < dp.length; k++) {
      int len = 0;
      List<String> s = new ArrayList<>();
      for (int n = 0; n < dp[0].length; n++) {
        String ss = " (" + dp[k][n][0] + "," + dp[k][n][1] + ")";
        len = max(len, ss.length());
        s.add(ss);
      }
      for (String ss : s) {
        while(ss.length() != len)
          ss = " " + ss;
        sb.append(ss + ",");
      }
      sb.append('\n');
    }
    System.out.println(sb);
  }

  static <T> void inc(Map<T, Integer> map, T key) {
    Integer val = map.get(key);
    if (val == null) val = 0;
    map.put(key, val + 1);
  }

  static void p(Object o) {
    if (o == null) out.println("null");
    else if (o instanceof int[] ) out.println(Arrays.toString((int[])o));
    else if (o instanceof long[] ) out.println(Arrays.toString((long[])o));
    else if (o instanceof double[] ) out.println(Arrays.toString((double[])o));
    else if (o instanceof float[] ) out.println(Arrays.toString((float[])o));
    else if (o instanceof byte[] ) out.println(Arrays.toString((byte[])o));
    else if (o instanceof char[] ) out.println(Arrays.toString((char[])o));
    else if (o instanceof short[] ) out.println(Arrays.toString((short[])o));
    else if (o instanceof boolean[] ) out.println(Arrays.toString((boolean[])o));
    else if ((o instanceof int[][])    || (o instanceof long[][])  || (o instanceof Object[][]) ||
             (o instanceof double[][]) || (o instanceof float[][]) || (o instanceof byte[][])   || 
             (o instanceof char[][])   || (o instanceof short[][]) || (o instanceof boolean[][]))
      for (Object ar : (Object[])o) p(ar);
    else if (o instanceof Object[]) out.println(Arrays.toString((Object[])o));
    else out.println(o);
  }

}

class InputReader {
  
  private int c, buffer_sz, buf_index, num_bytes_read;
  
  private final byte[] buf;
  private final java.io.InputStream stream;
  
  private static final int DEFAULT_BUFFER_SZ = 1 << 16; // 2^16
  private static final java.io.InputStream DEFAULT_STREAM = System.in;

  private static final byte EOF   = -1; // End Of File character
  private static final byte NL    = 10; // '\n' - New Line (NL)
  private static final byte SP    = 32; // ' '  - Space character (SP)
  private static final byte DASH  = 45; // '-'  - Dash character (DOT)
  private static final byte DOT   = 46; // '.'  - Dot character (DOT)

  // Lookup tables used for optimizations
  private static byte[] bytes = new byte[58];
  private static int [] ints  = new int[58];
  private static char[] chars = new char[128];
  
  static {
    char ch = ' '; int value = 0; byte _byte = 0;
    for (int i = 48; i <  58; i++ ) bytes[i] = _byte++;
    for (int i = 48; i <  58; i++ )  ints[i] = value++;
    for (int i = 32; i < 128; i++ ) chars[i] = ch++;
  }

  // double lookup table, used for optimizations.
  private static final double[][] doubles = {
    { 0.0d,0.00d,0.000d,0.0000d,0.00000d,0.000000d,0.0000000d,0.00000000d,0.000000000d,0.0000000000d,0.00000000000d,0.000000000000d,0.0000000000000d,0.00000000000000d,0.000000000000000d},
    { 0.1d,0.01d,0.001d,0.0001d,0.00001d,0.000001d,0.0000001d,0.00000001d,0.000000001d,0.0000000001d,0.00000000001d,0.000000000001d,0.0000000000001d,0.00000000000001d,0.000000000000001d},        
    { 0.2d,0.02d,0.002d,0.0002d,0.00002d,0.000002d,0.0000002d,0.00000002d,0.000000002d,0.0000000002d,0.00000000002d,0.000000000002d,0.0000000000002d,0.00000000000002d,0.000000000000002d},        
    { 0.3d,0.03d,0.003d,0.0003d,0.00003d,0.000003d,0.0000003d,0.00000003d,0.000000003d,0.0000000003d,0.00000000003d,0.000000000003d,0.0000000000003d,0.00000000000003d,0.000000000000003d},        
    { 0.4d,0.04d,0.004d,0.0004d,0.00004d,0.000004d,0.0000004d,0.00000004d,0.000000004d,0.0000000004d,0.00000000004d,0.000000000004d,0.0000000000004d,0.00000000000004d,0.000000000000004d},        
    { 0.5d,0.05d,0.005d,0.0005d,0.00005d,0.000005d,0.0000005d,0.00000005d,0.000000005d,0.0000000005d,0.00000000005d,0.000000000005d,0.0000000000005d,0.00000000000005d,0.000000000000005d},        
    { 0.6d,0.06d,0.006d,0.0006d,0.00006d,0.000006d,0.0000006d,0.00000006d,0.000000006d,0.0000000006d,0.00000000006d,0.000000000006d,0.0000000000006d,0.00000000000006d,0.000000000000006d},        
    { 0.7d,0.07d,0.007d,0.0007d,0.00007d,0.000007d,0.0000007d,0.00000007d,0.000000007d,0.0000000007d,0.00000000007d,0.000000000007d,0.0000000000007d,0.00000000000007d,0.000000000000007d},        
    { 0.8d,0.08d,0.008d,0.0008d,0.00008d,0.000008d,0.0000008d,0.00000008d,0.000000008d,0.0000000008d,0.00000000008d,0.000000000008d,0.0000000000008d,0.00000000000008d,0.000000000000008d},        
    { 0.9d,0.09d,0.009d,0.0009d,0.00009d,0.000009d,0.0000009d,0.00000009d,0.000000009d,0.0000000009d,0.00000000009d,0.000000000009d,0.0000000000009d,0.00000000000009d,0.000000000000009d}
  };

  public InputReader () { this(DEFAULT_STREAM, DEFAULT_BUFFER_SZ); }
  public InputReader (java.io.InputStream stream) { this(stream, DEFAULT_BUFFER_SZ); }
  public InputReader (int buffer_sz) { this(DEFAULT_STREAM, buffer_sz); }

  // Designated constructor
  public InputReader (java.io.InputStream stream, int buffer_sz) {
    if (stream == null || buffer_sz <= 0) throw new IllegalArgumentException();
    buf = new byte[buffer_sz];
    this.buffer_sz = buffer_sz;
    this.stream = stream;
  }

  // Reads a single character from input. 
  // Returns the byte value of the next character in the buffer.
  // Also returns EOF if there is no more data to read
  private byte read() throws java.io.IOException {

    if (num_bytes_read == EOF) throw new java.util.InputMismatchException();

    if (buf_index >= num_bytes_read) {
      buf_index = 0;
      num_bytes_read = stream.read(buf);
      if (num_bytes_read == EOF)
        return EOF;
    }

    return buf[buf_index++];

  }

  // Reads a single byte from the input stream
  public byte readByte() throws java.io.IOException {
    return (byte) readInt();
  }

  // Reads a 32bit signed integer from input stream
  public int readInt() throws java.io.IOException {
    c = read();
    while (c <= SP) c = read(); // while c is either: ' ', '\n', EOF
    int sgn = 1, res = 0;
    if (c == DASH) { sgn = -1; c = read(); }
    do { res = (res<<3)+(res<<1); res += ints[c]; c = read(); }
    while (c > SP); // Still has digits
    return res * sgn;
  }

  // Reads a 64bit signed integer from input stream
  public long readLong() throws java.io.IOException {
    c = read();
    while (c <= SP) c = read(); // while c is either: ' ' or '\n'
    int sgn = 1;
    if (c == DASH) { sgn = -1; c = read(); }
    long res = 0;
    do { res = (res<<3)+(res<<1); res += ints[c]; c = read(); }
    while (c > SP); // Still has digits
    return res * sgn; 
  }

  // Reads everything in the input stream into a string
  public String readAll() throws java.io.IOException {

    if (num_bytes_read == EOF) return null;

    java.io.ByteArrayOutputStream result = new java.io.ByteArrayOutputStream(buffer_sz);

    // Finish writing data currently in the buffer
    result.write(buf, buf_index, num_bytes_read - buf_index);

    // Write data until into the result output stream until there is no more
    while ( (num_bytes_read = stream.read(buf)) != EOF)
      result.write(buf, 0, num_bytes_read);
      
    return result.toString("UTF-8");

  }

  // Reads a line from input stream.
  // Returns null if there are no more lines
  public String readLine() throws java.io.IOException {
    try { c=read(); } catch (java.util.InputMismatchException e) {return null; }
    if (c == NL) return ""; // Empty line
    if (c == EOF) return null; // EOF
    StringBuilder res = new StringBuilder();  
    do { res.append(chars[c]); c = read(); }
    while (c != NL && c != EOF); // Spaces & tabs are ok, but not newlines or EOF characters
    return res.toString();    
  }

  // Reads a string of characters from the input stream. 
  // The delimiter separating a string of characters is set to be:
  // any ASCII value <= 32 meaning any spaces, new lines, EOF, tabs ...
  public String readStr() throws java.io.IOException {
    
    c = 0;

    // while c is either: ' ' or '\n'
    try { while (c <= SP) c = read();

    // EOF throws exception
    } catch (java.util.InputMismatchException e) { return null; }
    
    StringBuilder res = new StringBuilder();
    do { res.append(chars[c]); c = read(); }
    while (c > SP); // Still non-space characters
    return res.toString();
  }

  // Returns an exact value a double value from the input stream.
  // This method is ~2.5x slower than readDoubleFast.
  public double readDouble() throws java.io.IOException {
    return Double.valueOf(readStr());
  }

  // Very quickly reads a double value from the input stream. However, this method only 
  // returns an approximate double value from input stream. The value is not
  // exact because we're doing arithmetic (adding, multiplication) on finite floating point numbers.
  public double readDoubleFast() throws java.io.IOException {
    c = read(); int sgn = 1;
    while (c <= SP) c = read(); // while c is either: ' ', '\n', EOF
    if (c == DASH) { sgn = -1; c = read(); }
    double res = 0.0;
    // while c is not: ' ', '\n', '.' or -1
    while (c > DOT) {res *= 10.0; res += ints[c]; c = read(); }
    if (c == DOT) {
      int i = 0; c = read();
      // while c is digit and there are < 15 digits after dot
      while (c > SP && i < 15)
      { res += doubles[ints[c]][i++]; c = read(); }
    }
    return res * sgn;
  }

  // Closes the input stream
  public void close() throws java.io.IOException {
    stream.close();
  }

}


