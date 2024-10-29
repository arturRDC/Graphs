package br.ufrn.imd.utils;

import br.ufrn.imd.representations.Graph;
import java.util.*;

/**
 * Classe responsável por encontrar pontos de articulação e blocos em um grafo.
 * Um ponto de articulação é um vértice que, quando removido, aumenta o número de componentes conectados do grafo.
 */
public class ArticulationPoints {
    private final Graph graph;
    // Mapeia vértices para seus números DFS (ordem de descoberta)
    private final Map<String, Integer> dfsNumber;
    // Mapeia vértices para seus valores lowpt (menor número DFS alcançável)
    private final Map<String, Integer> lowpt;
    // Conjunto de vértices já visitados
    private final Set<String> visited;
    // Contador para números DFS
    private int dfsTime;
    // Contador de filhos da raiz na árvore DFS
    private int rootChildCount;

    // Armazena os pontos de articulação encontrados
    private final Set<String> articulationPoints;
    // Lista de conjuntos de vértices que formam blocos
    private final List<Set<String>> blocks;
    // Lista de arestas que pertencem a cada bloco
    private final List<List<Edge>> blockEdges;
    // Pilha para armazenar arestas durante o processamento
    private final Stack<Edge> edgeStack;

    /**
     * Classe record para representar uma aresta do grafo
     */
    private record Edge(String source, String target) {
        @Override
        public String toString() {
            return "(" + source + ", " + target + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (!(obj instanceof Edge other)) return false;
            return source.equals(other.source) && target.equals(other.target);
        }
    }

    /**
     * Construtor que inicializa as estruturas de dados necessárias
     */
    public ArticulationPoints(Graph graph) {
        this.graph = graph;
        this.dfsNumber = new HashMap<>();
        this.lowpt = new HashMap<>();
        this.visited = new HashSet<>();
        this.articulationPoints = new HashSet<>();
        this.blocks = new ArrayList<>();
        this.blockEdges = new ArrayList<>();
        this.edgeStack = new Stack<>();
    }

    /**
     * Metodo principal que inicia a busca por pontos de articulação e blocos
     */
    public void findArticulationPointsAndBlocks() {
        initializeStructures();
        String startVertex = graph.getVertices().get(0);
        dfsTraversal(startVertex, startVertex);
    }

    /**
     * Inicializa todas as estruturas de dados necessárias
     */
    private void initializeStructures() {
        clearDataStructures();
        initializeDfsValues();
    }

    /**
     * Limpa todas as estruturas de dados para uma nova execução
     */
    private void clearDataStructures() {
        dfsNumber.clear();
        lowpt.clear();
        visited.clear();
        articulationPoints.clear();
        blocks.clear();
        blockEdges.clear();
        edgeStack.clear();
        dfsTime = 1;
        rootChildCount = 0;
    }

    /**
     * Inicializa os valores DFS para todos os vértices
     */
    private void initializeDfsValues() {
        for (String vertex : graph.getVertices()) {
            dfsNumber.put(vertex, 0);
            lowpt.put(vertex, -1);
        }
    }

    /**
     * Realiza o percurso DFS no grafo
     */
    private void dfsTraversal(String currentVertex, String parentVertex) {
        visitVertex(currentVertex);

        for (String neighbor : graph.findAdjacentVertices(currentVertex)) {
            processNeighbor(currentVertex, parentVertex, neighbor);
        }
    }

    /**
     * Processa cada vizinho do vértice atual durante a DFS
     */
    private void processNeighbor(String currentVertex, String parentVertex, String neighbor) {
        Edge currentEdge = new Edge(currentVertex, neighbor);

        if (isUnvisitedVertex(neighbor)) {
            processUnvisitedNeighbor(currentVertex, parentVertex, neighbor, currentEdge);
        } else if (isValidBackEdge(neighbor, parentVertex, currentVertex)) {
            processBackEdge(currentVertex, neighbor, currentEdge);
        }
    }

    /**
     * Verifica se um vértice ainda não foi visitado
     */
    private boolean isUnvisitedVertex(String vertex) {
        return !visited.contains(vertex);
    }

    /**
     * Processa um vizinho não visitado durante a DFS
     */
    private void processUnvisitedNeighbor(String currentVertex, String parentVertex, String neighbor, Edge currentEdge) {
        edgeStack.push(currentEdge);
        dfsTraversal(neighbor, currentVertex);
        updateLowptForParent(currentVertex, neighbor);

        if (isRoot(currentVertex, parentVertex)) {
            rootChildCount++;
        }
        if (isArticulationPoint(currentVertex, parentVertex, neighbor)) {
            articulationPoints.add(currentVertex);
        }

        if (startsNewBlock(currentVertex, neighbor)) {
            createNewBlock(currentEdge);
        }
    }

    /**
     * Processa uma aresta de retorno durante a DFS
     */
    private void processBackEdge(String currentVertex, String neighbor, Edge currentEdge) {
        edgeStack.push(currentEdge);
        updateLowptForBackEdge(currentVertex, neighbor);
    }

    /**
     * Marca um vértice como visitado e atualiza seus valores DFS
     */
    private void visitVertex(String vertex) {
        visited.add(vertex);
        dfsNumber.put(vertex, dfsTime);
        lowpt.put(vertex, dfsTime);
        dfsTime++;
    }

    /**
     * Verifica se um vértice é ponto de articulação
     */
    private boolean isArticulationPoint(String currentVertex, String parentVertex, String childVertex) {
        return isRootArticulationPoint(currentVertex, parentVertex) ||
                isNonRootArticulationPoint(currentVertex, parentVertex, childVertex);
    }

    /**
     * Verifica se um vértice é a raiz da árvore DFS
     */
    private boolean isRoot(String vertex, String parentVertex) {
        return vertex.equals(parentVertex);
    }

    /**
     * Verifica se um vértice é raiz e um ponto de articulação
     * Baseado na propriedade de que se a raiz da árvore DFS tem mais de um filho,
     * removê-la aumentaria o número de componentes conectados do grafo se o número de filhos for maior que 1.
     */
    private boolean isRootArticulationPoint(String currentVertex, String parentVertex) {
        return isRoot(currentVertex, parentVertex) && rootChildCount > 1;
    }

    /**
     * Verifica se um vértice não-raiz é ponto de articulação
     * Baseado na propriedade de que se há arestas de retorno para os ancestrais,
     * existe uma conexão para fora da subárvore centrada no nó filho
     */
    private boolean isNonRootArticulationPoint(String currentVertex, String parentVertex, String childVertex) {
        return !isRoot(currentVertex, parentVertex) && hasNoBackEdgesToAncestors(currentVertex, childVertex);
    }

    /**
     * Verifica se um vértice inicia um novo bloco
     */
    private boolean startsNewBlock(String currentVertex, String childVertex) {
        return hasNoBackEdgesToAncestors(currentVertex, childVertex);
    }

    /**
     * Verifica se não existem arestas de retorno para ancestrais
     */
    private boolean hasNoBackEdgesToAncestors(String currentVertex, String childVertex) {
        return lowpt.get(childVertex) >= dfsNumber.get(currentVertex);
    }

    /**
     * Atualiza o valor lowpt de um vértice pai
     */
    private void updateLowptForParent(String parentVertex, String childVertex) {
        lowpt.put(parentVertex, Math.min(lowpt.get(parentVertex), lowpt.get(childVertex)));
    }

    /**
     * Verifica se uma aresta é uma aresta de retorno válida
     */
    private boolean isValidBackEdge(String neighbor, String parentVertex, String currentVertex) {
        return !neighbor.equals(parentVertex) && dfsNumber.get(neighbor) < dfsNumber.get(currentVertex);
    }

    /**
     * Atualiza o valor lowpt para uma aresta de retorno
     */
    private void updateLowptForBackEdge(String currentVertex, String neighbor) {
        lowpt.put(currentVertex, Math.min(lowpt.get(currentVertex), dfsNumber.get(neighbor)));
    }

    /**
     * Cria um novo bloco no grafo
     */
    private void createNewBlock(Edge currentEdge) {
        Set<String> blockVertices = new HashSet<>();
        List<Edge> blockEdgeList = new ArrayList<>();

        processEdgesUntilCurrentEdge(currentEdge, blockVertices, blockEdgeList);

        blocks.add(blockVertices);
        blockEdges.add(blockEdgeList);
    }

    /**
     * Processa as arestas da pilha, adicionando ao bloco, até encontrar a aresta atual
     */
    private void processEdgesUntilCurrentEdge(Edge currentEdge, Set<String> blockVertices, List<Edge> blockEdgeList) {
        Edge edge;
        do {
            edge = edgeStack.pop();
            addEdgeToBlock(edge, blockVertices, blockEdgeList);
        } while (!edge.equals(currentEdge));
    }

    /**
     * Adiciona uma aresta a um bloco
     */
    private void addEdgeToBlock(Edge edge, Set<String> blockVertices, List<Edge> blockEdgeList) {
        blockVertices.add(edge.source());
        blockVertices.add(edge.target());
        blockEdgeList.add(edge);
    }

    /**
     * Imprime os resultados da análise do grafo
     */
    public void printResults() {
        printArticulationPoints();
        printBlocks();
    }

    /**
     * Imprime os pontos de articulação encontrados
     */
    private void printArticulationPoints() {
        System.out.println("Pontos de Articulação:");
        if (articulationPoints.isEmpty()) {
            System.out.println("Não há pontos de articulação no grafo.");
            return;
        }
        articulationPoints.forEach(System.out::println);
    }

    /**
     * Imprime os blocos encontrados
     */
    private void printBlocks() {
        if (blocks.isEmpty()) {
            System.out.println("Não há blocos no grafo.");
            return;
        }

        for (int i = 0; i < blocks.size(); i++) {
            printBlock(blocks.get(i), i);
        }
    }

    /**
     * Imprime as informações de um bloco específico
     */
    private void printBlock(Set<String> block, int index) {
        System.out.println("\nBloco " + (index + 1) + ":");
        System.out.println("Vértices: " + block);
        System.out.println("Arestas: " + formatBlockEdges(blockEdges.get(index)));
    }

    /**
     * Formata a lista de arestas de um bloco para impressão
     */
    private String formatBlockEdges(List<Edge> edges) {
        return String.join(", ", edges.stream().map(Edge::toString).toList());
    }
}