package br.ufrn.imd.ui;

import br.ufrn.imd.solutions.SolutionsGraph;

import java.util.Scanner;

public class MenuGraph {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SolutionsGraph solutions = new SolutionsGraph();
        int choice;

        do {
            displayMenu();
            System.out.print("Enter your choice (0 to exit): ");

            // Input validation
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            // Consume the leftover newline character after nextInt()
            scanner.nextLine();

            switch (choice) {
                case 1:
//                    solutions.solution1();
                    break;
                case 2:
                    System.out.print("Enter the file name: ");
                    String fileName = scanner.nextLine();
                    solutions.solution2(fileName);
                    break;
                case 3:
//                    solutions.solution3();
                    break;
                // TODO: Add more cases
                case 0:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }

            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);

        scanner.close();
    }

    // Clears the console (platform-independent)
    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void displayMenu() {
        clearConsole();

        System.out.println("=== Graph Solutions Menu ===");
        System.out.println("1. Solution 1");
        System.out.println("2. Solution 2");
        System.out.println("3. Solution 3");
        System.out.println("0. Exit");
        System.out.println("========================");
    }
}