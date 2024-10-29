package br.ufrn.imd.representations;

import java.util.List;

public class AdjacencyMatrixDigraph extends Graph{
    @Override
    public List<String> findAdjacentVertices(String vertex) {
        return List.of();
    }

    @Override
    public void addEdge(String source, String destination) {

    }

    @Override
    public boolean hasEdge(String source, String destination) {
        return false;
    }

    @Override
    public void removeEdge(String source, String destination) {

    }

    @Override
    public void printGraph() {

    }

    @Override
    protected void addVertexSpecific(String vertex) {

    }

    @Override
    protected void removeVertexSpecific(String vertex, int index) {

    }

    public void printMatrix() {

    }
}
