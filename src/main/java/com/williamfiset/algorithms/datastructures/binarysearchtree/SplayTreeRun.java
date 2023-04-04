package com.williamfiset.algorithms.datastructures.binarysearchtree;

import java.util.Scanner;

public class SplayTreeRun {

    public static void main(String[] args) {

        SplayTree<Integer> splayTree = new SplayTree<>();
        Scanner scanner = new Scanner(System.in);
        int[] data = {2, 29, 26, -1, 10, 0, 2, 11};
        int choice = 0;
        for (int i : data) {
            splayTree.insertNode(i);
        }

        while (choice != 7) {
            System.out.println("1. Insert 2. Delete 3. Search 4.FindMin 5.FindMax 6. PrintTree 7. Exit");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter Data :");
                    splayTree.insertNode(scanner.nextInt());
                    break;
                case 2:
                    System.out.println("Enter Element to be Deleted:");
                    splayTree.deleteNode(scanner.nextInt());
                    break;
                case 3:
                    System.out.println("Enter Element to be Searched and Splayed:");
                    splayTree.searchNode(scanner.nextInt());
                    break;
                case 4:
                    System.out.println("Min: " + splayTree.findMinimumNode());
                    break;
                case 5:
                    System.out.println("Max: " + splayTree.findMaximumNode());
                    break;
                case 6:
                    System.out.println(splayTree);
                    break;
                case 7:
                    scanner.close();
                    break;
            }
        }
    }
}
