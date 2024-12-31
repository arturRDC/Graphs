package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import br.ufrn.imd.representations.Graph;

import java.util.*;


/**
 * Implementação do algoritmo de Edmonds-Karp para resolver o problema do fluxo máximo.
 */
public class EdmondsKarpSolver {
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

    private HashMap<String,String> parent;

    /**
     * Construtor que inicializa o solver com um grafo.
     * @param graph Grafo representado por uma matriz de adjacência.
     */
    public EdmondsKarpSolver(AdjacencyMatrixGraph graph) {
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
        setSink(sink); // Armazena o vértice sumidouro
        int maxFlow = 0; // Inicializa o fluxo máximo

        while (true) {
            visited.clear(); // Configura todos os vértices como não visitados
            int flow = bfs(source); // Retorna o gargalo encontrado em um caminho aumentante
            if (flow == 0) break; // Se o fluxo máximo for 0, termina a busca
            maxFlow += flow; // Adiciona o fluxo encontrado ao fluxo máximo
        }

        return maxFlow; // Retorna o fluxo máximo encontrado
    }


    /**
     * Define o vértice sumidouro.
     * @param sink Nome do vértice sumidouro.
     */
    private void setSink(String sink) {
        this.sink = sink;
    }

    /**
     * Busca em largura (BFS) para encontrar um caminho aumentante.
     * @param source Vértice de origem.
     * @return Gargalo do fluxo encontrado no caminho aumentante, ou 0 caso contrário.
     */
    private int bfs(String source) {
        Queue<String> queue = new LinkedList<>();
        parent = new HashMap<>();
        visited.add(source); // Marca o vértice de origem como visitado

        // Adiciona o vértice de origem à fila
        queue.offer(source);

        while (!queue.isEmpty()) {
            String currentVertex = queue.poll();
            if (currentVertex.equals(sink)) break;

            // Itera sobre os vizinhos do vértice atual
            for (String neighbor : graph.findAdjacentVertices(currentVertex)) {
                // Calcula o fluxo restante disponível na aresta
                int remainingFlow = findRemainingFlow(currentVertex, neighbor);

                if (!visited.contains(neighbor) && remainingFlow > 0) {
                    // Marca o vizinho como visitado
                    visited.add(neighbor);
                    // Salva o vértice atual como pai do vizinho
                    parent.put(neighbor, currentVertex);
                    // Adiciona o vizinho à fila
                    queue.offer(neighbor);
                }
            }
        }

        String sinkParent = parent.get(sink);

        // Se o vértice sumidouro não for alcançado, retorna 0
        if (sinkParent == null) return 0;

        // Calcula gargalo do caminho que vai da origem até o sumidouro
        int bottleneck = findPathBottleneck();

        // Atualiza o fluxo do caminho com o gargalo encontrado
        updatePathFlow(bottleneck);

        // Retorna o gargalo para ser adicionado ao fluxo máximo
        return bottleneck;
    }

    /**
     * Calcula o gargalo de um caminho que vai da origem até o sumidouro.
     * @return O gargalo encontrado no caminho.
     */
    private int findPathBottleneck() {
        String currentVertex = sink;
        String previousVertex = parent.get(sink);

        int bottleneck = INF;
        while (previousVertex != null) {
            bottleneck = Math.min(bottleneck, findRemainingFlow(previousVertex, currentVertex));
            currentVertex = previousVertex;
            previousVertex = parent.get(previousVertex);
        }
        return bottleneck;
    }

    /**
     * Atualiza o fluxo de um caminho com o gargalo encontrado.
     * @param bottleneck gargalo encontrado no caminho.
     */
    private void updatePathFlow(int bottleneck) {
        String previousVertex = parent.get(sink);
        String currentVertex = sink;

        while (previousVertex != null) {
            updateEdgeFlow(previousVertex, currentVertex, bottleneck);
            currentVertex = previousVertex;
            previousVertex = parent.get(previousVertex);
        }
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
