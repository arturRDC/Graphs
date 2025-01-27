package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementação do algoritmo guloso para encontrar um caminho Hamiltoniano e resolver o problema do Caixeiro Viajante (TSP).
 * O algoritmo escolhe iterativamente o vértice mais próximo e menos custoso do último vértice incluído no caminho.
 */
public class GreedyAlgorithm {
    private final AdjacencyMatrixGraph graph;
    private final int numVertices;
    private final List<String> path;
    private final Set<String> visited;
    private double totalCost;

    public GreedyAlgorithm(AdjacencyMatrixGraph graph) {
        this.numVertices = graph.getVertices().size();
        this.path = new ArrayList<>();
        this.visited = new HashSet<>();
        this.totalCost = 0.0;
        this.graph = graph;
    }

    public void solve() {
        // Escolhe um vértice inicial arbitrário, nesse caso o primeiro da lista de vértices
        String startVertex = graph.getVertices().get(0);

        path.add(startVertex);
        visited.add(startVertex);
        String currentVertex = startVertex;

        // Encontrar o vértice mais próximo ao último vértice incluído no caminho
        while (path.size() < numVertices) {
            String nearestVertex = findNearestNeighbor(currentVertex);
            if (nearestVertex == null) break;

            // Inserir o vértice após o último vértice (vizinho mais próximo)
            path.add(nearestVertex); // Adiciona o vértice mais próximo ao caminho
            visited.add(nearestVertex); // Marca o vértice como visitado
            totalCost += graph.getCost(currentVertex, nearestVertex); // Adiciona o custo da aresta ao custo total

            currentVertex = nearestVertex;
        }

        // Caso o ciclo formado seja Hamiltoniano, fecha o ciclo.
        if (path.size() == numVertices) {
            // Fechar o ciclo verificando se há aresta de volta ao início
            if (graph.getCost(currentVertex, startVertex) > 0) {
                path.add(startVertex);
                totalCost += graph.getCost(currentVertex, startVertex);
            } else {
                path.clear();  // Não é Hamiltoniano se não tiver aresta de volta
                totalCost = 0.0;
            }
        } else {
            path.clear();  // Não é Hamiltoniano se não visitou todos os vértices
            totalCost = 0.0;
        }
    }

    /**
     * Encontra o vizinho mais próximo de um vértice que ainda não foi visitado.
     *
     * @param vertex O vértice atual.
     * @return O vizinho mais próximo não visitado ou null se não houver.
     */
    private String findNearestNeighbor(String vertex) {
        double minDistance = Double.MAX_VALUE;
        String nearestVertex = null;

        for (String neighbor : graph.findAdjacentVertices(vertex)) {
            if (!visited.contains(neighbor)) {
                double distance = graph.getCost(vertex, neighbor);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestVertex = neighbor;
                }
            }
        }
        return nearestVertex;
    }

    /**
     * Retorna o caminho encontrado pelo algoritmo.
     * @return Lista de vértices representando o caminho.
     */
    public List<String> getPath() {
        return path;
    }

    /**
     * Retorna o custo total do caminho encontrado.
     * @return Custo total do caminho.
     */
    public double getTotalCost() {
        return totalCost;
    }
}