package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementação do algoritmo de Prim para encontrar a Árvore Geradora Mínima (MST).
 */
public class PrimAlgorithm {

    /**
     * Encontra e exibe a Árvore Geradora Mínima do grafo usando o algoritmo de Prim.
     * Exibe também o peso total da MST.
     * @param graph Grafo representado por uma matriz de adjacência.
     */
    public void findMinimumSpanningTree(AdjacencyMatrixGraph graph) {
        Set<String> includedVertices = new HashSet<>();
        Set<String> remainingVertices = new HashSet<>(graph.getVertices());
        List<Edge> mst = new ArrayList<>();
        int totalWeight = 0;

        // Escolhe qualquer vértice inicial (nesse caso, o primeiro da lista de vértices)
        String startVertex = graph.getVertices().get(0);
        includedVertices.add(startVertex);
        remainingVertices.remove(startVertex);

        // Enquanto houver vértices para processar
        while (!remainingVertices.isEmpty()) {
            Edge minimumEdge = null;

            // Encontrar a aresta (j, k) com menor peso, onde j pertence ao conjunto dos vértices já incluídos na árvore
            // e k pertence ao conjunto dos vertices que ainda serão processados
            for (String u : includedVertices) {
                for (String v : remainingVertices) {
                    int weight = graph.getWeight(u, v);
                    if (weight > 0 && (minimumEdge == null || weight < minimumEdge.weight())) {
                        minimumEdge = new Edge(u, v, weight);
                    }
                }
            }

            // Adiciona a aresta mínima ao conjunto da árvore geradora mínima
            if (minimumEdge != null) {
                mst.add(minimumEdge);
                totalWeight += minimumEdge.weight();

                // Atualiza os conjuntos
                includedVertices.add(minimumEdge.destination());
                remainingVertices.remove(minimumEdge.destination());
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
}