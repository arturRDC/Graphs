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
                        solutions.solution1(fileName);
                        break;
                    case 2:
                        solutions.solution2(fileName);
                        break;
                    case 3:
//                        solutions.solution3(fileName);
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
                case 7:
//                    solutions.solution7();
                    break;
                case 8:
//                    solutions.solution8();
                    break;
                case 9:
//                    solutions.solution9();
                    break;
                case 10:
//                    solutions.solution10();
                    break;
                case 11:
//                    solutions.solution11();
                    break;
                case 12:
//                    solutions.solution12();
                    break;
                case 13:
//                    solutions.solution13();
                    break;
                case 14:
//                    solutions.solution14();
                    break;
                case 15:
//                    solutions.solution15();
                    break;
                case 16:
//                    solutions.solution16();
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
        System.out.println("(1) Representação do Grafo a partir da Lista de Adjacências");
        System.out.println("(2) Representação do Grafo a partir da Matriz de Adjacências");
        System.out.println("(3) Representação do Grafo a partir da Matriz de Incidência");
        System.out.println("(0) Sair");
        System.out.println("========================");
    }

    private static void displaySecondMenu() {
        clearConsole();
        System.out.println("=== Soluções ===");
        System.out.println("(4) Converter matriz de adjacência para lista de Adjacências e vice-versa.");
        System.out.println("(5) Calcular o grau de cada vértice.");
        System.out.println("(6) Determinar se dois vértices são adjacentes.");
        System.out.println("(7) Determinar o número total de vértices");
        System.out.println("(8) Determinar o número total de arestas");
        System.out.println("(9) Incluir um novo vértice");
        System.out.println("(10) Excluir um vértice existente");
        System.out.println("(11) Determinar se um grafo é conexo ou não");
        System.out.println("(12) Determinar se um grafo é bipartido");
        System.out.println("(13) Dada uma representação de uma árvore por matriz de adjacência, produzir o código de Prüffer e vice versa.");
        System.out.println("(14) Busca em Largura, a partir de um vértice específico");
        System.out.println("(15) Busca em Profundidade, a partir de um vértice em específico.");
        System.out.println("(16) Determinação de articulações e blocos (biconectividade)");
        System.out.println("(0) Voltar ao menu principal");
        System.out.println("========================");
    }
}