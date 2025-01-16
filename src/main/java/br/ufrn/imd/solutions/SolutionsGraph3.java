package br.ufrn.imd.solutions;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import br.ufrn.imd.utils.CostGraphFileReader;
import br.ufrn.imd.utils.GraspSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


public class SolutionsGraph3 {
    private AdjacencyMatrixGraph graph;

    private final CostGraphFileReader costGraphFileReader;
    private long startTime;
    private long endTime;
    private final List<Long> times;
    private static final int EXECUTIONS_AMOUNT = 3000;

    public SolutionsGraph3() {
        this.costGraphFileReader = new CostGraphFileReader();
        this.times = new ArrayList<>();
    }

    public void solution1(String fileName) {
        readGraphData(fileName);
        System.out.println("Executando Algoritmo Guloso...");
        startTimer();
        // TODO: Algoritmo Guloso
        endTimer();
        printElapsedTime();

//        System.out.println("Executando Busca Local...");
//        startTimer();
        // TODO: Busca Local
//        endTimer();
//        printElapsedTime();
    }

    public void solution2(String fileName) {
        readGraphData(fileName);
        System.out.println("Executando Algoritmo da Inserção mais barata...");

        startTimer();
        // TODO: Algoritmo da Inserção mais barata
        endTimer();
        printElapsedTime();

//        System.out.println("Executando Busca Local...");
//        startTimer();
        // TODO (OPT): Busca Local
//        endTimer();
//        printElapsedTime();
    }

    public void solution3(String fileName) {
        readGraphData(fileName);
        GraspSolver graspSolver = new GraspSolver(graph, "1");
        graspSolver.useQualityRestriction();
        graspSolver.setAlpha(0.3);
        System.out.println("Executando Algoritmo GRASP...");

        List<String> currentSolution;
        List<String> bestSolution = new ArrayList<>();
        Double bestCost = Double.MAX_VALUE / 2;
        Double currentCost;
        for (int i = 0; i < EXECUTIONS_AMOUNT; i++) {
            startTimer();
            currentSolution = graspSolver.solve();
            currentCost = graspSolver.calculateSolutionCost(currentSolution);

            if (currentCost < bestCost) {
                bestCost = currentCost;
                bestSolution = currentSolution;
            }
            endTimer();
            times.add(endTime - startTime);
        }
        printAverageElapsedTime();
        System.out.println("Melhor custo GRASP: " + bestCost);
        System.out.println("Melhor Solução GRASP: " + bestSolution);

//        System.out.println("Executando Busca Local...");
//        startTimer();
//        // TODO: Busca Local
//        endTimer();
//        printElapsedTime();
    }

    private void readGraphData(String fileName) {
        costGraphFileReader.setFileName(fileName);
        graph = new AdjacencyMatrixGraph();
        costGraphFileReader.read(graph);
    }

    private void startTimer() {
        startTime = System.nanoTime();
    }

    private void endTimer() {
        endTime = System.nanoTime();
    }

    private void printElapsedTime() {
        long elapsedTime = endTime - startTime;
        System.out.println("Tempo percorrido: " + elapsedTime + " nanosegundos");
    }

    private void printAverageElapsedTime() {
        OptionalDouble average = times.stream().mapToLong(Long::longValue).average();
        if (average.isPresent()) {
            System.out.println("Tempo médio: " + average.getAsDouble() + " nanosegundos");
        } else {
            System.out.println("Tempo médio: Não há valores para calcular a média");
        }
    }

    public void setGraph(AdjacencyMatrixGraph graph) {
        this.graph = graph;
    }
}
