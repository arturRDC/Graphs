package br.ufrn.imd.representations;

public class IncidenceMatrixGraph extends Graph {
    protected int[][] incidenceMatrix; // Matriz de incidência do grafo
    protected int edgeCount; // Contador de arestas

    // Construtor da classe, inicializa a matriz de incidência
    public IncidenceMatrixGraph() {
        super();
        this.incidenceMatrix = new int[0][0]; // Inicializa como matriz vazia
        this.edgeCount = 0; // Contador de arestas inicia em 0
    }

    // Método para adicionar uma aresta entre dois vértices em um grafo não direcionado
    @Override
    public void addEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source); // Obtém o índice do vértice de origem
        int destIndex = vertices.indexOf(destination); // Obtém o índice do vértice de destino

        if (sourceIndex != -1 && destIndex != -1) {
            if (edgeExists(sourceIndex, destIndex)) {
                return;  // Aresta já existe, não faz nada
            }
            increaseMatrixSize();
            incidenceMatrix[edgeCount][sourceIndex] = 1; // Marca a origem
            incidenceMatrix[edgeCount][destIndex] = 1; // Marca o destino
            edgeCount++; // Incrementa o contador de arestas
        }
    }

    // Verifica se uma aresta já existe entre dois vértices
    private boolean edgeExists(int sourceIndex, int destIndex) {
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[i][sourceIndex] == 1 && incidenceMatrix[i][destIndex] == 1) {
                return true; // Aresta já existe
            }
        }
        return false; // Aresta não existe
    }

    // Aumenta o tamanho da matriz de incidência para acomodar uma nova aresta
    protected void increaseMatrixSize() {
        int[][] newMatrix = new int[edgeCount + 1][vertices.size()]; // Cria nova matriz com tamanho aumentado
        for (int i = 0; i < edgeCount; i++) {
            newMatrix[i] = incidenceMatrix[i]; // Copia as arestas existentes
        }
        incidenceMatrix = newMatrix; // Atualiza a matriz de incidência
    }

    // Remove uma aresta entre dois vértices
    @Override
    public void removeEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);

        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[i][sourceIndex] == 1 && incidenceMatrix[i][destIndex] == 1) {
                incidenceMatrix[i] = incidenceMatrix[edgeCount - 1];
                edgeCount--;
                return;
            }
        }
    }

    @Override
    protected void addVertexSpecific(String vertex) {
        for (int i = 0; i < edgeCount; i++) {
            int[] newRow = new int[vertices.size()];
            System.arraycopy(incidenceMatrix[i], 0, newRow, 0, incidenceMatrix[i].length);
            incidenceMatrix[i] = newRow;
        }
    }

    @Override
    protected void removeVertexSpecific(String vertex, int index) {
        for (int i = 0; i < edgeCount; i++) {
            for (int j = index; j < vertices.size() - 1; j++) {
                incidenceMatrix[i][j] = incidenceMatrix[i][j + 1];
            }
        }
    }

    @Override
    public void printGraph() {
        System.out.println("\nGraph (From Incidence Matrix Representation):");
        for (int i = 0; i < edgeCount; i++) {
            StringBuilder edgeString = new StringBuilder("Edge " + (i + 1) + ": ");
            String vertex1 = "", vertex2 = "";

            for (int j = 0; j < vertices.size(); j++) {
                if (incidenceMatrix[i][j] == 1) {
                    if (vertex1.isEmpty()) {
                        vertex1 = vertices.get(j);
                    } else {
                        vertex2 = vertices.get(j);
                    }
                }
            }
            edgeString.append(vertex1).append(", ").append(vertex2);
            System.out.println(edgeString);
        }
    }

    public void printMatrix() {
        System.out.println("Incidence Matrix:");
        System.out.print("   ");
        for (String vertex : vertices) {
            System.out.printf("%3s", vertex);
        }
        System.out.println();

        for (int i = 0; i < edgeCount; i++) {
            System.out.printf("E%-2d", (i + 1));
            for (int j = 0; j < vertices.size(); j++) {
                System.out.printf("%3d", incidenceMatrix[i][j]);
            }
            System.out.println();
        }
    }

    public int[][] toIncidenceMatrix() {
        int[][] matrix = new int[edgeCount][vertices.size()];
        for (int i = 0; i < edgeCount; i++) {
            System.arraycopy(incidenceMatrix[i], 0, matrix[i], 0, vertices.size());
        }
        return matrix;
    }
}