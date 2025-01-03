package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.HashMap;
import java.util.Map;

public class BellmanFordAlgorithm {

    /**
     * Implementa o algoritmo de Bellman-Ford para encontrar os caminhos mínimos de um vértice fonte.
     * @param graph  Grafo representado pela classe AdjacencyMatrixGraph.
     * @param source Vértice de origem.
     */
    public void findShortestPaths(AdjacencyMatrixGraph graph, String source) {
        Map<String, Integer> distances = new HashMap<>(); // Armazena as distâncias mínimas
        Map<String, String> predecessors = new HashMap<>(); // Armazena os predecessores
        int vertexCount = graph.getVertices().size(); // Número de vértices

        // Inicializa as distâncias e predecessores
        for (String vertex : graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
            predecessors.put(vertex, null);
        }
        distances.put(source, 0);

        // Relaxamento das arestas
        for (int i = 0; i < vertexCount; i++) {
            boolean updated = false; // Para rastrear se houve atualização nesta iteração
            for (String u : graph.getVertices()) {
                for (String v : graph.findAdjacentVertices(u)) {
                    int weight = graph.getWeight(u, v);
                    if (weight != 0 && weight != Integer.MAX_VALUE) { // Se há aresta de u para v
                        if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {

                            // Verificação de ciclo negativo na última iteração
                            if (i == vertexCount - 1) {
                                System.out.println("\nCiclo negativo detectado! Não é possível detectar o menor caminho.");
                                return;
                            }

                            distances.put(v, distances.get(u) + weight);
                            predecessors.put(v, u);
                            updated = true;
                        }
                    }
                }
            }
            // Se nenhuma atualização foi feita, podemos interromper mais cedo
            if (!updated) break;
        }

        // Exibe os resultados
        printResults(distances, predecessors, source);
    }

    /**
     * Exibe os resultados do algoritmo.
     * @param distances    Mapa de distâncias mínimas.
     * @param predecessors Mapa de predecessores.
     * @param source       Vértice fonte.
     */
    private void printResults(Map<String, Integer> distances, Map<String, String> predecessors, String source) {
        System.out.println("Caminhos mínimos a partir do vértice " + source + ":");
        for (String vertex : distances.keySet()) {
            System.out.print("Vértice: " + vertex + ", Distância: " + distances.get(vertex));
            System.out.print(", Caminho: ");
            printPath(predecessors, vertex);
            System.out.println();
        }
    }

    /**
     * Imprime o caminho do vértice fonte até o vértice atual.
     * @param predecessors Mapa de predecessores.
     * @param vertex       Vértice atual.
     */
    private void printPath(Map<String, String> predecessors, String vertex) {
        if (vertex == null) return;
        printPath(predecessors, predecessors.get(vertex));
        System.out.print(vertex + " ");
    }
}
