package br.ufrn.imd.solutions;

import br.ufrn.imd.representations.AdjacencyMatrixDigraph;
import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import br.ufrn.imd.utils.*;
import java.util.List;
import br.ufrn.imd.utils.BellmanFordAlgorithm;
import br.ufrn.imd.utils.CapacityGraphFileReader;
import br.ufrn.imd.utils.DijkstraAlgorithm;
import br.ufrn.imd.utils.FloydWarshallAlgorithm;
import br.ufrn.imd.utils.FordFulkersonSolver;
import br.ufrn.imd.utils.KruskalAlgorithm;
import br.ufrn.imd.utils.PrimAlgorithm;
import br.ufrn.imd.utils.WeightedGraphFileReader;
import br.ufrn.imd.utils.EdmondsKarpSolver;
import br.ufrn.imd.utils.HierholzerSolver;

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

        ChuLiuEdmondsAlgorithm chuLiuEdmonds = new ChuLiuEdmondsAlgorithm();
        List<String[]> result = chuLiuEdmonds.findMinimumSpanningArborescence((AdjacencyMatrixDigraph) graph, "1");

        System.out.println("Árvore Geradora Mínima Direcionada:");
        for (String[] edge : result) {
            System.out.println(edge[0] + " -> " + edge[1]);
        }
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

        BellmanFordAlgorithm bellmanFord = new BellmanFordAlgorithm();
        bellmanFord.findShortestPaths(graph, "4");
    }

    public void solution7(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixDigraph();
        weightedGraphFileReader.read(graph);

        FloydWarshallAlgorithm floydWarshall = new FloydWarshallAlgorithm();
        floydWarshall.findAllPairsShortestPaths(graph);
    }

    public void solution8(String fileName) {
        weightedGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixDigraph();
        weightedGraphFileReader.read(graph);
        HierholzerSolver solver = new HierholzerSolver(graph);
        System.out.println("Calculando ciclo euleriano...");
        solver.solve(graph.getVertices().get(0));
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
