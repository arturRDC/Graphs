package br.ufrn.imd.ui;

import br.ufrn.imd.solutions.SolutionsGraph;
import java.util.Scanner;

public class MenuGraph {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SolutionsGraph solutions = new SolutionsGraph();
        int choice;

        // Ask the user to enter the file name
        System.out.print("Digite o nome do arquivo: ");
        String fileName = scanner.nextLine();
//        scanner.nextLine(); // Wait for the user to press Enter

        do {
            displayMainMenu();
            System.out.print("Digite sua escolha (0 para sair): ");

            // Input validation
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline

            if (choice >= 1 && choice <= 3) {
                switch (choice) { // representation options
                    case 1:
//                        solutions.solution1(fileName);
                        break;
                    case 2:
//                        solutions.solution2(fileName);
                        break;
                    case 3:
//                        solutions.solution3(fileName);
                        break;
                }

                showSolutionOptions(scanner, solutions);
            } else if (choice == 0) {
                System.out.println("Saindo...");
            } else {
                System.out.println("Opção inválida. Por favor tente novamente.");
            }

            if (choice != 0) {
                System.out.println("\nAperte enter para continuar...");
                scanner.nextLine();
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void showSolutionOptions(Scanner scanner, SolutionsGraph solutions) {
        int stageChoice;
        do {
            displaySecondMenu();
            System.out.print("Digite sua escolha (0 para voltar): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
            stageChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline

            switch (stageChoice) {
                case 4:
//                    solutions.solution4();
                    break;
                case 5:
//                    solutions.solution5();
                    break;
                case 6:
//                    solutions.solution6();
                    break;
                // TODO: Add more solutions
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor tente novamente.");
            }

            if (stageChoice != 0) {
                System.out.println("\nAperte enter para continuar...");
                scanner.nextLine();
            }
        } while (stageChoice != 0);
    }

    // Clears the console (platform-independent)
    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void displayMainMenu() {
        clearConsole();
        System.out.println("=== Escolha uma representação ===");
        System.out.println("(1) Representação do Grafo a partir da Lista de Adjacências");
        System.out.println("(2) Representação do Grafo a partir da Matriz de Adjacências");
        System.out.println("(3) Representação do Grafo a partir da Matriz de Incidência");
        System.out.println("(0) Sair");
        System.out.println("========================");
    }

    private static void displaySecondMenu() {
        clearConsole();
        System.out.println("=== Soluções ===");
        System.out.println("(4) Solução 4");
        System.out.println("(5) Solução 5");
        System.out.println("(6) Solução 6");
        // TODO: Add more solutions
        System.out.println("(0) Voltar ao menu principal");
        System.out.println("========================");
    }
}