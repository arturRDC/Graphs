package br.ufrn.imd.utils;

import br.ufrn.imd.representations.AdjacencyMatrixDigraph;

import java.util.*;

public class ChuLiuEdmondsAlgorithm {
    private Map<String, List<String>> cycleMap = new HashMap<>();
    private Map<String, String[]> enteringEdges = new HashMap<>(); // Armazena {pseudoNode -> [source, destination]}

    private Map<String, List<String[]>> cycleEdgesMap = new HashMap<>();
    public List<String[]> findMinimumSpanningArborescence(AdjacencyMatrixDigraph graph, String root) {
        int rootIndex = graph.getVertices().indexOf(root);
        if (rootIndex == -1) {
            throw new IllegalArgumentException("Raiz não encontrada no grafo.");
        }

        List<String[]> selectedEdges = new ArrayList<>();

        // Passo 1: Selecionar as arestas de menor custo para cada nó (exceto raiz)
        for (String v : graph.getVertices()) {
            if (v.equals(root)) continue; // Ignorar a raiz

            String minEdgeSource = null;
            int minEdgeCost = Integer.MAX_VALUE;

            for (String u : graph.getVertices()) {
                Integer weight = graph.getWeight(u, v);
                if (weight != null && weight > 0 && weight < minEdgeCost) {
                    minEdgeCost = weight;
                    minEdgeSource = u;
                }
            }

            if (minEdgeSource != null) {
                selectedEdges.add(new String[]{minEdgeSource, v});
            }
        }

        // Passo 2: Detectar e tratar ciclos
        while (hasCycle(selectedEdges, graph)) {
            // Encontrar o ciclo
            List<String[]> cycleEdges = findCycle(selectedEdges, graph);

            // Contrair o ciclo em um pseudo-nó
            String pseudoNode = contractCycle(graph, cycleEdges);

            // Ajustar os custos das arestas para o pseudo-nó
            adjustCostsForPseudoNode(graph, pseudoNode, cycleEdges);

            // Recalcular as arestas de menor custo
            selectedEdges = new ArrayList<>();
            for (String v : graph.getVertices()) {
                if (v.equals(root)) continue;

                String minEdgeSource = null;
                int minEdgeCost = Integer.MAX_VALUE;

                for (String u : graph.getVertices()) {
                    Integer weight = graph.getWeight(u, v);
                    if (weight != null && weight > 0 && weight < minEdgeCost) {
                        minEdgeCost = weight;
                        minEdgeSource = u;
                    }
                }

                if (minEdgeSource != null) {
                    selectedEdges.add(new String[]{minEdgeSource, v});
                }
            }
        }

        return expandCycles(selectedEdges);
    }

    private boolean hasCycle(List<String[]> selectedEdges, AdjacencyMatrixDigraph graph) {
        Set<String> visited = new HashSet<>();
        Set<String> stack = new HashSet<>();

        for (String[] edge : selectedEdges) {
            if (detectCycle(edge[0], selectedEdges, visited, stack)) {
                return true;
            }
        }

        return false;
    }

    private boolean detectCycle(String node, List<String[]> edges, Set<String> visited, Set<String> stack) {
        if (stack.contains(node)) {
            return true;
        }

        if (visited.contains(node)) {
            return false;
        }

        visited.add(node);
        stack.add(node);

        for (String[] edge : edges) {
            if (edge[0].equals(node)) {
                if (detectCycle(edge[1], edges, visited, stack)) {
                    return true;
                }
            }
        }

        stack.remove(node);
        return false;
    }

    private List<String[]> findCycle(List<String[]> selectedEdges, AdjacencyMatrixDigraph graph) {
        Map<String, String> parentMap = new HashMap<>();
        for (String[] edge : selectedEdges) {
            parentMap.put(edge[0], edge[1]);
        }

        Set<String> visited = new HashSet<>();
        List<String> cycle = new ArrayList<>();

        for (String node : parentMap.keySet()) {
            if (!visited.contains(node)) {
                String current = node;
                while (current != null && !visited.contains(current)) {
                    visited.add(current);
                    cycle.add(current);
                    current = parentMap.get(current);
                }

                if (current != null && cycle.contains(current)) {
                    int cycleStart = cycle.indexOf(current);
                    List<String[]> cycleEdges = new ArrayList<>();
                    for (int i = cycleStart; i < cycle.size() - 1; i++) {
                        cycleEdges.add(new String[]{cycle.get(i), cycle.get(i + 1)});
                    }
                    cycleEdges.add(new String[]{cycle.get(cycle.size() - 1), current});
                    return cycleEdges;
                }
            }
            cycle.clear();
        }

        return new ArrayList<>();
    }

    private String contractCycle(AdjacencyMatrixDigraph graph, List<String[]> cycleEdges) {
        List<String> cycleNodes = new ArrayList<>();
        for (String[] edge : cycleEdges) {
            if (!cycleNodes.contains(edge[0])) {
                cycleNodes.add(edge[0]);
            }
            if (!cycleNodes.contains(edge[1])) {
                cycleNodes.add(edge[1]);
            }
        }

        String pseudoNode = "K" + cycleNodes.hashCode();
        graph.addVertex(pseudoNode);

        cycleMap.put(pseudoNode, cycleNodes);

        // Calcular o menor custo de entrada no ciclo
        int minCycleCost = Integer.MAX_VALUE;
        String bestSource = null;
        String bestDestination = null;

        for (String node : cycleNodes) {
            for (String other : graph.getVertices()) {
                if (!cycleNodes.contains(other)) {
                    Integer cost = graph.getWeight(other, node);
                    if (cost != 0 && cost < minCycleCost) {
                        minCycleCost = cost;
                        bestSource = other;
                        bestDestination = node;
                    }
                }
            }
        }

        // Modificar os custos das arestas que entram no ciclo
        Map<String, Integer> adjustedCosts = new HashMap<>();
        for (String other : graph.getVertices()) {
            if (!cycleNodes.contains(other)) {
                for (String node : cycleNodes) {
                    Integer costToNode = graph.getWeight(other, node);
                    if (costToNode != 0) {
                        Integer internalCost = graph.getWeight(getCycleEnteringNode(node, cycleEdges), node);
                        if (internalCost != null) {
                            int adjustedCost = costToNode - internalCost;
                            int currentCost = adjustedCosts.getOrDefault(other, Integer.MAX_VALUE);
                            if (adjustedCost < currentCost) {
                                adjustedCosts.put(other, adjustedCost);
                                // Registrar o menor caminho atualizado
                                if (adjustedCost < minCycleCost) {
                                    minCycleCost = adjustedCost;
                                    bestSource = other;
                                    bestDestination = node;
                                }
                            }
                        }
                    }
                }
            }
        }

        // Atualizar o grafo com os menores custos
        for (Map.Entry<String, Integer> entry : adjustedCosts.entrySet()) {
            graph.setWeight(entry.getKey(), pseudoNode, entry.getValue());
        }

        // Registrar o menor caminho no mapa
        if (bestSource != null && bestDestination != null) {
            enteringEdges.put(pseudoNode, new String[]{bestSource, bestDestination});

            // Romper o ciclo: remover todas as arestas que chegam no nó destino dentro do ciclo
            List<String[]> modifiedCycleEdges = new ArrayList<>();
            for (String[] edge : cycleEdges) {
                if (edge[1].equals(bestDestination)) {
                    graph.setWeight(edge[0], edge[1], 0); // Remove aresta ao definir peso como 0
                } else {
                    modifiedCycleEdges.add(edge); // Armazena as arestas restantes no ciclo
                }
            }

            // Armazenar as arestas internas restantes após romper o ciclo
            cycleEdgesMap.put(pseudoNode, modifiedCycleEdges);
        }


        // Remover os nós do ciclo do grafo
        for (String node : cycleNodes) {
            graph.removeVertex(node);
        }

        return pseudoNode;
    }

    private String getCycleEnteringNode(String node, List<String[]> cycleEdges) {
        for (String[] edge : cycleEdges) {
            if (edge[1].equals(node)) {
                return edge[0];
            }
        }
        return null;
    }

    private List<String[]> expandCycles(List<String[]> selectedEdges) {
        List<String[]> expandedEdges = new ArrayList<>();
        for (String[] edge : selectedEdges) {
            String destination = edge[1];

            // Se o destino for um pseudo-nó, expanda o ciclo
            if (cycleMap.containsKey(destination)) {

                // Adicionar o menor caminho armazenado
                String[] enteringEdge = enteringEdges.get(destination);
                if (enteringEdge != null) {
                    expandedEdges.add(new String[]{enteringEdge[0], enteringEdge[1]});
                }

                // Reconstruir as arestas internas do ciclo usando as informações armazenadas
                List<String[]> internalCycleEdges = cycleEdgesMap.get(destination);
                if (internalCycleEdges != null) {
                    expandedEdges.addAll(internalCycleEdges);
                }
            } else {
                expandedEdges.add(edge); // Aresta normal
            }
        }
        return expandedEdges;
    }

    private void adjustCostsForPseudoNode(AdjacencyMatrixDigraph graph, String pseudoNode, List<String[]> cycleEdges) {
        for (String[] edge : cycleEdges) {
            // Verificar se os vértices do ciclo ainda existem no grafo
            if (graph.getVertices().contains(edge[0]) && graph.getVertices().contains(edge[1])) {
                Integer originalWeight = graph.getWeight(edge[0], edge[1]);
                if (originalWeight != null) {
                    graph.setWeight(edge[0], pseudoNode, originalWeight);
                }
            }
        }
    }
}
