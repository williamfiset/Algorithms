package com.williamfiset.algorithms.graphtheory;

import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class HamiltonianCycle {
    private int[]   path;
    private int[][] graph;
    private boolean flag=false;
    public void setGraph(int[][] graph1){
        this.graph=graph1;
    }

    public int[] getPath(){
        return this.path;
    }

    public void getHamiltonCircuit(int[][] g) {
        if(g==null)throw new IllegalArgumentException("Graph cannot be null.");
        //used to record the circuit
        boolean[] used = new boolean[g.length];
        int[] path = new int[g.length];
        for(int i = 0;i < g.length;i++) {
            //initialise
            used[i] = false;
            path[i] = -1;
        }
        used[0] = true;
        //the start point
        path[0] = 0;
        //deep search from the first point
        dfs(g, path, used, 1);
        if(!flag){
            System.out.print("No solution");
        }
    }
    /*
     * step means the current walked points' number
     */
    public boolean dfs(int[][] g, int[] path, boolean[] used, int step) {
        if(step == g.length) {     //when finish all points
            if(g[path[step - 1]][0] == 1) {
                storeResult(path);
                flag=true;
                //the last step can reach the end
                for(int i = 0;i < path.length;i++)
                    System.out.print(path[i]+">");
                System.out.print(path[0]);
                System.out.println();
                return true;
            }
            return false;
        } else {
            storeResult(path);
            for(int i = 0;i < g.length;i++) {
                if(!used[i] && g[path[step - 1]][i] == 1) {
                    used[i] = true;
                    path[step] = i;
                    if(dfs(g, path, used, step + 1))
                        return true;
                    else {
                        used[i] = false;
                        path[step] = -1;
                    }
                }
            }
        }
        return false;
    }

    public void storeResult(int [] path){
        this.path=new int[path.length+1];
        for(int i=0;i< path.length;i++){
            this.path[i]=path[i];
        }
        this.path[path.length]=path[0];
    }

    public static void main(String[] args) {
        HamiltonianCycle hc = new HamiltonianCycle();
        Scanner scan = new Scanner(System.in);
            // Make an object of HamiltonianCycle class
            // Accept number of vertices
        System.out.println("Enter number of vertices");
        int n = scan.nextInt();
            // get graph
        System.out.println("Enter adjacency matrix");
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                graph[i][j] = scan.nextInt();
        hc.getHamiltonCircuit(graph);
        scan.close();
    }


}

