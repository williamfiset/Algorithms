package com.williamfiset.algorithms.graphtheory;

public class FindAllCliques {
    static int size = 10000;
    static int[] vertices = new int[size];
    static int n;
    static int[][] graph = new int[size][size];
    static int[] degree = new int[size];

    /**
     * Function that checks if the given set of vertices
     * in store array is a clique or not
     * @param count number of vertices in the vertices array
     * @return true id the current subgraph is a clique
     *         false otherwise
     */
    static boolean isClique(int count) {
        for (int i = 1; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                // If any edge is missing
                if (graph[vertices[i]][vertices[j]] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *  Function that prints the clique
     * @param n number of nodes in the clique
     */
    static void print(int n) {
        for (int i = 1; i < n; i++) {
            if (i < n-1) {
                System.out.print(vertices[i] + ", ");
            } else {
                System.out.print(vertices[i] + ";");
                System.out.println();
            }
        }
    }

    /**
     * Function that finds all the cliques of size s
     * @param i
     * @param position
     * @param s the vertex count of the searched clique
     */
    static void findCliques(int i, int position, int s) {
        // Check if any vertices from i+1 can be inserted
        for (int j = i + 1; j <= n - (s - position); j++) {

            // If the degree of the graph is sufficient
            if (degree[j] >= s - 1) {

                // Add the vertex to store
                vertices[position] = j;

                // If the graph is not a clique of size k
                // then it cannot be a clique
                // by adding another edge
                if (isClique(position + 1)) {

                    // If the length of the clique is
                    // still less than the desired size
                    if (position < s) {
                        findCliques(j, position + 1, s);
                    } else {
                        print(position + 1);
                    }
                }
            }
        }
    }

    /**
     * Function that finds all cliques
     * @param edges
     * @param n
     */
    public static void findAllCliques(int[][] edges, int n){
        for (int[] edge : edges) {
            graph[edge[0]][edge[1]] = 1;
            graph[edge[1]][edge[0]] = 1;
            degree[edge[0]]++;
            degree[edge[1]]++;
        }

        for (int i =1; i<= n; i++) {
            findCliques(0, 1, i);
        }
    }

    public static void main(String[] args){
        int[][] edges = {
                { 1, 2 },
                { 2, 3 },
                { 3, 1 },
                { 4, 3 },
                { 4, 5 },
                { 5, 3 },
        };
        n = 5;

        findAllCliques(edges, n);
    }
}