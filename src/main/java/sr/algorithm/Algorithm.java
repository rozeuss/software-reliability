package sr.algorithm;

import sr.graph.Edge;
import sr.graph.Graph;
import sr.graph.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class Algorithm {

    private Map<List<Vertex>, Double> pathProbabilities;
    private Graph graph;

    public Algorithm(Graph graph) {
        this.pathProbabilities = new HashMap<>();
        this.graph = graph;
    }

    public Map<List<Vertex>, Double> getPathProbabilities() {
        return pathProbabilities.isEmpty() ? findPathProbabilities() : pathProbabilities;
    }

    public Map<List<Vertex>, Double> findPathProbabilities() {
        List<Vertex> nodeList = new ArrayList<>();
        Map<List<Vertex>, Double> pathProbabilites = new HashMap<>();
        double currentProbability = 1;
        pathProbabilites.put(nodeList, currentProbability);
        Vertex startNode = graph.getFirstVertex();
        Vertex lastNode = graph.getLastVertex();
        multiplyProbabilityThroughNextNode(startNode, nodeList, pathProbabilites, currentProbability);
        pathProbabilities = filterProperPaths(pathProbabilites, lastNode);
        return pathProbabilities;
    }

    private Map<List<Vertex>, Double> filterProperPaths(Map<List<Vertex>, Double> pathProbabilites, Vertex endNode) {
        return pathProbabilites.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(endNode))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void multiplyProbabilityThroughNextNode(Vertex node,
                                                    List<Vertex> nodeList,
                                                    Map<List<Vertex>, Double> pathProbabilites,
                                                    double currentProbability) {
        //add new node to list
        double probability = pathProbabilites.get(nodeList);
        nodeList.add(node);
        double newProbability = probability * currentProbability;
        // update probability
        pathProbabilites.put(nodeList, newProbability);

        //go through next vertex
        Set<Edge> edges = getEdgesRelatedWithNode(node);
        if (edges == null)
            return;
        edges.forEach(edge -> {
            List<Vertex> newList = new ArrayList<>();
            newList.addAll(nodeList);
            pathProbabilites.put(newList, newProbability);
            multiplyProbabilityThroughNextNode(edge.getDestination(), newList, pathProbabilites, edge.getWeight());
        });
    }

    private Set<Edge> getEdgesRelatedWithNode(Vertex node) {
        return graph.getEdgesIncidentToVertex(node);
    }

    public Map<List<Vertex>, Double> run(OptimizationParams params) {
        findPathProbabilities();
        setAllReliabilityToMax();
        runSingle(params.getMaxCost(), params.getMinReliability());
        return pathProbabilities;
    }

    public void runSingle(Double maxCost, Double minReliability) {
        Double reliability = calculateReliabilityForStructure();
        Double cost = calculateCostForStructure();
        System.out.printf("Reliability: %.5f Cost: %.3f %n", reliability, cost);
        System.out.println();

        while (cost > maxCost || reliability < minReliability) {
            randomReliabilities();
            reliability = calculateReliabilityForStructure();
            cost = calculateCostForStructure();
        }

        System.out.printf("Found reliability: %.5f and Cost: %.3f that fits requirements", reliability, cost);
        System.out.println();
    }

    private void randomReliabilities() {
        double rangeMin = 0.985;
        double rangeMax = 1.0;
        Random r = new Random();

        findTheMostExpensiveNodes().forEach(node -> {
            double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            node.setReliability(randomValue);
        });
    }

    private List<Vertex> findTheMostExpensiveNodes() {
        return graph.getVertices().stream().sorted((node1, node2) -> {
            int answer;
            if (node1.getCost() == node2.getCost()) {
                answer = 0;
            } else if (node1.getCost() < node2.getCost()) {
                answer = 1;
            } else {
                answer = -1;
            }
            return answer;
        }).limit(3).collect(Collectors.toList());
    }

    private Double calculateCostForStructure() {
        final Double[] cost = {0.0};
        graph.getVertices().forEach(node -> {
            cost[0] = cost[0] + calculateCostForSingleNode(node);
        });
        return cost[0];
    }

    private Double calculateCostForSingleNode(Vertex node) {
        Double cost;
        cost = node.getS() + node.getAlpha() * Math.pow(Math.E, node.getBeta() * node.getReliability());
        node.setCost(cost);
        return cost;
    }

    private Double calculateReliabilityForStructure() {
        Map<List<Vertex>, Double> properPathProbabilites = getPathProbabilities();
        Double reliability = 0.0;
        for (Map.Entry<List<Vertex>, Double> entry : properPathProbabilites.entrySet()) {
            reliability = reliability + calculateReliabilityForSinglePath(entry.getKey(), entry.getValue());
        }
        return reliability;
    }

    private Double calculateReliabilityForSinglePath(List<Vertex> nodes, Double probability) {
        final double[] reliability = {1};
        nodes.forEach(node -> reliability[0] = reliability[0] * node.getReliability());
        return reliability[0] * probability;
    }

    private void setAllReliabilityToMax() {
        graph.getVertices().forEach(node -> node.setReliability(1.0));
    }
}
