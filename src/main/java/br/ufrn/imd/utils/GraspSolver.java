package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

/**
 * Classe GraspSolver.
 * Esta classe implementa o algoritmo GRASP
 */
public class GraspSolver {

    /**
     * Tipos de restrição aplicáveis ao conjunto de candidatos.
     */
    enum RestrictionType {
        QUALITY_RESTRICTION, // Restrição baseada na qualidade do custo
        SIZE_RESTRICTION     // Restrição baseada no tamanho do conjunto
    }

    private final AdjacencyMatrixGraph graph; // Grafo representado por matriz de adjacência
    private final Random random; // Gerador de números aleatórios
    private List<String> candidates; // Lista de vértices candidatos
    private Double minCost; // Custo mínimo dos candidatos
    private Double maxCost; // Custo máximo dos candidatos
    private Double alpha; // Parâmetro utilizado para calcular o critério de inclusão
    private Double qualityCriterion; // Critério de qualidade para filtrar candidatos
    private final String startingVertex; // Vértice inicial
    private String currentVertex; // Vértice atual
    private RestrictionType restrictionType; // Tipo de restrição aplicada
    private final Set<String> visited; // Conjunto de vértices visitados
    private final List<String> solution; // Lista que armazena a solução construída

    /**
     * Construtor da classe GraspSolver.
     *
     * @param graph Grafo representado por matriz de adjacência.
     * @param startingVertex Vértice inicial para o algoritmo GRASP.
     */
    public GraspSolver(AdjacencyMatrixGraph graph, String startingVertex) {
        this.graph = graph;
        this.random = new Random();
        this.candidates = new ArrayList<>();
        this.restrictionType = RestrictionType.QUALITY_RESTRICTION;
        this.startingVertex = startingVertex;
        this.currentVertex = startingVertex;
        this.visited = new HashSet<>();
        this.solution = new ArrayList<>();
    }

    /**
     * Calcula o custo total baseado no inverso do custo dos candidatos.
     *
     * @return Custo total calculado.
     */
    private Double calculateTotalCost() {
        return candidates.stream()
                .mapToDouble(neighbor -> 1 / graph.getCost(currentVertex, neighbor))
                .sum();
    }

    /**
     * Calcula e atualiza a lista de candidatos.
     * Remove vértices já visitados e ordena por custo.
     */
    private void calculateCandidates() {
        candidates.clear();
        candidates.addAll(graph.findAdjacentVertices(currentVertex));  // Adiciona todos os vizinhos
        candidates.removeIf(visited::contains); // Remove vértices já visitados
        // Ordena por (1 / custo)
        candidates.sort((a, b) -> Double.compare(1 / graph.getCost(currentVertex, a), 1 / graph.getCost(currentVertex, b)));
    }

    /**
     * Seleciona aleatoriamente um vizinho com base nos custos.
     *
     * @return Vizinho escolhido ou null se nenhum estiver disponível.
     */
    public String getRandomNeighbor() {
        double totalCost = calculateTotalCost();
        double randomValue = random.nextDouble(totalCost);
        double cumulativeCost = 0.0;

        for (String neighbor : candidates) {
            cumulativeCost += 1 / graph.getCost(currentVertex, neighbor);
            if (randomValue < cumulativeCost) {
                return neighbor;
            }
        }
        return null;
    }

    /**
     * Calcula o custo mínimo e máximo dos candidatos.
     */
    private void calculateMinMaxCost() {
        if (candidates.isEmpty()) return;
        minCost = candidates.stream().mapToDouble(element -> graph.getCost(currentVertex, element)).min().getAsDouble();
        maxCost = candidates.stream().mapToDouble(element -> graph.getCost(currentVertex, element)).max().getAsDouble();
    }

    /**
     * Calcula o critério de qualidade baseado no custo mínimo, máximo e alfa.
     */
    private void calculateQualityCriterion() {
        this.qualityCriterion = minCost + alpha * (maxCost - minCost);
    }

    /**
     * Restringe a lista de candidatos conforme o tipo de restrição.
     */
    private void restrictCandidates() {
        switch (this.restrictionType) {
            case QUALITY_RESTRICTION:
                calculateMinMaxCost();
                calculateQualityCriterion();
                candidates.removeIf(neighbor -> graph.getCost(currentVertex, neighbor) > qualityCriterion);
                break;
            case SIZE_RESTRICTION:
                candidates = candidates.subList(0, (int) Math.ceil(alpha * candidates.size()));
                break;
        }
    }

    /**
     * Define a restrição de qualidade como ativa.
     */
    public void useQualityRestriction() {
        this.restrictionType = RestrictionType.QUALITY_RESTRICTION;
    }

    /**
     * Define a restrição de tamanho como ativa.
     */
    public void useSizeRestriction() {
        this.restrictionType = RestrictionType.SIZE_RESTRICTION;
    }

    /**
     * Define o valor de alfa utilizado nas restrições.
     *
     * @param alpha Valor de alfa.
     */
    public void setAlpha(Double alpha) {
        this.alpha = alpha;
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
        return overallCost;
    }

    /**
     * Resolve o problema utilizando o algoritmo GRASP.
     *
     * @return Lista representando a solução encontrada.
     */
    public List<String> solve() {
        clearDataStructures();
        visitCurrentVertex();
        while (true) {
            calculateCandidates();
            if (candidates.isEmpty()) {
                break;
            }
            restrictCandidates();
            currentVertex = getRandomNeighbor();
            visitCurrentVertex();
        }
        solution.add(startingVertex); // Fecha o ciclo
        return solution;
    }

    /**
     * Marca o vértice atual como visitado e o adiciona à solução.
     */
    private void visitCurrentVertex() {
        visited.add(currentVertex);
        solution.add(currentVertex);
    }

    /**
     * Limpa as estruturas de dados para uma nova execução.
     */
    private void clearDataStructures() {
        visited.clear();
        solution.clear();
        currentVertex = startingVertex;
    }
}
