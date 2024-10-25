package br.ufrn.imd.utils;

import br.ufrn.imd.representations.Graph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class GraphFileReader {
    private String fileName;

    // Ler um arquivo contendo informações sobre vertices e arestas, gerando um grafo
    public void read(Graph graph) {
        if (fileName == null || fileName.isEmpty()) {
            System.err.println("Nome do arquivo não informado");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Ler o número de vértices da primeira linha
            int numVertices = Integer.parseInt(br.readLine().trim());

            // Adicionar todos os vértices ao grafo
            for (int i = 1; i <= numVertices; i++) {
                graph.addVertex(String.valueOf(i));
            }

            // Ler cada aresta e adicioná-la ao grafo
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String vertex1 = parts[0].trim();
                    String vertex2 = parts[1].trim();

                    // Adicionar a aresta
                    graph.addEdge(vertex1, vertex2);
                }
            }
            System.out.println("Grafo carregado com " + numVertices + " vértices.");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
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
