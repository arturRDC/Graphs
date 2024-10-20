package br.ufrn.imd.representations;

import java.util.ArrayList;
import java.util.List;

public abstract class Graph {
    protected List<String> vertices;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public void addVertex(String vertex) {
        vertices.add(vertex);
        addVertexSpecific(vertex);
    }

    public void removeVertex(String vertex) {
        int index = vertices.indexOf(vertex);
        if (index != -1) {
            vertices.remove(index);
            removeVertexSpecific(vertex, index);
        }
    }

    public abstract void addEdge(String source, String destination);
    public abstract void removeEdge(String source, String destination);
    public abstract void printGraph();

    protected abstract void addVertexSpecific(String vertex);
    protected abstract void removeVertexSpecific(String vertex, int index);
}