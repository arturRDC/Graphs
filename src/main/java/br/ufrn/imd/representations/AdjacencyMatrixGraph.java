

package br.ufrn.imd.representations;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixGraph extends Graph {
    protected final List<List<Integer>> adjacencyMatrix;
    protected List<List<Integer>> weightMatrix;
    protected List<List<Integer>> capacityMatrix;

    public AdjacencyMatrixGraph() {
        super();
        this.adjacencyMatrix = new ArrayList<>();
        this.weightMatrix = new ArrayList<>();
        this.capacityMatrix = new ArrayList<>();
    }

    // Retorna a lista de vértices adjacentes a um vértice
    @Override
    public List<String> findAdjacentVertices(String vertex) {
        List<String> neighbors = new ArrayList<>();
        int vertexIndex = vertices.indexOf(vertex);

        if (vertexIndex != -1) {
            List<Integer> adjacencyRow = adjacencyMatrix.get(vertexIndex);
            for (int i = 0; i < adjacencyRow.size(); i++) {
                if (adjacencyRow.get(i) > 0) {
                    neighbors.add(vertices.get(i));
                }
            }
        }

        return neighbors;
    }

    @Override
    protected void addVertexSpecific(String vertex) {
        int currentSize = vertices.size();

        // Adicionar novas linhas
        adjacencyMatrix.add(new ArrayList<>(currentSize));
        weightMatrix.add(new ArrayList<>(currentSize));
        capacityMatrix.add(new ArrayList<>(currentSize));

        // Preencher todas as linhas para manter dimensões quadradas
        for (int i = 0; i < currentSize; i++) {
            List<Integer> adjRow = adjacencyMatrix.get(i);
            List<Integer> weightRow = weightMatrix.get(i);
            List<Integer> capRow = capacityMatrix.get(i);

            while (adjRow.size() < currentSize) {
                adjRow.add(0);
                weightRow.add(0);
                capRow.add(0);
            }
        }
    }

    @Override
    protected void removeVertexSpecific(String vertex, int index) {
        // Remover linha
        adjacencyMatrix.remove(index);
        weightMatrix.remove(index);
        capacityMatrix.remove(index);

        // Remover coluna de todas as linhas restantes
        for (int i = 0; i < adjacencyMatrix.size(); i++) {
            adjacencyMatrix.get(i).remove(index);
            weightMatrix.get(i).remove(index);
            capacityMatrix.get(i).remove(index);
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
        System.out.println("Grafo:");
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
        System.out.print(" ");
        for (String vertex : vertices) {
            System.out.printf("%3s", vertex);
        }
        System.out.println();

        for (int i = 0; i < vertices.size(); i++) {
            System.out.printf("%-3s", vertices.get(i));
            for (int j = 0; j < vertices.size(); j++) {
                System.out.printf("%-3d", adjacencyMatrix.get(i).get(j));
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

    public Integer getWeight(String vertex1, String vertex2) {
        int sourceIndex = vertices.indexOf(vertex1);
        int destIndex = vertices.indexOf(vertex2);
        return weightMatrix.get(sourceIndex).get(destIndex);
    }

    public Integer getCapacity(String vertex1, String vertex2) {
        int sourceIndex = vertices.indexOf(vertex1);
        int destIndex = vertices.indexOf(vertex2);
        return capacityMatrix.get(sourceIndex).get(destIndex);
    }

    public void setWeight(String vertex1, String vertex2, int weight) {
        int sourceIndex = vertices.indexOf(vertex1);
        int destIndex = vertices.indexOf(vertex2);
        weightMatrix.get(sourceIndex).set(destIndex, weight);
        weightMatrix.get(destIndex).set(sourceIndex, weight);
    }

    public void setCapacity(String vertex1, String vertex2, int capacity) {
        int sourceIndex = vertices.indexOf(vertex1);
        int destIndex = vertices.indexOf(vertex2);
        capacityMatrix.get(sourceIndex).set(destIndex, capacity);
        capacityMatrix.get(destIndex).set(sourceIndex, capacity);
    }
}