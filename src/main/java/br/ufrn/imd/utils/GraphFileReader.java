package br.ufrn.imd.utils;

import br.ufrn.imd.representations.Graph;

public class GraphFileReader {
    private String fileName;
    public void read(Graph graph) {
        // TODO: Read the file and add vertices and edges to the graph
        // Mock data
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
