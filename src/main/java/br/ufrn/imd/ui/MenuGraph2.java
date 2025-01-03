package br.ufrn.imd.ui;

import br.ufrn.imd.solutions.SolutionsGraph2;

import java.util.Scanner;

public class MenuGraph2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SolutionsGraph2 solutions = new SolutionsGraph2();
        showSolutionOptions(scanner, solutions);
    }


    private static void showSolutionOptions(Scanner scanner, SolutionsGraph2 solutions) {
        int choice;
        do {
            displayMenu();
            System.out.print("Digite sua escolha (0 para voltar): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    solutions.solution1("inputs/GRAFO4.txt");
                    break;
                case 2:
                    solutions.solution2("inputs/GRAFO4.txt");
                    break;
                case 3:
                    solutions.solution3("inputs/GRAFO4.txt");
                    break;
                case 4:
                    solutions.solution4("inputs/DIGRAFO4.txt");
                    break;
                case 5:
                    solutions.solution5("inputs/DIGRAFO5.txt");
                    break;
                case 6:
                    solutions.solution6("inputs/DIGRAFO5.txt");
                    break;
                case 7:
                    solutions.solution7("inputs/DIGRAFO5.txt");
                    break;
                case 8:
                    solutions.solution8("inputs/DIGRAFO7.txt");
                    break;
                case 9:
                    solutions.solution9("inputs/DIGRAFO5.txt");
                    break;
                case 10:
                    solutions.solution10("inputs/DIGRAFO6.txt");
                    break;
                case 11:
                    solutions.solution11("inputs/DIGRAFO6.txt");
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor tente novamente.");
            }

            if (choice != 0) {
                System.out.println("\nAperte enter para continuar...");
                scanner.nextLine();
            }
        } while (choice != 0);
    }

    private static void displayMenu() {
        System.out.println("=== Soluções ===");
        System.out.println("(1) Algoritmo de Kruskal");
        System.out.println("(2) Algoritmo de Prim");
        System.out.println("(3) Algoritmo de Boruvka (OPC)");
        System.out.println("(4) Algoritmo de Chu-Liu/Edmonds");
        System.out.println("(5) Algoritmo de Dijkstra");
        System.out.println("(6) Algoritmo de Bellman-Ford");
        System.out.println("(7) Algoritmo de Floyd-Warshall");
        System.out.println("(8) Algoritmo de Hierholzer (CICLOS)");
        System.out.println("(9) Algoritmo de Hierholzer (CAMINHOS) (OPC)");
        System.out.println("(10) Algoritmo de Ford-Fulkerson");
        System.out.println("(11) Algoritmo de Edmonds-Karp");
        System.out.println("(0) Voltar ao menu principal");
        System.out.println("========================");
    }
}