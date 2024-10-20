package br.ufrn.imd.solutions;

import br.ufrn.imd.representations.AdjacencyMatrixDigraph;
import br.ufrn.imd.representations.Graph;
import br.ufrn.imd.utils.GraphFileReader;

public class SolutionsDigraph {
    private Graph graph;

    private final GraphFileReader graphFileReader;

    public SolutionsDigraph() {
        this.graphFileReader = new GraphFileReader();
    }



    private void readAdjacencyMatrix(String fileName) {
        setGraph(new AdjacencyMatrixDigraph());
        graphFileReader.setFileName(fileName);
        graphFileReader.read(graph);
    }

    public void solution17(String fileName) {
        readAdjacencyMatrix(fileName);
        System.out.println("Matriz de Adjacência:");
        ((AdjacencyMatrixDigraph) graph).printMatrix();
        graph.printGraph();
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
