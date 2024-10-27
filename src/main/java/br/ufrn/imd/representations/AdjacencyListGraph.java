package br.ufrn.imd.representations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListGraph extends Graph {
    private final List<LinkedList<String>> adjacencyList;

    public AdjacencyListGraph() {
        super();
        this.adjacencyList = new ArrayList<>();
    }

    @Override
    public void addEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);
        if (sourceIndex != -1 && destIndex != -1) {
            adjacencyList.get(sourceIndex).add(destination);
            adjacencyList.get(destIndex).add(source);
        }
    }

    @Override
    public void removeEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);
        if (sourceIndex != -1 && destIndex != -1) {
            adjacencyList.get(sourceIndex).remove(destination);
            adjacencyList.get(destIndex).remove(source);
        }
    }

    @Override
    public void printGraph() {
        System.out.println("Graph:");
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + ": {");
            List<String> adjacentVertices = new ArrayList<>();
            for (String neighbor : adjacencyList.get(i)) {
                adjacentVertices.add("(" + vertices.get(i) + "," + neighbor + ")");
            }
            System.out.print(String.join(", ", adjacentVertices));
            System.out.println("}");
        }
    }

    public void printList() {
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + " -> ");
            LinkedList<String> currentList = adjacencyList.get(i);
            for (int j = 0; j < currentList.size(); j++) {
                System.out.print(currentList.get(j));
                if (j < currentList.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println(" -> null");
        }
    }

    @Override
    protected void addVertexSpecific(String vertex) {
        adjacencyList.add(new LinkedList<>());
    }

    @Override
    protected void removeVertexSpecific(String vertex, int index) {
        adjacencyList.remove(index);
        for (LinkedList<String> connections : adjacencyList) {
            connections.remove(vertex);
        }
    }
}
