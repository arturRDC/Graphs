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

    public int getNumberOfVertices() {
        return vertices.size();
    }

    public int getNumberOfEdges() {
        int edgeCount = 0;

        for (String vertex : vertices) {
            edgeCount += findAdjacentVertices(vertex).size();
        }

        // Dividir por 2, pois cada aresta é contada duas vezes na lista de adjacência
        return edgeCount / 2;
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

    // Encontra todos os subgrafos do grafo e associa cada vértice a um identificador de subgrafo
    public Map<String, Integer> findConnectedComponents(Graph graph) {
        Map<String, Integer> componentMap = new HashMap<>();  // Armazena os subgrafos conexos
        int componentId = 0;  // ID do subgrafo atual

        // Percorre todos os vértices do grafo
        for (String vertex : graph.vertices) {
            if (!componentMap.containsKey(vertex)) {
                dfs(vertex, componentId, componentMap);  // Usa DFS iterativa
                componentId++;
            }
        }
        return componentMap;
    }

    public void dfs(String startVertex, int componentId, Map<String, Integer> componentMap) {
        Deque<String> stack = new ArrayDeque<>();
        List<String> visitedVertices = new ArrayList<>(); // Lista para armazenar os vértices visitados
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            // Verifica se o vértice já foi visitado
            if (!componentMap.containsKey(vertex)) {
                // Marca o vértice como pertencente ao subgrafo atual
                componentMap.put(vertex, componentId);
                visitedVertices.add(vertex); // Adiciona o vértice à lista de visitados

                // Itera sobre todos os vizinhos do vértice atual
                for (String neighbor : findAdjacentVertices(vertex)) {
                    // Se o vizinho ainda não foi visitado, adiciona-o à pilha
                    if (!componentMap.containsKey(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        // Imprime todos os vértices visitados após a travessia
        System.out.println("Ordem (DFS): [" + String.join(",", visitedVertices) + "]");
    }

    // Verifica se o grafo é conexo
    public boolean isConnected(Graph graph) {
        Map<String, Integer> components = findConnectedComponents(graph);
        return components.values().stream().allMatch(id -> id == 0);
    }

    // Determina se dois vértices são adjacentes
    public boolean areAdjacent(String vertex1, String vertex2) {
        return findAdjacentVertices(vertex1).contains(vertex2);
    }

    public void dfsWithEntryExit(String startVertex) {
        // Maps para armazenar o tempo de entrada e saída de cada vértice
        Map<String, Integer> entryTime = new HashMap<>();
        Map<String, Integer> exitTime = new HashMap<>();
        Set<String> visited = new HashSet<>();
        int[] entryCounter = {1};  // Contador de entrada
        int[] exitCounter = {1};    // Contador de saída

        // Inicia a DFS com o primeiro vértice
        dfsWithEntryExitHelper(startVertex, visited, entryTime, exitTime, entryCounter, exitCounter);

        // Exibe os tempos de entrada e saída de cada vértice
        System.out.println("Tempos de entrada e saída:");
        for (String vertex : vertices) {
            System.out.println("Vértice " + vertex + ": entrada = " + entryTime.get(vertex) + ", saída = " + exitTime.get(vertex));
        }
    }
    private void dfsWithEntryExitHelper(String vertex, Set<String> visited, Map<String, Integer> entryTime, Map<String, Integer> exitTime, int[] entryCounter, int[] exitCounter) {
        // Marca o vértice como visitado e define o tempo de entrada
        visited.add(vertex);
        entryTime.put(vertex, entryCounter[0]++);  // Incrementa apenas o contador de entrada

        // Para cada vizinho não visitado, realiza a DFS recursiva
        for (String neighbor : findAdjacentVertices(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsWithEntryExitHelper(neighbor, visited, entryTime, exitTime, entryCounter, exitCounter);
            }
        }

        // Define o tempo de saída após explorar todos os vizinhos
        exitTime.put(vertex, exitCounter[0]++);
    }

}