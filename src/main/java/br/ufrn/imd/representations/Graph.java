package br.ufrn.imd.representations;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public abstract class Graph {
    protected List<String> vertices;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public void addVertex(String vertex) {
        vertices.add(vertex);
        addVertexSpecific(vertex);
    }

    public void removeVertex(String vertex) {
        int index = vertices.indexOf(vertex);
        if (index != -1) {
            vertices.remove(index);
            removeVertexSpecific(vertex, index);
        }
    }

    public List<String> getVertices() {
        return vertices;
    }

    public void bfs(String startVertex) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(startVertex);
        queue.offer(startVertex);

        while (!queue.isEmpty()) {
            // Remove primeiro vértice da fila e imprime
            String currentVertex = queue.poll();
            System.out.print(currentVertex + " ");

            // Obtém todos os vértices adjacentes ao atual
            List<String> adjacentVertices = findAdjacentVertices(currentVertex);

            // Adiciona os vértices adjacentes não visitados à fila
            for (String neighbor : adjacentVertices) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        System.out.println();
    }

    // Retorna a lista de vértices adjacentes a um vértice
    public abstract List<String> findAdjacentVertices(String vertex);


    public boolean hasVertex(String vertex) {
        return vertices.contains(vertex);
    }
    public abstract void addEdge(String source, String destination);
    public abstract boolean hasEdge(String source, String destination);
    public abstract void removeEdge(String source, String destination);
    public abstract void printGraph();

    protected abstract void addVertexSpecific(String vertex);
    protected abstract void removeVertexSpecific(String vertex, int index);

    public abstract String[] getNeighbors(String vertex);

    // Encontra todos os subgrafos do grafo e associa cada vértice a um identificador de subgrafo
    public Map<String, Integer> findConnectedComponents(Graph graph) {
        Map<String, Integer> componentMap = new HashMap<>();  // Armazena os subgrafos conexos
        int componentId = 0;  // ID do subgrafo atual

        // Percorre todos os vértices do grafo
        for (String vertex : graph.vertices) {
            if (!componentMap.containsKey(vertex)) {
                iterativeDFS(vertex, componentId, graph, componentMap);  // Usa DFS iterativa
                componentId++;
            }
        }
        return componentMap;
    }

    // Realiza uma busca em profundidade iterativa (DFS) para marcar vértices do mesmo subgrafo
    private void iterativeDFS(String startVertex, int componentId, Graph graph, Map<String, Integer> componentMap) {
        Deque<String> stack = new ArrayDeque<>();
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            // Verifica se o vértice já foi visitado
            if (!componentMap.containsKey(vertex)) {
                // Marca o vértice como pertencente ao subgrafo atual
                componentMap.put(vertex, componentId);

                // Itera sobre todos os vizinhos do vértice atual
                for (String neighbor : graph.getNeighbors(vertex)) {
                    // Se o vizinho ainda não foi visitado, adiciona-o à pilha
                    if (!componentMap.containsKey(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }

    // Verifica se o grafo é conexo
    public boolean isConnected(Graph graph) {
        Map<String, Integer> components = findConnectedComponents(graph);
        return components.values().stream().allMatch(id -> id == 0);
    }
}