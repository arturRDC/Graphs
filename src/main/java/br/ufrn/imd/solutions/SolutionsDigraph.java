package br.ufrn.imd.solutions;

import br.ufrn.imd.representations.AdjacencyMatrixDigraph;
import br.ufrn.imd.representations.Graph;
import br.ufrn.imd.representations.IncidenceMatrixDigraph;
import br.ufrn.imd.utils.GraphFileReader;
import br.ufrn.imd.utils.TopologicalSort;

import java.util.List;
import java.util.Scanner;

public class SolutionsDigraph {
    private Graph graph;

    private final GraphFileReader graphFileReader;

    public SolutionsDigraph() {
        this.graphFileReader = new GraphFileReader();
    }

    private void readAdjacencyMatrix(String fileName) {
        setGraph(new AdjacencyMatrixDigraph());
        graphFileReader.setFileName(fileName);
        graphFileReader.read(graph);
    }

    // Leitura de Matriz de Incidência
    public void readIncidenceMatrix(String fileName) {
        setGraph(new IncidenceMatrixDigraph());
        graphFileReader.setFileName(fileName);
        graphFileReader.read(graph);
    }

    public void solution17(String fileName) {
        readAdjacencyMatrix(fileName);
        System.out.println("Digrafo (a partir representação da matriz de adjacência):");
        ((AdjacencyMatrixDigraph) graph).printMatrix();
        graph.printGraph();
    }

    public void solution18(String fileName) {
        readIncidenceMatrix(fileName);
        System.out.println("Matriz de Incidência:");
        ((IncidenceMatrixDigraph) graph).printMatrix();
        System.out.println("\nDigrafo (a partir representação da matriz de incidência):");
        graph.printGraph();
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void solution22() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o vértice inicial para a travessia DFS com a determinação de profundidade de entrada e saida de cada vertice: ");
        String startVertex = scanner.nextLine();
        if (!graph.hasVertex(startVertex)) {
            System.out.println("Vértice inicial não encontrado no Digrafo.");
            return;
        }
        graph.dfsWithEntryExit(startVertex);
    }

    public void solution23() {
        System.out.println("Ordem Topológica:");
        List<String> topologicalOrder = new TopologicalSort(graph).sort();
        System.out.println(topologicalOrder);
    }
}
