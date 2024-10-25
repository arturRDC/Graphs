package br.ufrn.imd.representations;

public class IncidenceMatrixDigraph extends IncidenceMatrixGraph {

    // Construtor para digrafo
    public IncidenceMatrixDigraph() {
        super();
    }

    // Método para adicionar uma aresta direcionada entre dois vértices
    @Override
    public void addEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);

        if (sourceIndex != -1 && destIndex != -1) {
            if (edgeExists(sourceIndex, destIndex)) {
                return;
            }
            increaseMatrixSize();
            incidenceMatrix[edgeCount][sourceIndex] = 1; // Origem
            incidenceMatrix[edgeCount][destIndex] = -1; // Destino
            edgeCount++;
        }
    }

    // Verifica se uma aresta direcionada já existe
    private boolean edgeExists(int sourceIndex, int destIndex) {
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[i][sourceIndex] == 1 && incidenceMatrix[i][destIndex] == -1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);

        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[i][sourceIndex] == 1 && incidenceMatrix[i][destIndex] == -1) {
                incidenceMatrix[i] = incidenceMatrix[edgeCount - 1];
                edgeCount--;
                return;
            }
        }
    }

    @Override
    public void printGraph() {
        System.out.println("\nDirected Graph (From Incidence Matrix Representation):");
        for (int i = 0; i < edgeCount; i++) {
            StringBuilder edgeString = new StringBuilder("Edge " + (i + 1) + ": ");
            String vertex1 = "", vertex2 = "";

            for (int j = 0; j < vertices.size(); j++) {
                if (incidenceMatrix[i][j] == 1) {
                    vertex1 = vertices.get(j);
                } else if (incidenceMatrix[i][j] == -1) {
                    vertex2 = vertices.get(j);
                }
            }
            edgeString.append(vertex1).append(" -> ").append(vertex2);
            System.out.println(edgeString);
        }
    }
}
