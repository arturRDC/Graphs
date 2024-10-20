package br.ufrn.imd.solutions;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import br.ufrn.imd.representations.Graph;
import br.ufrn.imd.utils.GraphFileReader;

public class SolutionsGraph {
    private Graph graph;

    private final GraphFileReader graphFileReader;

    public SolutionsGraph() {
        this.graphFileReader = new GraphFileReader();
    }



    private void readAdjacencyMatrix(String fileName) {
        setGraph(new AdjacencyMatrixGraph());
        graphFileReader.setFileName(fileName);
        graphFileReader.read(graph);
    }

    public void solution2(String fileName) {
        readAdjacencyMatrix(fileName);
        System.out.println("Matriz de Adjacência:");
        ((AdjacencyMatrixGraph) graph).printMatrix();
        graph.printGraph();
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
