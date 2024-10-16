

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
        if (sourceIndex != -1 && destIndex != -1) {
            adjacencyMatrix.get(sourceIndex).set(destIndex, 1);
            adjacencyMatrix.get(destIndex).set(sourceIndex, 1);
        }
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
}