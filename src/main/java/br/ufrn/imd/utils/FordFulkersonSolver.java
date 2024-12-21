package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import br.ufrn.imd.representations.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe para resolver o problema de fluxo máximo utilizando o algoritmo de Ford-Fulkerson.
 */
public class FordFulkersonSolver {
    // Grafo representado por matriz de adjacência
    private final AdjacencyMatrixGraph graph;

    // Lista de vértices visitados durante a busca em profundidade (DFS)
    private final List<String> visited;

    // Vértice sumidouro (destino)
    private String sink;

    // Matriz que representa os fluxos nas arestas
    private List<List<Integer>> flowMatrix;

    // Valor que representa infinito (dividido por 2 para evitar overflow)
    private static final int INF = Integer.MAX_VALUE / 2;

    /**
     * Construtor que inicializa o solver com um grafo.
     * @param graph Grafo representado por uma matriz de adjacência.
     */
    public FordFulkersonSolver(AdjacencyMatrixGraph graph) {
        this.graph = graph;
        this.visited = new ArrayList<>();
        initializeFlowMatrix(graph);
    }

    /**
     * Inicializa a matriz de fluxo com valores zero.
     * @param graph Grafo cuja matriz de fluxo será inicializada.
     */
    public void initializeFlowMatrix(Graph graph) {
        int numberOfVertices = graph.getNumberOfVertices(); // Número de vértices no grafo
        flowMatrix = new ArrayList<>();

        // Criação de uma matriz de fluxo preenchida com zeros
        for (int i = 0; i < numberOfVertices; i++) {
            List<Integer> row = new ArrayList<>(Collections.nCopies(numberOfVertices, 0));
            flowMatrix.add(row);
        }
    }

    /**
     * Resolve o problema de fluxo máximo utilizando o algoritmo de Ford-Fulkerson.
     * @param source Vértice de origem (string representando o rótulo do vértice).
     * @param sink Vértice sumidouro (string representando o rótulo do vértice).
     * @return O valor do fluxo máximo entre a origem e o sumidouro.
     */
    public int solve(String source, String sink) {
        int maxFlow = 0; // Variável para armazenar o fluxo máximo
        setSink(sink); // Salvar o vértice sumidouro

        // Percorre grafo procurando caminhos aumentantes
        for (int flow = dfs(source, INF); flow != 0; flow = dfs(source, INF)) {
            visited.clear(); // Limpa a lista de vértices visitados
            maxFlow += flow; // Adiciona o fluxo encontrado ao fluxo máximo
        }
        return maxFlow; // Retorna o fluxo máximo
    }

    /**
     * Define o vértice sumidouro.
     * @param sink Nome do vértice sumidouro.
     */
    private void setSink(String sink) {
        this.sink = sink;
    }

    /**
     * Busca em profundidade (DFS) para encontrar um caminho aumentante.
     * @param vertex Vértice atual.
     * @param flow Fluxo máximo possível no caminho atual.
     * @return O gargalo do fluxo encontrado no caminho aumentante, ou 0 se nenhum caminho foi encontrado.
     */
    private int dfs(String vertex, int flow) {
        // Caso base: verifica se o vértice atual é o sumidouro
        if (vertex.equals(sink)) {
            return flow;
        }

        visited.add(vertex); // Marca o vértice atual como visitado

        // Itera sobre os vértices adjacentes
        for (String neighbor : graph.findAdjacentVertices(vertex)) {
            int remainingFlow = findRemainingFlow(vertex, neighbor); // Calcula o fluxo restante disponível na aresta

            // Continua a busca se houver fluxo restante e o vizinho não foi visitado
            if (remainingFlow > 0 && !visited.contains(neighbor)) {
                // Chamada recursiva para o vizinho atualizando o gargalo
                int bottleneck = dfs(neighbor, Math.min(flow, remainingFlow));

                // Se o caminho aumentante foi encontrado, atualiza os fluxos das arestas e retorna
                if (bottleneck > 0) {
                    updateEdgeFlow(vertex, neighbor, bottleneck);
                    return bottleneck;
                }
            }
        }
        return 0; // Retorna 0 se nenhum caminho aumentante foi encontrado
    }

    /**
     * Atualiza o fluxo das arestas diretas e reversas.
     * A aresta de retorno utiliza fluxo negativo para que (capacidade - fluxo) > 0.
     * @param vertex Vértice de origem.
     * @param neighbor Vértice vizinho (destino).
     * @param bottleneck Fluxo encontrado no caminho aumentante.
     */
    private void updateEdgeFlow(String vertex, String neighbor, int bottleneck) {
        int vertexIndex = graph.getVertices().indexOf(vertex); // Índice do vértice de origem
        int neighborIndex = graph.getVertices().indexOf(neighbor); // Índice do vértice de destino

        int flow = flowMatrix.get(vertexIndex).get(neighborIndex); // Fluxo atual da aresta
        int reverseFlow = flowMatrix.get(neighborIndex).get(vertexIndex); // Fluxo reverso da aresta

        // Atualiza o fluxo para a aresta direta
        flowMatrix.get(vertexIndex).set(neighborIndex, flow + bottleneck);

        // Atualiza o fluxo para a aresta reversa
        flowMatrix.get(neighborIndex).set(vertexIndex, reverseFlow - bottleneck);
    }

    /**
     * Calcula o fluxo restante em uma aresta.
     * fluxo restante = (capacidade da aresta - fluxo usado atualmente)
     * @param vertex Vértice de origem.
     * @param neighbor Vértice vizinho destino.
     * @return O fluxo que ainda pode ser usado na aresta.
     */
    private int findRemainingFlow(String vertex, String neighbor) {
        int vertexIndex = graph.getVertices().indexOf(vertex); // Índice do vértice de origem
        int neighborIndex = graph.getVertices().indexOf(neighbor); // Índice do vértice de destino

        int flow = flowMatrix.get(vertexIndex).get(neighborIndex); // Fluxo atual na aresta
        int capacity = graph.getCapacity(vertex, neighbor); // Capacidade máxima da aresta

        return capacity - flow; // Retorna o fluxo restante
    }
}
