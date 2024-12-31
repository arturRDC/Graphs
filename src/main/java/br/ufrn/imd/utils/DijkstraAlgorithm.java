package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implementação do algoritmo de Dijkstra para encontrar os caminhos mínimos a partir de um vértice fonte.
 */
public class DijkstraAlgorithm {

    /**
     * Encontra as distâncias mínimas de um vértice de origem para todos os outros vértices do grafo.
     * @param graph  Grafo representado por uma matriz de adjacência.
     * @param source Vértice de origem.
     */
    public void findShortestPaths(AdjacencyMatrixGraph graph, String source) {
        // Inicializa estruturas auxiliares
        Map<String, Integer> distances = new HashMap<>(); // Armazena as distâncias mínimas
        Map<String, String> predecessors = new HashMap<>(); // Armazena os predecessores
        Set<String> visited = new HashSet<>(); // Conjunto de vértices visitados
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Inicializa distâncias e predecessores
        for (String vertex : graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
            predecessors.put(vertex, null);
        }
        distances.put(source, 0);

        // Adiciona o vértice de origem na fila de prioridade
        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            // Encontra o vértice não visitado com menor distância
            String current = priorityQueue.poll();

            if (visited.contains(current)) continue;
            visited.add(current);

            // Atualiza as distâncias dos vizinhos.
            // Para cada vértice vizinho do vértice atual, verifica se a distância mínima registrada pode ser melhorada.
            // Caso seja, atualiza a distância e o predecessor do vizinho, e o insere na fila de prioridade.
            for (String neighbor : graph.findAdjacentVertices(current)) {
                if (!visited.contains(neighbor)) {
                    int newDistance = distances.get(current) + graph.getWeight(current, neighbor);
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        predecessors.put(neighbor, current);
                        priorityQueue.add(neighbor);
                    }
                }
            }
        }

        // Exibe os resultados
        System.out.println("Caminhos mínimos a partir do vértice " + source + ":");
        for (String vertex : graph.getVertices()) {
            System.out.print("Vértice: " + vertex + ", Distância: " + distances.get(vertex));
            System.out.print(", Caminho: ");
            printPath(predecessors, vertex);
            System.out.println();
        }
    }

    /**
     * Imprime o caminho do vértice de origem até o vértice atual.
     * @param predecessors Mapa de predecessores.
     * @param vertex       Vértice atual.
     */
    private void printPath(Map<String, String> predecessors, String vertex) {
        if (vertex == null) return;
        printPath(predecessors, predecessors.get(vertex));
        System.out.print(vertex + " ");
    }
}
