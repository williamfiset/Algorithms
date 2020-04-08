package com.williamfiset.algorithms.datastructures.selforganizinglist;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    int mark = 1;
    SelfOrganizingList list = new SelfOrganizingList();

    do {
      System.out.println("Enter operation:");
      System.out.println("1. Insert element");
      System.out.println("2. Delete element");
      System.out.println("3. Search element");
      System.out.println("4. Check if list is empty");
      System.out.println("5. Get size of list");
      System.out.println("6. Print list");
      System.out.println("7. Exit");

      int choice = input.nextInt();

      switch (choice) {
        case 1:
          System.out.println("Enter integer: ");
          list.insertNode(input.nextInt());
          list.display();
          break;
        case 2:
          if (!list.isEmpty()) {
            System.out.println("Enter position to be deleted: ");
            list.deleteNode(input.nextInt());
            list.display();
          } else {
            System.out.println("List is empty");
          }
          break;
        case 3:
          if (!list.isEmpty()) {
            System.out.println("Enter integer to search for: ");
            list.search(input.nextInt());
            list.display();
          } else {
            System.out.println("List is empty");
          }
          break;
        case 4:
          if (list.isEmpty()) {
            System.out.println("List is empty");
          } else {
            System.out.println("List is not empty");
          }
          break;
        case 5:
          System.out.println("List has " + list.getSize() + " elements");
          break;
        case 6:
          list.display();
          break;
        case 7:
          mark = 0;
          input.close();
          break;
        default:
          System.out.println("Wrong choice! Please enter again: ");
      }
    } while (mark != 0);
  }
}
