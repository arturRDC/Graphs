package br.ufrn.imd.representations;

public class AdjacencyMatrixDigraph extends AdjacencyMatrixGraph{

    @Override
    public void addEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);
        if (sourceIndex != -1 && destIndex != -1 && !hasEdge(source, destination)) {
            adjacencyMatrix.get(sourceIndex).set(destIndex, 1);
        }
    }

    @Override
    public void removeEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);
        if (sourceIndex != -1 && destIndex != -1) {
            adjacencyMatrix.get(sourceIndex).set(destIndex, 0);
        }
    }

}
