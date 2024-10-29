package br.ufrn.imd.utils;

import br.ufrn.imd.representations.Graph;
import java.util.*;

public class ArticulationPoints {
    private final Graph graph;
    private final Map<String, Integer> dfsNumber;
    private final Map<String, Integer> lowpt;
    private final Set<String> visited;
    private int dfsTime;
    private int rootChildCount;

    private final Set<String> articulationPoints;
    private final List<Set<String>> blocks;
    private final List<List<Edge>> blockEdges;
    private final Stack<Edge> edgeStack;

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

    public void findArticulationPointsAndBlocks() {
        initializeStructures();
        String startVertex = graph.getVertices().get(0);
        dfsTraversal(startVertex, startVertex);
    }

    private void initializeStructures() {
        clearDataStructures();
        initializeDfsValues();
    }

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

    private void initializeDfsValues() {
        for (String vertex : graph.getVertices()) {
            dfsNumber.put(vertex, 0);
            lowpt.put(vertex, -1);
        }
    }

    private void dfsTraversal(String currentVertex, String parentVertex) {
        visitVertex(currentVertex);

        for (String neighbor : graph.findAdjacentVertices(currentVertex)) {
            processNeighbor(currentVertex, parentVertex, neighbor);
        }
    }

    private void processNeighbor(String currentVertex, String parentVertex, String neighbor) {
        Edge currentEdge = new Edge(currentVertex, neighbor);

        if (isUnvisitedVertex(neighbor)) {
            processUnvisitedNeighbor(currentVertex, parentVertex, neighbor, currentEdge);
        } else if (isValidBackEdge(neighbor, parentVertex, currentVertex)) {
            processBackEdge(currentVertex, neighbor, currentEdge);
        }
    }

    private boolean isUnvisitedVertex(String vertex) {
        return !visited.contains(vertex);
    }

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

    private void processBackEdge(String currentVertex, String neighbor, Edge currentEdge) {
        edgeStack.push(currentEdge);
        updateLowptForBackEdge(currentVertex, neighbor);
    }

    private void visitVertex(String vertex) {
        visited.add(vertex);
        dfsNumber.put(vertex, dfsTime);
        lowpt.put(vertex, dfsTime);
        dfsTime++;
    }

    private boolean isArticulationPoint(String currentVertex, String parentVertex, String childVertex) {
        return isRootArticulationPoint(currentVertex, parentVertex) ||
               isNonRootArticulationPoint(currentVertex, parentVertex, childVertex);
    }

    private boolean isRoot(String vertex, String parentVertex) {
        return vertex.equals(parentVertex);
    }

    private boolean isRootArticulationPoint(String currentVertex, String parentVertex) {
        return isRoot(currentVertex, parentVertex) && rootChildCount > 1;
    }

    private boolean isNonRootArticulationPoint(String currentVertex, String parentVertex, String childVertex) {
        return !isRoot(currentVertex, parentVertex) && hasNoBackEdgesToAncestors(currentVertex, childVertex);
    }

    private boolean startsNewBlock(String currentVertex, String childVertex) {
        return hasNoBackEdgesToAncestors(currentVertex, childVertex);
    }

    private boolean hasNoBackEdgesToAncestors(String currentVertex, String childVertex) {
        return lowpt.get(childVertex) >= dfsNumber.get(currentVertex);
    }

    private void updateLowptForParent(String parentVertex, String childVertex) {
        lowpt.put(parentVertex, Math.min(lowpt.get(parentVertex), lowpt.get(childVertex)));
    }

    private boolean isValidBackEdge(String neighbor, String parentVertex, String currentVertex) {
        return !neighbor.equals(parentVertex) && dfsNumber.get(neighbor) < dfsNumber.get(currentVertex);
    }

    private void updateLowptForBackEdge(String currentVertex, String neighbor) {
        lowpt.put(currentVertex, Math.min(lowpt.get(currentVertex), dfsNumber.get(neighbor)));
    }

    private void createNewBlock(Edge currentEdge) {
        Set<String> blockVertices = new HashSet<>();
        List<Edge> blockEdgeList = new ArrayList<>();

        processEdgesUntilCurrentEdge(currentEdge, blockVertices, blockEdgeList);

        blocks.add(blockVertices);
        blockEdges.add(blockEdgeList);
    }

    private void processEdgesUntilCurrentEdge(Edge currentEdge, Set<String> blockVertices, List<Edge> blockEdgeList) {
        Edge edge;
        do {
            edge = edgeStack.pop();
            addEdgeToBlock(edge, blockVertices, blockEdgeList);
        } while (!edge.equals(currentEdge));
    }

    private void addEdgeToBlock(Edge edge, Set<String> blockVertices, List<Edge> blockEdgeList) {
        blockVertices.add(edge.source());
        blockVertices.add(edge.target());
        blockEdgeList.add(edge);
    }

    public void printResults() {
        printArticulationPoints();
        printBlocks();
    }

    private void printArticulationPoints() {
        System.out.println("Pontos de Articulação:");
        if (articulationPoints.isEmpty()) {
            System.out.println("Não há pontos de articulação no grafo.");
            return;
        }
        articulationPoints.forEach(System.out::println);
    }

    private void printBlocks() {
        if (blocks.isEmpty()) {
            System.out.println("Não há blocos no grafo.");
            return;
        }

        for (int i = 0; i < blocks.size(); i++) {
            printBlock(blocks.get(i), i);
        }
    }

    private void printBlock(Set<String> block, int index) {
        System.out.println("\nBloco " + (index + 1) + ":");
        System.out.println("Vértices: " + block);
        System.out.println("Arestas: " + formatBlockEdges(blockEdges.get(index)));
    }

    private String formatBlockEdges(List<Edge> edges) {
        return String.join(", ", edges.stream().map(Edge::toString).toList());
    }
}
