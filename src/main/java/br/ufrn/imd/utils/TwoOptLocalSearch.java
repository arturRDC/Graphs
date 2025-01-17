package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que implementa a busca local 2-opt
 */
public class TwoOptLocalSearch {
    // Grafo representado por uma matriz de adjacência
    private AdjacencyMatrixGraph graph;

    // Construtor que inicializa a busca local com o grafo fornecido
    public TwoOptLocalSearch(AdjacencyMatrixGraph graph) {
        this.graph = graph;
    }

    /**
     * Calcula o custo total de uma solução.
     *
     * @param solution Lista de vértices representando a solução.
     * @return Custo total da solução.
     */
    public Double calculateSolutionCost(List<String> solution) {
        double overallCost = 0.0;
        // Itera sobre a solução para calcular o custo das arestas entre os vértices
        for (int i = 0; i < solution.size() - 1; i++) {
            overallCost += graph.getCost(solution.get(i), solution.get(i + 1));
        }
        // Adiciona o custo para fechar o ciclo (último vértice para o primeiro)
        overallCost += graph.getCost(solution.get(solution.size() - 1), solution.get(0));
        return overallCost;
    }

    /**
     * Realiza o swap 2-opt em uma solução.
     * Troca a ordem dos vértices entre as posições i e j.
     *
     * @param solution Solução original.
     * @param i Índice inicial do segmento a ser invertido.
     * @param j Índice final do segmento a ser invertido.
     * @return Nova solução gerada após o swap.
     */
    private List<String> twoOptSwap(List<String> solution, int i, int j) {
        // Cria uma nova solução adicionando os elementos antes de i
        List<String> newSolution = new ArrayList<>(solution.subList(0, i));

        // Inverte o segmento entre i e j
        List<String> reversedSegment = new ArrayList<>(solution.subList(i, j + 1));
        Collections.reverse(reversedSegment);
        newSolution.addAll(reversedSegment);

        // Adiciona os elementos após j
        newSolution.addAll(solution.subList(j + 1, solution.size()));
        return newSolution;
    }

    /**
     * Executa a busca local 2-opt para melhorar a solução.
     *
     * @param solution Solução inicial.
     * @return Solução melhorada após a busca local.
     */
    public List<String> search(List<String> solution) {
        // Copia a solução inicial para a solução atual
        List<String> currentSolution = new ArrayList<>(solution);
        boolean foundBetterSolution;

        do {
            foundBetterSolution = false;
            // Calcula o custo da solução atual
            Double currentCost = calculateSolutionCost(currentSolution);

            // Itera sobre todas as possíveis combinações de i e j
            for (int i = 0; i < currentSolution.size() - 1; i++) {
                for (int j = i + 1; j < currentSolution.size(); j++) {
                    // Realiza o swap 2-opt e calcula o custo da nova solução
                    List<String> newSolution = twoOptSwap(currentSolution, i, j);
                    Double newCost = calculateSolutionCost(newSolution);

                    // Substitui a solução atual pela nova se o custo for menor
                    if (newCost < currentCost) {
                        currentSolution = newSolution;
                        currentCost = newCost;
                        foundBetterSolution = true;
                        break;
                    }
                }
                // Sai do laço externo se uma solução melhor for encontrada
                if (foundBetterSolution) break;
            }
        } while (foundBetterSolution); // Continua enquanto encontrar soluções melhores

        return currentSolution;
    }
}
