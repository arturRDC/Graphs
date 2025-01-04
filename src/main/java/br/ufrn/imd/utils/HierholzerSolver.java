package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Classe que implementa o algoritmo de Hierholzer para encontrar ciclos eulerianos.
 */
public class HierholzerSolver {

    /**
     * Grafo representado por matriz de adjacência.
     */
    private final AdjacencyMatrixGraph graph;
    /**
     * Conjunto de arestas visitadas.
     */
    private final Set<Edge> visited;
    /**
     * Indica se um ciclo foi encontrado.
     */
    private boolean cycleFound;
    /**
     * Vértice inicial do ciclo.
     */
    private String startVertex;
    /**
     * Caminho do ciclo euleriano.
     */
    private LinkedList<String> cyclePath;
    /**
     * Caminho utilizado na busca DFS.
     */
    private LinkedList<String> dfsPath;
    /**
     * Índice para inserção de ciclos no caminho principal.
     */
    private int insertionIndex;

    /**
     * Construtor da classe.
     *
     * @param graph O grafo representado por matriz de adjacência.
     */
    public HierholzerSolver(AdjacencyMatrixGraph graph) {
        this.graph = graph;
        this.visited = new HashSet<>();
        this.cyclePath = new LinkedList<>();
        this.dfsPath = new LinkedList<>();
        this.insertionIndex = 0;
    }


    /**
     * Resolve o problema de encontrar um ciclo euleriano.
     */
    public void solve(String startVertex) {
        initializeSolver(startVertex);
        processInitialCycle();
        refineCycle();
        printCyclePath();
    }

    /**
     * Inicializa as variáveis para a resolução.
     *
     * @param startVertex O vértice inicial.
     */
    private void initializeSolver(String startVertex) {
        this.startVertex = startVertex;
        dfsPath.addFirst(startVertex); // Inicia o caminho DFS com o vértice inicial
    }

    /**
     * Processa o ciclo inicial a partir do vértice inicial.
     */
    private void processInitialCycle() {
        findCycle(startVertex); // Encontra o ciclo a partir do vértice inicial
        removeEdges(); // Remove as arestas do ciclo encontrado
    }

    /**
     * Refina o ciclo inicial, explorando novos ciclos possíveis.
     */
    private void refineCycle() {
        for (int i = 0; i < cyclePath.size(); i++) {
            String vertex = cyclePath.get(i);
            insertionIndex = cyclePath.indexOf(vertex) + 1;
            do {
                startVertex = vertex;
                resetDfsPath(); // Reinicia o caminho DFS para o próximo ciclo
                findCycle(vertex); // Encontra um novo ciclo a partir do vértice
                if (cycleFound) {
                    removeEdges(); // Remove as arestas utilizadas no novo ciclo
                }
            } while (cycleFound); // Continua enquanto um ciclo for encontrado
        }
    }

    /**
     * Imprime o caminho do ciclo final.
     */
    private void printCyclePath() {
        System.out.println(cyclePath);
    }

    /**
     * Reinicia o caminho DFS.
     */
    private void resetDfsPath() {
        dfsPath.clear(); // Limpa o caminho DFS
        dfsPath.addFirst(startVertex); // Adiciona o vértice inicial ao caminho DFS
    }

    /**
     * Encontra um ciclo a partir do vértice atual.
     *
     * @param current O vértice atual.
     */
    private void findCycle(String current) {
        cycleFound = false;
        dfs(current);
    }

    /**
     * Remove as arestas utilizadas no ciclo encontrado.
     */
    private void removeEdges() {
        for (int i = 1; i < cyclePath.size(); i++) {
            String v1 = cyclePath.get(i - 1);
            String v2 = cyclePath.get(i);
            graph.removeEdge(v1, v2); // Remove as arestas que fazem parte do ciclo
        }
    }

    /**
     * Realiza a busca em profundidade (DFS).
     *
     * @param current O vértice atual.
     */
    private void dfs(String current) {
        List<String> neighbors = graph.findAdjacentVertices(current);
        for (String neighbor : neighbors) {

            Edge edge = new Edge(current, neighbor);
            if (cycleFound) return; // Se um ciclo foi encontrado, termina a busca

            if (neighbor.equals(startVertex)) {
                processCycleEnd(); // Finaliza o ciclo
                return;
            } else if (!visited.contains(edge)) {
                visitEdge(edge, neighbor); // Visita a aresta e continua a DFS
            }
        }

    }

    /**
     * Processa o final do ciclo.
     */
    private void processCycleEnd() {
        dfsPath.addLast(startVertex); // Adiciona o vértice inicial ao final do ciclo
        if (cyclePath.size() > 1) {
            String cycleStart = cyclePath.get(insertionIndex - 1);
            // Remove o primeiro vértice do ciclo se for igual ao anterior
            if (cycleStart.equals(dfsPath.getFirst())) {
                dfsPath.removeFirst();
            }
        }
        // Insere o ciclo encontrado no caminho principal
        cyclePath.addAll(insertionIndex, dfsPath);
        dfsPath.clear(); // Limpa o caminho DFS
        cycleFound = true; // Marca que o ciclo foi encontrado
    }

    /**
     * Marca uma aresta como visitada e continua a DFS.
     *
     * @param edge     Aresta visitada.
     * @param neighbor Vizinho atual na DFS.
     */
    private void visitEdge(Edge edge, String neighbor) {
        visited.add(edge); // Marca a aresta como visitada
        dfsPath.add(neighbor); // Adiciona o vizinho ao caminho DFS
        dfs(neighbor); // Continua a busca DFS
    }

    /**
     * Representação de uma aresta
     */
    record Edge(String v1, String v2){}
}
