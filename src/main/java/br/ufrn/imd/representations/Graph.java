package br.ufrn.imd.representations;
import java.util.*;

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
    protected abstract List<String> findAdjacentVertices(String vertex);


    public boolean hasVertex(String vertex) {
        return vertices.contains(vertex);
    }
    public abstract void addEdge(String source, String destination);
    public abstract boolean hasEdge(String source, String destination);
    public abstract void removeEdge(String source, String destination);
    public abstract void printGraph();

    protected abstract void addVertexSpecific(String vertex);
    protected abstract void removeVertexSpecific(String vertex, int index);
}