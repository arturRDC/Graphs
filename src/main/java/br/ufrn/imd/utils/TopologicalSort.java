package br.ufrn.imd.utils;

import br.ufrn.imd.representations.Graph;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TopologicalSort {
    private final Graph graph;
    private final Set<String> visitedVertices;
    private final Deque<String> topologicalOrder;

    public TopologicalSort(Graph graph) {
        this.graph = graph;
        this.visitedVertices = new HashSet<>();
        this.topologicalOrder = new LinkedList<>();
    }

    public List<String> sort() {
        // Percorre todos os vértices do grafo
        for (String vertex : graph.getVertices()) {
            // Se o vértice ainda não foi visitado, inicia a DFS a partir dele
            if (!visitedVertices.contains(vertex)) {
                dfs(vertex);
            }
        }

        // Retorna a ordem topológica
        return new ArrayList<>(topologicalOrder);
    }

    private void dfs(String vertex) {
        // Marca o vértice como visitado
        visitedVertices.add(vertex);

        // Visita todos os vizinhos do vértice
        for (String neighbor : graph.findAdjacentVertices(vertex)) {
            if (!visitedVertices.contains(neighbor)) {
                dfs(neighbor);
            }
        }

        // Adiciona o vértice à pilha da ordem topológica
        topologicalOrder.offerFirst(vertex);
    }
}