package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CostGraphFileReader {
    private String fileName;

    // Ler um arquivo contendo informações sobre vertices e arestas, gerando um grafo
    public void read(AdjacencyMatrixGraph graph) {
        if (fileName == null || fileName.isEmpty()) {
            System.err.println("Nome do arquivo não informado");
            return;
        }

        System.out.println("Lendo arquivo " + fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Ler o número de vértices da primeira linha
            int numVertices = Integer.parseInt(br.readLine().trim());



            // Ler cada aresta e adicioná-la ao grafo
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String vertex1 = parts[0].trim();
                    String vertex2 = parts[1].trim();

                    if (!graph.hasVertex(vertex1)) {
                        graph.addVertex(vertex1);
                    }
                    if (!graph.hasVertex(vertex2)) {
                        graph.addVertex(vertex2);
                    }

                    // Adicionar a aresta
                    graph.addEdge(vertex1, vertex2);

                    String cost = parts[2].trim();
                    graph.setCost(vertex1, vertex2, Double.parseDouble(cost));
                }
                else {
                    graph.addVertex(line.trim());
                }
            }
            System.out.println("Grafo carregado com " + numVertices + " vértices.");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
