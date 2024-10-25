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

    public void readDigraph(Graph graph, boolean directed) {
        // Adicionar vértices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        // Adicionar arestas
        if (directed) {
            // Para grafos direcionados
            graph.addEdge("A", "B");
            graph.addEdge("C", "A");
            graph.addEdge("B", "D");
        } else {
            // Para grafos não direcionados (aresta nos dois sentidos)
            graph.addEdge("A", "B");
            graph.addEdge("B", "A");  // Aresta de volta
            graph.addEdge("A", "C");
            graph.addEdge("C", "A");  // Aresta de volta
            graph.addEdge("B", "D");
            graph.addEdge("D", "B");  // Aresta de volta
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
