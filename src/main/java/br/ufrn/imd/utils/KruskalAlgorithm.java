package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa uma aresta do grafo com vértices de origem, destino e peso associado.
 * Implementa a interface Comparable para permitir a ordenação baseada no peso.
 */
record Edge(String source, String destination, int weight) implements Comparable<Edge> {
    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}

/**
 * Implementação do algoritmo de Kruskal para encontrar a Árvore Geradora Mínima (MST).
 * Utiliza a estrutura de Disjoint Set (Union-Find) para detectar ciclos.
 */
public class KruskalAlgorithm {

    private Map<String, String> parent;

    public KruskalAlgorithm() {
        this.parent = new HashMap<>();
    }

    /**
     * Encontra e exibe a Árvore Geradora Mínima do grafo usando o algoritmo de Kruskal.
     * Exibe também o peso total da MST.
     * @param graph Grafo representado por uma matriz de adjacência.
     */
    public void findMinimumSpanningTree(AdjacencyMatrixGraph graph) {
        List<Edge> edges = extractEdges(graph);
        List<Edge> mst = new ArrayList<>();
        int totalWeight = 0;

        // Inicializa o conjunto de disjoint sets
        for (String vertex : graph.getVertices()) {
            parent.put(vertex, vertex);
        }

        // Ordena as arestas em ordem crescente de peso
        Collections.sort(edges);

        // Processa cada aresta na ordem dos pesos
        for (Edge edge : edges) {
            String rootSource = find(edge.source());
            String rootDestination = find(edge.destination());

            // Inclui a aresta se os dois vértices não pertencem ao mesmo conjunto (sem ciclo)
            if (!rootSource.equals(rootDestination)) {
                mst.add(edge);
                totalWeight += edge.weight();
                parent.put(rootSource, rootDestination);
            }

            // Interrompe o processamento quando a MST contém n-1 arestas
            if (mst.size() == graph.getNumberOfVertices() - 1) {
                break;
            }
        }

        // Exibe a árvore geradora mínima
        System.out.println("Árvore Geradora Mínima:");
        for (Edge edge : mst) {
            System.out.println("{" + edge.source() + "," + edge.destination() + "} : " + edge.weight());
        }
        // Exibe o peso total da árvore geradora mínima
        System.out.println("Peso Total da Árvore Geradora Mínima: " + totalWeight);
    }

    /**
     * Extrai todas as arestas do grafo para o formato do record.
     * @param graph Grafo representado por uma matriz de adjacência.
     * @return Lista de arestas do grafo.
     */
    private List<Edge> extractEdges(AdjacencyMatrixGraph graph) {
        List<Edge> edges = new ArrayList<>();
        for (String vertex : graph.getVertices()) {
            for (String neighbor : graph.findAdjacentVertices(vertex)) {
                int weight = graph.getWeight(vertex, neighbor);
                edges.add(new Edge(vertex, neighbor, weight));
            }
        }
        return edges;
    }

    /**
     * Encontra o representante de um conjunto no DSU.
     * @param vertex Vértice cujo representante será encontrado.
     * @return Representante do conjunto ao qual o vértice pertence.
     */
    private String find(String vertex) {
        if (!parent.get(vertex).equals(vertex)) {
            parent.put(vertex, find(parent.get(vertex)));
        }
        return parent.get(vertex);
    }
}


