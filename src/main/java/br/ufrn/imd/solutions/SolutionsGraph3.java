package br.ufrn.imd.solutions;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import br.ufrn.imd.utils.CostGraphFileReader;


public class SolutionsGraph3 {
    private AdjacencyMatrixGraph graph;

    private final CostGraphFileReader costGraphFileReader;

    public SolutionsGraph3() {
        this.costGraphFileReader = new CostGraphFileReader();
    }

    public void solution1(String fileName) {
        costGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixGraph();
        costGraphFileReader.read(graph);


    }

    public void solution2(String fileName) {
        costGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixGraph();
        costGraphFileReader.read(graph);


    }

    public void solution3(String fileName) {
        costGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixGraph();
        costGraphFileReader.read(graph);


    }


    public void setGraph(AdjacencyMatrixGraph graph) {
        this.graph = graph;
    }
}
