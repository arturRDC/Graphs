package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementação do algoritmo de Floyd-Warshall para encontrar os caminhos mínimos entre todos os pares de vértices.
 */
public class FloydWarshallAlgorithm {

    /**
     * Encontra as distâncias mínimas entre todos os pares de vértices em um grafo direcionado com pesos.
     * @param graph Grafo representado por uma matriz de adjacência.
     */
    public void findAllPairsShortestPaths(AdjacencyMatrixGraph graph) {
        String[] vertices = graph.getVertices().toArray(new String[0]);

        // Inicializa as matrizes de distância e predecessores
        Map<String, Map<String, Integer>> dist = new HashMap<>();
        Map<String, Map<String, String>> pred = new HashMap<>();

        // Preenche as matrizes com os pesos do grafo
        for (String u : vertices) {
            dist.put(u, new HashMap<>());
            pred.put(u, new HashMap<>());
            for (String v : vertices) {
                int weight = graph.getWeight(u, v);
                dist.get(u).put(v, weight == 0 && !graph.hasEdge(u,v) ? Integer.MAX_VALUE : weight); // INF se não houver aresta
                pred.get(u).put(v, weight != Integer.MAX_VALUE ? u : null); // Predecessor ou null
            }
            dist.get(u).put(u, 0); // Distância para si mesmo é 0
            pred.get(u).put(u, u); // Predecessor para si mesmo
        }

        // Aplica o algoritmo de Floyd-Warshall
        for (String k : vertices) {
            for (String i : vertices) {
                for (String j : vertices) {
                    if (dist.get(i).get(k) != Integer.MAX_VALUE && dist.get(k).get(j) != Integer.MAX_VALUE) {
                        int newDist = dist.get(i).get(k) + dist.get(k).get(j);
                        if (dist.get(i).get(j) > newDist) {
                            dist.get(i).put(j, newDist);
                            pred.get(i).put(j, pred.get(k).get(j));
                        }
                    }
                }
            }
        }

        // Exibe os resultados
        System.out.println("Matriz de distâncias mínimas:");
        printResults(vertices, dist);
    }

    /**
     * Imprime os resultados da matriz de distâncias mínimas.
     * @param vertices Conjunto de vértices.
     * @param matrix Matriz a ser exibida.
     */
    private void printResults(String[] vertices, Map<String, Map<String, Integer>> matrix) {

        System.out.print("\t");
        for (String v : vertices) {
            System.out.print(v + "\t");
        }
        System.out.println();

        for (String u : vertices) {
            System.out.print(u + "\t");
            for (String v : vertices) {
                Integer value = matrix.get(u).get(v);
                System.out.print((value == Integer.MAX_VALUE ? 0 : value) + "\t");
            }
            System.out.println();
        }
    }
}
