package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.ArrayList;
import java.util.List;

public class CheapestInsertion {
    private final AdjacencyMatrixGraph graph;

    public CheapestInsertion(AdjacencyMatrixGraph graph) {
        this.graph = graph;
    }

    /**
     * Calcula o custo total de uma solução.
     *
     * @param solution Lista de vértices representando a solução.
     * @return Custo total da solução.
     */
    public Double calculateSolutionCost(List<String> solution) {
        double totalCost = 0.0;
        for (int i = 0; i < solution.size() - 1; i++) {
            totalCost += graph.getCost(solution.get(i), solution.get(i + 1));
        }
        // Fechar o ciclo (último vértice para o primeiro)
        totalCost += graph.getCost(solution.get(solution.size() - 1), solution.get(0));
        return totalCost;
    }

    /**
     * Executa o algoritmo da Inserção Mais Barata.
     *
     * @return Lista representando a solução construída.
     */
    public List<String> solve() {
        // Passo 1: Inicializa a rota com os dois primeiros vértices
        List<String> vertices = graph.getVertices();
        List<String> solution = new ArrayList<>();
        solution.add(vertices.get(0));
        solution.add(vertices.get(1));
        solution.add(vertices.get(0)); // Fecha o ciclo


        List<String> remainingVertices = new ArrayList<>(vertices);
        remainingVertices.remove(vertices.get(0));
        remainingVertices.remove(vertices.get(1));

        // Passo 2: Enquanto existirem vértices restantes
        while (!remainingVertices.isEmpty()) {
            double bestInsertionCost = Double.MAX_VALUE;
            String bestVertex = null;
            int bestPosition = -1;

            // Encontra o vértice e a posição com menor custo de inserção
            for (String vertex : remainingVertices) {
                for (int i = 0; i < solution.size() - 1; i++) {
                    String from = solution.get(i);
                    String to = solution.get(i + 1);
                    double insertionCost = graph.getCost(from, vertex) + graph.getCost(vertex, to) - graph.getCost(from, to);

                    if (insertionCost < bestInsertionCost) {
                        bestInsertionCost = insertionCost;
                        bestVertex = vertex;
                        bestPosition = i + 1;
                    }
                }
            }

            // Insere o vértice na melhor posição
            if (bestVertex != null && bestPosition != -1) {
                solution.add(bestPosition, bestVertex);
                remainingVertices.remove(bestVertex);
            }
        }

        return solution;
    }
}
