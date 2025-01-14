package br.ufrn.imd.ui;

import br.ufrn.imd.solutions.SolutionsGraph3;

import java.util.Scanner;

public class MenuGraph3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SolutionsGraph3 solutions = new SolutionsGraph3();
        int choice;
        String fileName;

        do {
            displayMainMenu();
            System.out.print("Digite sua escolha (0 para sair): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();


            if (choice >= 1 && choice <= 12) {
                fileName = "inputs/GRAFO-P" + choice + ".txt";

                pressEnter(scanner);
                showSolutionOptions(scanner, solutions, fileName);
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

    private static void showSolutionOptions(Scanner scanner, SolutionsGraph3 solutions, String fileName) {
        int stageChoice;
        do {
            displaySecondMenu();
            System.out.print("Digite sua escolha (0 para voltar): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
            stageChoice = scanner.nextInt();
            scanner.nextLine();

            switch (stageChoice) {
                case 1:
                    solutions.solution1(fileName);
                    break;
                case 2:
                    solutions.solution2(fileName);
                    break;
                case 3:
                    solutions.solution3(fileName);
                    break;
                case 0:
                    System.out.println("Voltando...");
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


    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void displayMainMenu() {
        clearConsole();
        System.out.println("======== Escolha um problema ========");
        System.out.println("(1)  Problema 1  - Km:      [n = 48]");
        System.out.println("(2)  Problema 2  - minutos: [n = 48]");
        System.out.println("(3)  Problema 3  - Km:      [n = 36]");
        System.out.println("(4)  Problema 4  - minutos: [n = 36]");
        System.out.println("(5)  Problema 5  - Km:      [n = 24]");
        System.out.println("(6)  Problema 6  - minutos: [n = 24]");
        System.out.println("(7)  Problema 7  - Km:      [n = 12]");
        System.out.println("(8)  Problema 8  - minutos: [n = 12]");
        System.out.println("(9)  Problema 9  - Km:      [n =  7]");
        System.out.println("(10) Problema 10 - minutos: [n =  7]");
        System.out.println("(11) Problema 11 - Km:      [n =  6]");
        System.out.println("(12) Problema 12 - minutos: [n =  6]");
        System.out.println("(0) Sair");
        System.out.println("==================================");
    }

    private static void displaySecondMenu() {
        clearConsole();
        System.out.println("=== Algoritmos ===");
        System.out.println("(1) Algoritmo Guloso.");
        System.out.println("(2) Algoritmo da Inserção mais barata.");
        System.out.println("(3) Algoritmo GRASP.");
        System.out.println("(0) Voltar ao menu principal");
        System.out.println("========================");
    }
}