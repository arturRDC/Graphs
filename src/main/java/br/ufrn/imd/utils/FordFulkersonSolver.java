package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import br.ufrn.imd.representations.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FordFulkersonSolver {
    private final AdjacencyMatrixGraph graph;
    private final List<String> visited;
    private String sink;
    private List<List<Integer>> flowMatrix;
    private static final int INF = Integer.MAX_VALUE / 2;

    public FordFulkersonSolver(AdjacencyMatrixGraph graph) {
        this.graph = graph;
        this.visited = new ArrayList<>();
        initializeFlowMatrix(graph);
    }

    public void initializeFlowMatrix(Graph graph) {
        int numberOfVertices = graph.getNumberOfVertices();
        flowMatrix = new ArrayList<>();

        for (int i = 0; i < numberOfVertices; i++) {
            List<Integer> row = new ArrayList<>(Collections.nCopies(numberOfVertices, 0));
            flowMatrix.add(row);
        }
    }

    public int solve(String source, String sink) {
        int maxFlow = 0;
        setSink(sink);
        for (int flow = dfs(source, INF); flow != 0; flow = dfs(source, INF)) {
            visited.clear();
            maxFlow += flow;
        }
        return maxFlow;
    }

    private void setSink(String sink) {
        this.sink = sink;
    }

    private int dfs(String vertex, int flow) {
        // Vértice sumidouro encontrado
        if (vertex.equals(sink)) {
            return flow;
        }

        visited.add(vertex);

        for (String neighbor : graph.findAdjacentVertices(vertex)) {
            int remainingFlow = findRemainingFlow(vertex, neighbor);
            if (remainingFlow > 0 && !visited.contains(neighbor)) {
                int bottleneck = dfs(neighbor, Math.min(flow, remainingFlow));

                if (bottleneck > 0) {
                    updateEdgeFlow(vertex, neighbor, bottleneck);
                    return bottleneck;
                }
            }
        }
        return 0; // Vértice sumidouro não encontrado
    }

    private void updateEdgeFlow(String vertex, String neighbor, int bottleneck) {
        int vertexIndex = graph.getVertices().indexOf(vertex);
        int neighborIndex = graph.getVertices().indexOf(neighbor);

        int flow = flowMatrix.get(vertexIndex).get(neighborIndex);
        int reverseFlow = flowMatrix.get(neighborIndex).get(vertexIndex);

        // Atualizar o fluxo para a aresta
        flowMatrix.get(vertexIndex).set(neighborIndex, flow + bottleneck);

        // Atualizar o fluxo para a aresta de retorno
        flowMatrix.get(neighborIndex).set(vertexIndex, reverseFlow - bottleneck);
    }

    private int findRemainingFlow(String vertex, String neighbor) {
        int vertexIndex = graph.getVertices().indexOf(vertex);
        int neighborIndex = graph.getVertices().indexOf(neighbor);

        int flow = flowMatrix.get(vertexIndex).get(neighborIndex);
        int capacity = graph.getCapacity(vertex, neighbor);


        return capacity - flow;
    }
}
