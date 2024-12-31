package br.ufrn.imd.solutions;

import br.ufrn.imd.representations.AdjacencyMatrixDigraph;
import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import br.ufrn.imd.utils.CapacityGraphFileReader;
import br.ufrn.imd.utils.DijkstraAlgorithm;
import br.ufrn.imd.utils.FordFulkersonSolver;
import br.ufrn.imd.utils.KruskalAlgorithm;
import br.ufrn.imd.utils.PrimAlgorithm;
import br.ufrn.imd.utils.WeightedGraphFileReader;
import br.ufrn.imd.utils.EdmondsKarpSolver;

public class SolutionsGraph2 {
    private AdjacencyMatrixGraph graph;

    private final WeightedGraphFileReader weightedGraphFileReader;
    private final CapacityGraphFileReader capacityGraphFileReader;

    public SolutionsGraph2() {
        this.capacityGraphFileReader = new CapacityGraphFileReader();
        this.weightedGraphFileReader = new WeightedGraphFileReader();
    }

    public void solution1(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixGraph();
        weightedGraphFileReader.read(graph);

        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.findMinimumSpanningTree(graph);
    }

    public void solution2(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixGraph();
        weightedGraphFileReader.read(graph);

        PrimAlgorithm prim = new PrimAlgorithm();
        prim.findMinimumSpanningTree(graph);
    }

    public void solution3(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixGraph();
        weightedGraphFileReader.read(graph);
    }

    public void solution4(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixDigraph();
        weightedGraphFileReader.read(graph);
    }

    public void solution5(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixDigraph();
        weightedGraphFileReader.read(graph);

        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
        dijkstra.findShortestPaths(graph, "4");
    }

    public void solution6(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixDigraph();
        weightedGraphFileReader.read(graph);
    }

    public void solution7(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixDigraph();
        weightedGraphFileReader.read(graph);
    }

    public void solution8(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixGraph();
        weightedGraphFileReader.read(graph);
    }

    public void solution9(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixDigraph();
        weightedGraphFileReader.read(graph);
    }

    public void solution10(String fileName) {
        capacityGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixDigraph();
        capacityGraphFileReader.read(graph);
        FordFulkersonSolver solver = new FordFulkersonSolver(graph);
        System.out.println("O fluxo máximo é: " + solver.solve("s", "t"));
    }

    public void solution11(String fileName) {
        capacityGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixDigraph();
        capacityGraphFileReader.read(graph);
        EdmondsKarpSolver solver = new EdmondsKarpSolver(graph);
        System.out.println("O fluxo máximo é: " + solver.solve("s", "t"));
    }

    public void setGraph(AdjacencyMatrixGraph graph) {
        this.graph = graph;
    }
}
