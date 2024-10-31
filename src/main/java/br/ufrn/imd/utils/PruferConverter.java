package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.*;

public class PruferConverter {
    private final AdjacencyMatrixGraph tree;
    private Comparator<String> comparator = String::compareTo;
    private final List<String> vertices;

    public PruferConverter(AdjacencyMatrixGraph tree) {
        this.tree = tree;
        this.vertices = new ArrayList<>(tree.getVertices());

        if (canParseInteger(vertices.get(0))) {
            comparator = Comparator.comparingInt(Integer::parseInt);
        }
    }

    private static boolean canParseInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public List<String> encode() {
        List<String> pruferSequence = new ArrayList<>();
        int n = vertices.size();

        while (n > 2) {
            String leaf = findSmallestLeaf(vertices);

            String neighbor = tree.findAdjacentVertices(leaf).get(0);

            pruferSequence.add(neighbor);

            tree.removeVertex(leaf);
            vertices.remove(leaf);
            n--;
        }

        return pruferSequence;
    }

    private String findSmallestLeaf(List<String> vertices) {
        return vertices.stream()
                .filter(v -> tree.findAdjacentVertices(v).size() == 1)
                .min(comparator)
                .get();
    }

    public AdjacencyMatrixGraph decode(List<String> code, List<String> vertices) {
        if (code.size() != vertices.size() - 2) {
            throw new IllegalArgumentException("Invalid sequence length for given number of vertices");
        }

        AdjacencyMatrixGraph tree = new AdjacencyMatrixGraph();
        vertices.forEach(tree::addVertex);

        Map<String, Integer> degree = new HashMap<>();
        vertices.forEach(v -> degree.put(v, 1));
        code.forEach(v -> degree.merge(v, 1, Integer::sum));

        while (!code.isEmpty()) {
            String leaf = vertices.stream()
                    .filter(v -> degree.get(v) == 1)
                    .min(comparator)
                    .get();

            String neighbor = code.remove(0);
            tree.addEdge(leaf, neighbor);

            degree.put(leaf, degree.get(leaf) - 1);
            degree.put(neighbor, degree.get(neighbor) - 1);
        }

        List<String> lastTwo = vertices.stream()
                .filter(v -> degree.get(v) == 1)
                .limit(2)
                .toList();

        tree.addEdge(lastTwo.get(0), lastTwo.get(1));

        return tree;
    }
}