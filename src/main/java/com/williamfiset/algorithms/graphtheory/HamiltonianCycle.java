package com.williamfiset.algorithms.graphtheory;

import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class HamiltonianCycle {
        /**elements used to store path and the graph **/
        int     V, pathCount;
        int[]   path;
        int[][] graph;

        //used for testing
        public void setGraph(int[][] graph1){
            this.graph=graph1;
        }

        public int[] getPath(){
            return this.path;
        }
        /** Function to find cycle
         * Take the graph as input
         * And will print "No solution" if the graph has no HamiltionianCycle
         * Function solve will do actual find paths job
         * **/
        public void findHamiltonianCycle(int[][] g)
        {
            if(g==null)throw new IllegalArgumentException("Graph cannot be null.");
            V = g.length;
            path = new int[V+1];
            Arrays.fill(path, -100);
            graph = g;
            try
            {
                path[0] = 0;
                pathCount = 1;
                solve(0);
                System.out.print("No solution");
            }
            catch (Exception e)
            {
                path[V]=path[0];
                System.out.println(e.getMessage());
                display();
            }
        }

        /** function to find paths recursively
         *Take int as input
         *Will add coordinators into the path list.
         * **/
        public void solve(int vertex) throws Exception
        {
            /** solution **/
            if (graph[vertex][0] == 1 && pathCount == V)
                throw new Exception("Solution found");
            /** all vertices selected but last vertex not linked to 0 **/
            if (pathCount == V)
                return;
            for (int i = 0; i < V; i++)
            {
                /** if connected **/
                if (graph[vertex][i] == 1)
                {
                    /** add to path **/
                    path[pathCount++] = i;
                    /** remove connection **/
                    graph[vertex][i] = 0;
                    graph[i][vertex] = 0;
                    /** if vertex not already selected solve recursively **/
                    boolean flag=false;
                    for (int j= 0; j < pathCount - 1; j++)
                        if (path[j] == i)
                            flag= true;
                    flag= false;
                    if (!flag)
                        solve(i);
                    /** restore connection **/
                    graph[vertex][i] = 1;
                    graph[i][vertex] = 1;
                    /** remove path **/
                    path[--pathCount] = -1;
                }
            }

        }


        /** display solution **/
        public void display()
        {
            System.out.print("\nThe Hamiltonian Path is: ");
            for (int i = 0; i <= V; i++)
                System.out.print(path[i % V] + " ");
            System.out.println();
        }

        /** Main function **/
        public static void main(String[] args)
        {
            Scanner scan = new Scanner(System.in);
            /** Make an object of HamiltonianCycle class **/
            HamiltonianCycle hc = new HamiltonianCycle();
            /** Accept number of vertices **/
            System.out.println("Enter number of vertices");
            int n = scan.nextInt();
            /** get graph **/
            System.out.println("Enter adjacency matrix");
            int[][] graph = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    graph[i][j] = scan.nextInt();
            hc.findHamiltonianCycle(graph);
            scan.close();
        }
    }

