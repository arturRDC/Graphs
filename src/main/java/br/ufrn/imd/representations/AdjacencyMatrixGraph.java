

package br.ufrn.imd.representations;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixGraph extends Graph {
    private final List<List<Integer>> adjacencyMatrix;

    public AdjacencyMatrixGraph() {
        super();
        this.adjacencyMatrix = new ArrayList<>();
    }

    @Override
    protected void addVertexSpecific(String vertex) {
        adjacencyMatrix.add(new ArrayList<>(vertices.size()));
        for (List<Integer> row : adjacencyMatrix) {
            while (row.size() < vertices.size()) {
                row.add(0);
            }
        }
    }

    @Override
    protected void removeVertexSpecific(String vertex, int index) {
        adjacencyMatrix.remove(index);
        for (List<Integer> row : adjacencyMatrix) {
            row.remove(index);
        }
    }

    @Override
    public void addEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);
        if (sourceIndex != -1 && destIndex != -1 && !hasEdge(source, destination)) {
            adjacencyMatrix.get(sourceIndex).set(destIndex, 1);
            adjacencyMatrix.get(destIndex).set(sourceIndex, 1);
        }
    }

    @Override
    public boolean hasEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);
        if (sourceIndex != -1 && destIndex != -1) {
            return adjacencyMatrix.get(sourceIndex).get(destIndex) == 1;
        }
        return false;
    }

    @Override
    public void removeEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);
        if (sourceIndex != -1 && destIndex != -1) {
            adjacencyMatrix.get(sourceIndex).set(destIndex, 0);
            adjacencyMatrix.get(destIndex).set(sourceIndex, 0);
        }
    }

    @Override
    public void printGraph() {
        System.out.println("Graph:");
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + ": {");
            List<String> adjacentVertices = new ArrayList<>();
            for (int j = 0; j < vertices.size(); j++) {
                if (adjacencyMatrix.get(i).get(j) == 1) {
                    adjacentVertices.add("(" + vertices.get(i) + "," + vertices.get(j) + ")");
                }
            }
            System.out.print(String.join(", ", adjacentVertices));
            System.out.println("}");
        }
    }

    public void printMatrix() {
        System.out.println("Matrix:");
        System.out.print("  ");
        for (String vertex : vertices) {
            System.out.print(vertex + " ");
        }
        System.out.println();
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + " ");
            for (int j = 0; j < vertices.size(); j++) {
                System.out.print(adjacencyMatrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public AdjacencyListGraph toAdjacencyList() {
        AdjacencyListGraph listGraph = new AdjacencyListGraph();

        // Adiciona os mesmos vértices ao grafo de lista
        for (String vertex : this.vertices) {
            listGraph.addVertex(vertex);
        }

        // Constrói a lista de adjacências com base na matriz de adjacência
        int size = this.vertices.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.adjacencyMatrix.get(i).get(j) == 1) {
                    listGraph.addEdge(this.vertices.get(i), this.vertices.get(j));
                }
            }
        }

        return listGraph;
    }

    @Override
    public String[] getNeighbors(String vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) {
            return new String[0]; // Retorna um array vazio se o vértice não existir
        }

        List<String> neighbors = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            if (adjacencyMatrix.get(vertexIndex).get(i) == 1) {
                neighbors.add(vertices.get(i)); // Adiciona o vértice i como vizinho
            }
        }

        return neighbors.toArray(new String[0]);
    }
}