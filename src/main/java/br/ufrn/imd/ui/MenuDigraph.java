package br.ufrn.imd.ui;


import br.ufrn.imd.solutions.SolutionsDigraph;
import java.util.Scanner;

public class MenuDigraph {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SolutionsDigraph solutions = new SolutionsDigraph();
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

            if (choice == 17 || choice == 18) {
                switch (choice) { // representation options
                    case 17:
                        solutions.solution17(fileName);
                        break;
                    case 18:
                        solutions.solution18(fileName);
                        break;
                }
                pressEnter(scanner);
                showSolutionOptions(scanner, solutions);
            } else if (choice == 0) {
                System.out.println("Saindo...");
            } else {
                System.out.println("Opção inválida. Por favor tente novamente.");
            }

            if (choice != 0) {
                pressEnter(scanner);
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void pressEnter(Scanner scanner) {
        System.out.println("\nAperte enter para continuar...");
        scanner.nextLine();
    }

    private static void showSolutionOptions(Scanner scanner, SolutionsDigraph solutions) {
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
                case 19:
                    solutions.solution19();
                    break;
                case 20:
//                    solutions.solution20();
                    break;
                case 21:
//                    solutions.solution21();
                    break;
                case 22:
                    solutions.solution22();
                    break;
                case 23:
                    solutions.solution23();
                    break;
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
        System.out.println("(17) Representação do Digrafo a partir da Matriz de Adjacências");
        System.out.println("(18) Representação do Digrafo a partir da Matriz de Incidência");
        System.out.println("(0) Sair");
        System.out.println("========================");
    }

    private static void displaySecondMenu() {
        clearConsole();
        System.out.println("=== Soluções ===");
        System.out.println("(19) Determinação do Grafo subjacente");
        System.out.println("(20) Conversão de matriz de incidência para estrela direta e vice versa");
        System.out.println("(21) Conversão de matriz de adjacência para estrela reversa e vice-versa");
        System.out.println("(22) Busca em profundidade, com determinação de profundidade de entrada e de saída de cada vértice.");
        System.out.println("(23) Aplicação usando busca em profundidade: Ordenação Topológica ");
        System.out.println("(0) Voltar ao menu principal");
        System.out.println("========================");
    }
}