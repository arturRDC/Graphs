package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoOptLocalSearch {
    private AdjacencyMatrixGraph graph;

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
        for (int i = 0; i < solution.size() - 1; i++) {
            overallCost += graph.getCost(solution.get(i), solution.get(i + 1));
        }
        overallCost += graph.getCost(solution.get(solution.size() - 1), solution.get(0));
        return overallCost;
    }

    private List<String> twoOptSwap(List<String> solution, int i, int j) {
        List<String> newSolution = new ArrayList<>(solution.subList(0, i));

        List<String> reversedSegment = new ArrayList<>(solution.subList(i, j + 1));
        Collections.reverse(reversedSegment);
        newSolution.addAll(reversedSegment);

        newSolution.addAll(solution.subList(j + 1, solution.size()));
        return newSolution;
    }

    public List<String> search(List<String> solution) {
        List<String> currentSolution = new ArrayList<>(solution);
        boolean foundBetterSolution;

        do {
            foundBetterSolution = false;
            Double currentCost = calculateSolutionCost(currentSolution);

            for (int i = 0; i < currentSolution.size() - 1; i++) {
                for (int j = i + 1; j < currentSolution.size(); j++) {
                    List<String> newSolution = twoOptSwap(currentSolution, i, j);
                    Double newCost = calculateSolutionCost(newSolution);

                    if (newCost < currentCost) {
                        currentSolution = newSolution;
                        currentCost = newCost;
                        foundBetterSolution = true;
                        break;
                    }
                }
                if (foundBetterSolution) break;
            }
        } while (foundBetterSolution);

        return currentSolution;
    }
}