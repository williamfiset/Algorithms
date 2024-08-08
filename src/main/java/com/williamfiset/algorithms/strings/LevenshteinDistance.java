package com.williamfiset.algorithms.strings;

public class LevenshteinDistance {

  // The Levenshtein distance algorithm calculates the minimum number of edits
  // (insertions, deletions, or substitutions) needed to transform one string
  // into another. It utilizes a matrix to store the distances between all
  // prefixes of the two strings, and iteratively fills this matrix using
  // dynamic programming. The final value in the matrix represents the
  // Levenshtein distance between the two original strings.
  public int calculateDistance(String origin, String destiny) {
    int rows = origin.length();
    int columns = destiny.length();

    if (rows == 0 && columns == 0) {
      return 0;
    } else if (rows == 0) {
      return columns;
    } else if (columns == 0) {
      return rows;
    }

    int[][] distance = new int[rows + 1][columns + 1];

    for (int i = 0; i <= rows; i++) {
      distance[i][0] = i;
    }

    for (int i = 0; i <= columns; i++) {
      distance[0][i] = i;
    }

    for (int i = 1; i <= rows; i++) {
      for (int j = 1; j <= columns; j++) {
        if (origin.charAt(i - 1) == destiny.charAt(j - 1)) {
          distance[i][j] = distance[i - 1][j - 1];
        } else {
          distance[i][j] =
              1
                  + Math.min(
                      distance[i][j - 1], Math.min(distance[i - 1][j], distance[i - 1][j - 1]));
        }
      }
    }

    return distance[rows][columns];
  }

  public static void main(String[] args) {
    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    String origin = "Rust is a good language";
    String destiny = "Java is a good language";

    System.out.println(levenshteinDistance.calculateDistance(origin, destiny));
  }
}
