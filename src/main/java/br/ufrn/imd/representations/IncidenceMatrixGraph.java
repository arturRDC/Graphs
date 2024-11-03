package br.ufrn.imd.representations;

import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixGraph extends Graph {
    protected List<List<Integer>> incidenceMatrix; // Matriz de incidência do grafo
    protected int edgeCount; // Contador de arestas

    // Construtor da classe, inicializa a matriz de incidência
    public IncidenceMatrixGraph() {
        super();
        this.edgeCount = 0; // Contador de arestas inicia em 0
        this.incidenceMatrix = new ArrayList<>(); // Inicializa a matriz de incidência como lista de listas
    }

    // Retorna a lista de vértices adjacentes a um vértice
    @Override
    public List<String> findAdjacentVertices(String vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        List<String> adjacentVertices = new ArrayList<>();

        if (vertexIndex == -1) {
            return adjacentVertices; // Vértice não encontrado
        }

        // Iterar sobre a matriz de incidência para encontrar arestas que incluem o vértice
        for (List<Integer> row : incidenceMatrix) {
            if (row.get(vertexIndex) == 1) {
                // Achar o outro vértice na aresta
                for (int i = 0; i < row.size(); i++) {
                    if (i != vertexIndex && row.get(i) == 1) {
                        adjacentVertices.add(vertices.get(i));
                    }
                }
            }
        }
        return adjacentVertices;
    }


    // Método para adicionar uma aresta entre dois vértices em um grafo não direcionado
    @Override
    public void addEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);

        if (sourceIndex != -1 && destIndex != -1 && !hasEdge(source, destination)) {
            // Inicializa uma nova linha para representar a nova aresta
            List<Integer> newEdge = new ArrayList<>();
            for (int i = 0; i < vertices.size(); i++) {
                newEdge.add(0); // Inicializa todos os vértices como 0 na nova aresta
            }
            newEdge.set(sourceIndex, 1);
            newEdge.set(destIndex, 1);
            incidenceMatrix.add(newEdge); // Adiciona a nova aresta à matriz
            edgeCount++;
        }
    }

    @Override
    public boolean hasEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);
        if (sourceIndex != -1 && destIndex != -1) {
            for (List<Integer> row : incidenceMatrix) {
                if (row.get(sourceIndex) == 1 && row.get(destIndex) == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    // Remove uma aresta entre dois vértices
    @Override
    public void removeEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);

        // Verifica se os índices são válidos antes de prosseguir
        if (sourceIndex == -1 || destIndex == -1) {
            System.out.println("Aresta entre " + source + " e " + destination + " não pode ser removida: vértice não encontrado.");
            return;
        }

        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix.get(i).get(sourceIndex) == 1 && incidenceMatrix.get(i).get(destIndex) == 1) {
                incidenceMatrix.set(i, incidenceMatrix.get(edgeCount - 1));
                incidenceMatrix.remove(edgeCount - 1);
                edgeCount--;
                return;
            }
        }
    }

    @Override
    protected void addVertexSpecific(String vertex) {
        // Adiciona um elemento a todas as listas representando as arestas
        incidenceMatrix.forEach(row -> row.add(0));
    }

    @Override
    protected void removeVertexSpecific(String vertex, int index) {
        for (int i = 0; i < edgeCount; i++) {
            incidenceMatrix.get(i).remove(index);
        }
    }

    @Override
    public void printGraph() {
        System.out.println("\nGraph (From Incidence Matrix Representation):");
        for (int i = 0; i < edgeCount; i++) {
            StringBuilder edgeString = new StringBuilder("Edge " + (i + 1) + ": ");
            String vertex1 = "", vertex2 = "";

            for (int j = 0; j < vertices.size(); j++) {
                if (incidenceMatrix.get(i).get(j) == 1) {
                    if (vertex1.isEmpty()) {
                        vertex1 = vertices.get(j);
                    } else {
                        vertex2 = vertices.get(j);
                    }
                }
            }
            edgeString.append(vertex1).append(", ").append(vertex2);
            System.out.println(edgeString);
        }
    }

    public void printMatrix() {
        System.out.println("Incidence Matrix:");
        System.out.print("   ");
        for (String vertex : vertices) {
            System.out.printf("%3s", vertex);
        }
        System.out.println();

        for (int i = 0; i < edgeCount; i++) {
            System.out.printf("E%-2d", (i + 1));
            for (int j = 0; j < vertices.size(); j++) {
                System.out.printf("%3d", incidenceMatrix.get(i).get(j));
            }
            System.out.println();
        }
    }

    public int[][] toIncidenceMatrix() {
        int[][] matrix = new int[edgeCount][vertices.size()];
        for (int i = 0; i < edgeCount; i++) {
            for (int j = 0; j < vertices.size(); j++) {
                matrix[i][j] = incidenceMatrix.get(i).get(j);
            }
        }
        return matrix;
    }
}