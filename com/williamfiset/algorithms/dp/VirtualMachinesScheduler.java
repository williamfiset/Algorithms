package com.williamfiset.algorithms.dp;

/**
 * Cloud computing companies, like Amazon, have a lot of virtual machines in their disposal, which
 * they offer to users for a price for the total time of the VM usage. There are quite a few
 * different types of VMs with different CPU and Disk speeds as well as storage sizes, which all
 * have different costs. For example VM-A has faster CPUs than VM-B, but VM-B has faster storage
 * read/write. Then, there is VM-C which is faster than both VM-A and VM-B in both CPU and Disk
 * speeds but is considerably more expensive. The problem that arises is how to assign the processes
 * to the VMs efficiently and we achieve that with dynamic programming.
 *
 * We have a complex process that consists of N steps in a chain: P1 -> P2 -> P3 -> ... -> Pn We also have M different VMs.
 * As an input we have 2 arrays, one being NxM showing the cost of a process running in a VM, and the other being MxM showing the cost of sending data from one VM to an other.
 *
 * E.g
 *
 * 4 Processes 3 Virtual Machines
 *
 * 5 6 3      Each process has 4 steps and there are 3 VMs.
 * 7 8 5      The first VM runs the 4 processes with 5, 7, 7, 2 cost respectively and so on.
 * 7 8 3
 * 2 7 6
 *
 * 0 7 2
 * 7 0 2
 * 2 2 0
 *
 * The Algorithm fills an array Costs NxM, where each cell Cost(i, j) contains the minimum total cost of the complex process until step i when it runs on VM-j. In order for step i to run on VM-j, the results from i-1 step must already be on VM-j or sent on VM-j including the communication cost between VMs.
 *
 * For the example above the Cost array is the following:
 *
 * 5 6 3
 * 12 13 8
 * 17 18 11
 * 15 20 17
 *
 * This means that the least expensive execution costs 15 and is achieved when the last process is on the first VM.
 *
 *
 * In order to run the program create a jar file and give as input a text file with 2 arrays in it, in the format as the arrays in the example above separated with a blank line.
 * Or change scheduler.scanFile(args[0]) to scheduler.scanFile("<filename>.txt") and run it in an IDE. (IntelliJ IDEA preferably)
 *
 * @author Dragatis Nikolas, nikosdraga@gmail.com
 */
import java.util.ArrayList;
import java.util.Collections;


public class VirtualMachinesScheduler {
  public static void main(String[] args) throws Exception {

    double time = System.currentTimeMillis();
    Scheduler scheduler = new Scheduler();

    scheduler.scanInput(args);

    System.out.println("\nTime: " + (System.currentTimeMillis() - time) / 1000.0 + " seconds.");
  }

  /**
   * This is the class that contains the methods for producing the final array of the dynamic
   * programming after reading and storing the data from a txt file.
   */
  public static class Scheduler {
    private int virtualMachines = 0;
    private int processes = 0;

    /**
     * This function is used to scan the txt file, with the data, initialize the dimensions of
     * the array with the processes costs and the array with the communication costs.
     * In the end it prints the results to the user.
     *
     * @param input The string array of arguments goven to the program.
     * @throws Exception
     */
    public void scanInput(String input[]) throws Exception {

      processes = Integer.parseInt(input[0]);
      virtualMachines = Integer.parseInt(input[1]);

      int[][] processesCost = new int[processes][virtualMachines];
      int[][] communicationsCost = new int[virtualMachines][virtualMachines];

      initializeArrays(input, processesCost, communicationsCost, processes, virtualMachines);

      printArray(Scheduling(processesCost, communicationsCost), processes, virtualMachines);
    }

    /**
     * This function is used to produce the final array of the dynamic programming with the results.
     *
     * @param processesCost is the array which contains each process cost on each virtual machine.
     * @param communicationsCost is the array which contains the costs to transfer data from one
     *     virtual machine to another.
     */
    public int[][] Scheduling(int[][] processesCost, int[][] communicationsCost) {
      int[][] Cost = new int[processes][virtualMachines];

      if (virtualMachines >= 0) {
        System.arraycopy(processesCost[0], 0, Cost[0], 0, virtualMachines);
      }

      for (int i = 1; i < processes; i++) {
        for (int j = 0; j < virtualMachines; j++) {
          Cost[i][j] = findMin(i, j, Cost, processesCost, communicationsCost, virtualMachines);
        }
      }
      return Cost;
    }

    /**
     * This function returns the minimum cost for executing a particular process on different
     * virtual machines considering the communication cost and the cost of the previous process.
     *
     * @param x is the number of processes.
     * @param y is the number of virtual machines.
     * @param Cost is the final array of the dynamic programming.
     * @param processesCost is the array that contains the costs of each process for each virtual
     *     machine.
     * @param communicationsCost is the array that contains the costs for transferring the results
     *     of the last process to the next virtual machine.
     * @param virtualMachines is the number of virtual machines.
     * @return returns the minimum cost for executing a process on every virtual machine.
     */
    private int findMin(
        int x,
        int y,
        int[][] Cost,
        int[][] processesCost,
        int[][] communicationsCost,
        int virtualMachines) {

      ArrayList<Integer> tempArray2 = new ArrayList<>();

      for (int i = 0; i < virtualMachines; i++) {
        int temp = Cost[x - 1][i] + processesCost[x][y] + communicationsCost[y][i];
        tempArray2.add(temp);
      }

      Collections.sort(tempArray2);

      return tempArray2.get(0);
    }

    /**
     * This function is used to store the two arrays (processCost and communicationCost) that are
     * read from the input file.
     *
     * @param input The String of arguments given to the program.
     * @param processes is the number of processes as read from the txt file.
     * @param virtualMachines is the number of virtual machines as read from the txt file.
     */
    private void initializeArrays(String input[], int[][] processesCost, int[][] communicationsCost, int processes, int virtualMachines) {
      int i;
      int j = 0;

      for (i = 0; i < processes; i++) {
        for (j = 0; j < virtualMachines; j++) {
          processesCost[i][j] = Integer.parseInt(input[i + j + 2]);
        }
      }

      for (int k = 0; k < virtualMachines; k++)
        for (int l = 0; l < virtualMachines; l++)
          communicationsCost[k][l] = Integer.parseInt(input[i + j + 2 + k + l]);
    }

    /**
     * This function is used to print an array with x * y dimensions.
     *
     * @param array An array to be printed.
     * @param x is the number of rows of the array.
     * @param y is the number of columns of the array.
     */
    private void printArray(int[][] array, int x, int y) {
      for (int i = 0; i < x; i++) {
        for (int j = 0; j < y; j++) {
          System.out.printf("%d ", array[i][j]);
        }
        System.out.println();
      }
    }
  }
}
