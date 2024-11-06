package br.ufrn.imd.solutions;

import br.ufrn.imd.representations.AdjacencyListGraph;
import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import br.ufrn.imd.representations.Graph;
import br.ufrn.imd.representations.IncidenceMatrixGraph;
import br.ufrn.imd.utils.ArticulationPoints;
import br.ufrn.imd.utils.GraphFileReader;
import br.ufrn.imd.utils.PruferConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class SolutionsGraph {
    private Graph graph;

    private final GraphFileReader graphFileReader;

    public SolutionsGraph() {
        this.graphFileReader = new GraphFileReader();
    }

    private void readAdjacencyMatrix(String fileName) {
        setGraph(new AdjacencyMatrixGraph());
        graphFileReader.setFileName(fileName);
        graphFileReader.read(graph);
    }

    // Leitura de Matriz de Incidência
    public void readIncidenceMatrix(String fileName) {
        setGraph(new IncidenceMatrixGraph());
        graphFileReader.setFileName(fileName);
        graphFileReader.read(graph);
    }

    private void readAdjacencyList(String fileName) {
        setGraph(new AdjacencyListGraph());
        graphFileReader.setFileName(fileName);
        graphFileReader.read(graph);
    }

    public Graph solution1(String fileName) {
        readAdjacencyList(fileName);
        System.out.println("Lista de Adjacência:");
        ((AdjacencyListGraph) graph).printList();
        graph.printGraph();
        return graph;
    }

    public Graph solution2(String fileName) {
        readAdjacencyMatrix(fileName);
        System.out.println("Matriz de Adjacência:");
        ((AdjacencyMatrixGraph) graph).printMatrix();
        graph.printGraph();
        return graph;
    }

    public Graph solution3(String fileName) {
        readIncidenceMatrix(fileName);
        System.out.println("Matriz de Incidência:");
        ((IncidenceMatrixGraph) graph).printMatrix();
        graph.printGraph();
        return graph;
    }

    public Graph solution4(AtomicInteger graphType, Graph graph) {
        switch (graphType.get()) {
            case 1:
                AdjacencyListGraph adjacencyListGraph = (AdjacencyListGraph) graph;
                AdjacencyMatrixGraph matrixGraph = adjacencyListGraph.toAdjacencyMatrix();
                System.out.println("Matriz de Adjacência equivalente:");
                matrixGraph.printMatrix();
                graphType.set(2);
                return matrixGraph;
            case 2:
                AdjacencyMatrixGraph adjacencyMatrixGraph = (AdjacencyMatrixGraph) graph;
                AdjacencyListGraph listGraph = adjacencyMatrixGraph.toAdjacencyList();
                System.out.println("Lista de Adjacências equivalente:");
                listGraph.printList();
                graphType.set(1);
                return listGraph;
            default:
                System.out.println("Conversão não suportada para este tipo de grafo.");
                return graph;
        }
    }

    public void solution6() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o primeiro vértice: ");
        String firstVertex = scanner.nextLine();
        System.out.print("Digite o segundo vértice: ");
        String secondVertex = scanner.nextLine();

        if (!graph.hasVertex(firstVertex) || !graph.hasVertex(secondVertex)) {
            System.out.println("Vértices não encontrados no grafo.");
            return;
        }
        if (graph.areAdjacent(firstVertex, secondVertex)) {
            System.out.println("Os vértices são adjacentes.");
        } else {
            System.out.println("Os vértices não são adjacentes.");
        }
    }

    public void solution7() {
        int numberOfVertices = graph.getNumberOfVertices();
        System.out.println("Número de vértices no grafo: " + numberOfVertices);
    }

    public void solution8() {
        int numberOfEdges = graph.getNumberOfEdges();
        System.out.println("Número de arestas no grafo: " + numberOfEdges);
    }

    public void solution9(Integer graphType, Graph graph) {
        Scanner scanner = new Scanner(System.in);

        printGraph(graphType, graph); // Imprime o grafo antes da operação

        if (addVertexInGraph(scanner, graph)) { // Usa o método genérico addVertexInGraph
            System.out.println("\nGrafo após a adição do vértice:");
            printGraph(graphType, graph); // Imprime o grafo depois da operação
        }
    }

    public void solution10(Integer graphType, Graph graph) {
        Scanner scanner = new Scanner(System.in);

        printGraph(graphType, graph); // Imprime o grafo antes da operação

        if (removeVertexFromGraph(scanner, graph)) { // Usa o método genérico removeVertexFromGraph
            System.out.println("\nGrafo após a remoção do vértice:");
            printGraph(graphType, graph); // Imprime o grafo depois da operação
        }
    }

    public void solution11() {
        if (graph.isConnected(graph)) {
            System.out.println("O grafo é conexo.");
        } else {
            System.out.println("O grafo não é conexo.");
        }
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    private void printGraph(Integer graphType, Graph graph) {
        switch (graphType) {
            case 1:
                System.out.println("Grafo atual (Lista de Adjacência):");
                ((AdjacencyListGraph) graph).printList();
                break;
            case 2:
                System.out.println("Grafo atual (Matriz de Adjacência):");
                ((AdjacencyMatrixGraph) graph).printMatrix();
                break;
            case 3:
                System.out.println("Grafo atual (Matriz de Incidência):");
                ((IncidenceMatrixGraph) graph).printMatrix();
                break;
            default:
                System.out.println("Tipo de grafo inválido.");
        }
    }

    private boolean addEdgeToGraph(Scanner scanner, Graph graph) {
        System.out.print("Digite a aresta a ser adicionada no formato (x, y) ou 0 para cancelar: ");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            System.out.println("Operação cancelada.");
            return false;
        }

        try {
            // Extrai os vértices da entrada do usuário
            String[] vertices = input.substring(1, input.length() - 1).split(", ");
            String source = vertices[0];
            String destination = vertices[1];

            // Verifica se a aresta já existe
            if (graph.hasEdge(source, destination)) {
                System.out.println("Erro: A aresta (" + source + ", " + destination + ") já existe no grafo.");
                return false;
            }

            // Adiciona a aresta
            graph.addEdge(source, destination);
            System.out.println("Aresta adicionada com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Entrada inválida. Certifique-se de usar o formato (x, y).");
            return false;
        }
    }

    private boolean remEdgeToGraph(Scanner scanner, Graph graph) {
        System.out.print("Digite a aresta a ser removida no formato (x, y) ou 0 para cancelar: ");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            System.out.println("Operação cancelada.");
            return false;
        }

        try {
            // Extrai os vértices da entrada do usuário
            String[] vertices = input.substring(1, input.length() - 1).split(", ");
            String source = vertices[0];
            String destination = vertices[1];

            // Verifica se a aresta já existe
            if (!graph.hasEdge(source, destination)) {
                System.out.println("Erro: A aresta (" + source + ", " + destination + ") não existe no grafo.");
                return false;
            }

            // remove a aresta
            graph.removeEdge(source, destination);
            System.out.println("Aresta removida com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Entrada inválida. Certifique-se de usar o formato (x, y).");
            return false;
        }
    }

    private boolean removeVertexFromGraph(Scanner scanner, Graph graph) {
        System.out.print("Digite o vértice a ser removido ou 0 para cancelar: ");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            System.out.println("Operação cancelada.");
            return false;
        }

        // Verifica se o vértice existe
        if (!graph.hasVertex(input)) {
            System.out.println("Erro: O vértice " + input + " não existe no grafo.");
            return false;
        }

        try {
            // Remove o vértice
            graph.removeVertex(input);
            System.out.println("Vértice removido com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao remover o vértice.");
            return false;
        }
    }

    private boolean addVertexInGraph(Scanner scanner, Graph graph) {
        System.out.print("Digite o vértice a ser adicionado ou 0 para cancelar: ");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            System.out.println("Operação cancelada.");
            return false;
        }

        // Verifica se o vértice existe
        if (graph.hasVertex(input)) {
            System.out.println("Erro: O vértice " + input + " ja existe no grafo.");
            return false;
        }

        try {
            // Remove o vértice
            graph.addVertex(input);
            System.out.println("Vértice adicionado com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao adicionar o vértice.");
            return false;
        }
    }



    public void solution13() {
        if (!(graph instanceof AdjacencyMatrixGraph)) {
            System.out.println("A representação da árvore deve ser da matriz de adjacências.");
            return;
        }

        // Converter árvore para código de Prüfer
        System.out.println("\nConvertendo árvore para código de Prüfer...");
        PruferConverter pruferConverter = new PruferConverter((AdjacencyMatrixGraph) graph);

        try {
            List<String> pruferSequence = pruferConverter.encode();
            System.out.println("Código de Prüfer:");
            System.out.println(pruferSequence);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Converter código de Prüfer para árvore
        List<String> vertices = new ArrayList<>(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        List<String> pruferInput = new ArrayList<>(List.of("3", "3", "3", "6", "6", "6", "9", "9"));
        System.out.println("\nConvertendo código de Prüfer para árvore...");
        AdjacencyMatrixGraph tree = pruferConverter.decode(pruferInput, vertices);
        tree.printMatrix();
        tree.printGraph();
    }

    public void solution14() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o vértice inicial: ");
        String startVertex = scanner.nextLine();
        if (!graph.hasVertex(startVertex)) {
            System.out.println("Vértice inicial não encontrado no grafo.");
            return;
        }
        graph.bfs(startVertex);
    }

    public void solution15() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o vértice inicial para a travessia DFS: ");
        String startVertex = scanner.nextLine();
        if (!graph.hasVertex(startVertex)) {
            System.out.println("Vértice inicial não encontrado no grafo.");
            return;
        }
        graph.dfs(startVertex, 0, new HashMap<>());
    }

    public void solution16() {
        ArticulationPoints articulationPoints = new ArticulationPoints(graph);
        articulationPoints.findArticulationPointsAndBlocks();
        articulationPoints.printResults();
    }
}
