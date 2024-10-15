package br.ufrn.imd.solutions;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.Scanner;

public class Solutions1_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Solutions 1.3");
        System.out.println("=============");
        waitForEnter(scanner);
        question1();


        scanner.close();
    }

    private static void waitForEnter(Scanner scanner) {
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    private static void question1() {
        System.out.println("Question 1");
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();

        // Add vertices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        // Add edges
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");

        // Print the graph
        System.out.println("Graph after adding vertices and edges:");
        graph.printMatrix();
        graph.printGraph();

        // Remove a vertex
        graph.removeVertex("C");

        // Print the graph
        System.out.println("\nGraph after removing vertex 'C':");
        graph.printMatrix();
        graph.printGraph();

        // Remove an edge
        graph.removeEdge("A", "B");

        // Print the graph
        System.out.println("\nGraph after removing the edge between 'A' and 'B':");
        graph.printMatrix();
        graph.printGraph();
    }
}
