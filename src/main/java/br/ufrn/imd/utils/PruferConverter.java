package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixGraph;

import java.util.*;

public class PruferConverter {
    private final AdjacencyMatrixGraph tree;
    private Comparator<String> comparator = String::compareTo;
    private final List<String> vertices;

    // Construtor que inicializa o grafo e lista de vértices
    public PruferConverter(AdjacencyMatrixGraph tree) {
        this.tree = tree;
        this.vertices = new ArrayList<>(tree.getVertices());

        // Configura o comparador para ordenar numericamente caso os vértices sejam inteiros
        if (canParseInteger(vertices.get(0))) {
            comparator = Comparator.comparingInt(Integer::parseInt);
        }
    }

    // Verifica se uma string pode ser convertida em um inteiro
    private static boolean canParseInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Gera a sequência de Prüfer a partir da árvore na representação de matriz de adjacências
    public List<String> encode() {
        List<String> pruferSequence = new ArrayList<>();
        int n = vertices.size();

        // Enquanto o número de vértices for maior que 2
        while (n > 2) {
            // Encontra o menor vértice folha
            String leaf = findSmallestLeaf(vertices);

            // Encontra o vizinho do vértice folha
            String neighbor = tree.findAdjacentVertices(leaf).get(0);

            // Adiciona o vizinho na sequência de Prüfer
            pruferSequence.add(neighbor);

            // Remove o vértice folha do grafo e da lista de vértices
            tree.removeVertex(leaf);
            vertices.remove(leaf);
            n--;
        }

        return pruferSequence;
    }

    // Encontra o menor vértice folha
    private String findSmallestLeaf(List<String> vertices) {
        return vertices.stream()
                // Filtra vértices com apenas um vizinho
                .filter(v -> tree.findAdjacentVertices(v).size() == 1)
                // Retorna o menor vértice conforme o comparador
                .min(comparator)
                .orElseThrow(()-> new IllegalArgumentException("Árvore inválida."));
    }

    // Decodifica um código de Prüfer e reconstroi a árvore em uma matriz de adjacências
    public AdjacencyMatrixGraph decode(List<String> code, List<String> vertices) {
        // Verifica se o tamanho do código é válido
        if (code.size() != vertices.size() - 2) {
            throw new IllegalArgumentException("Tamanho do código de Prüfer inválido.");
        }

        AdjacencyMatrixGraph tree = new AdjacencyMatrixGraph();
        vertices.forEach(tree::addVertex); // Adiciona todos os vértices da árvore


        Map<String, Integer> degree = new HashMap<>();
        // Inicializa o grau de cada vértice com 1
        vertices.forEach(v -> degree.put(v, 1));
        // Incrementa o grau de cada vértice de acordo com o código de Prüfer
        code.forEach(v -> degree.merge(v, 1, Integer::sum));

        // Gera a árvore adicionando arestas
        while (!code.isEmpty()) {
            // Encontra o menor vértice folha (grau 1)
            String leaf = findSmallestLeaf(vertices);

            // Remove o primeiro vértice do código de prufer e conecta com o vértice folha
            String neighbor = code.remove(0);
            tree.addEdge(leaf, neighbor);

            // Decrementa os graus dos vértices
            degree.put(leaf, degree.get(leaf) - 1);
            degree.put(neighbor, degree.get(neighbor) - 1);
        }

        // Adiciona a última aresta entre os dois vértices restantes com grau 1
        List<String> lastTwo = vertices.stream()
                .filter(v -> degree.get(v) == 1)
                .limit(2)
                .toList();

        tree.addEdge(lastTwo.get(0), lastTwo.get(1));

        return tree;
    }
}
