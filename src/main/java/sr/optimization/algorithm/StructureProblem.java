package sr.optimization.algorithm;

import sr.optimization.graph.Edge;
import sr.optimization.graph.Graph;
import sr.optimization.graph.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class StructureProblem {

    private Map<List<Vertex>, Double> pathProbabilities;
    private Graph graph;

    public StructureProblem(Graph graph) {
        this.pathProbabilities = new HashMap<>();
        this.graph = graph;
    }

    private static double calculateVertexCost(Vertex v) {
        return v.getCostConstant() + v.getAlpha() * Math.pow(Math.E, v.getBeta() * v.getReliability());
    }

    private static double calculatePathReliability(List<Vertex> vertices, Double probability) {
        return probability * vertices.stream()
                .map(Vertex::getReliability)
                .reduce(1.0, (v1, v2) -> v1 * v2);
    }

    private double calculateStructureReliability() {
        return pathProbabilities.entrySet().stream()
                .map(e -> calculatePathReliability(e.getKey(), e.getValue()))
                .reduce(0.0, Double::sum);
    }

    private double calculateStructureCost() {
        return graph.getVertices().stream()
                .peek(vertex -> vertex.setCost(calculateVertexCost(vertex)))
                .map(Vertex::getCost)
                .reduce(0.0, Double::sum);
    }

    //TODO
    private Map<List<Vertex>, Double> findPathProbabilities() {
        List<Vertex> vertices = new ArrayList<>();
        Map<List<Vertex>, Double> temporaryPathProbabilities = new HashMap<>();
        double currentProbability = 1;
        temporaryPathProbabilities.put(vertices, currentProbability);
        Vertex startNode = graph.getFirstVertex();
        Vertex lastNode = graph.getLastVertex();
        multiplyProbabilityThroughNextNode(startNode, vertices, temporaryPathProbabilities, currentProbability);
        this.pathProbabilities = filterProperPaths(temporaryPathProbabilities, lastNode);
        return this.pathProbabilities;
    }

    //TODO
    private Map<List<Vertex>, Double> filterProperPaths(Map<List<Vertex>, Double> pathProbabilites, Vertex endNode) {
        return pathProbabilites.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(endNode))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    //TODO
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
        Set<Edge> edges = graph.getEdgesIncidentToVertex(node);
        if (edges == null)
            return;
        edges.forEach(edge -> {
            List<Vertex> newList = new ArrayList<>();
            newList.addAll(nodeList);
            pathProbabilites.put(newList, newProbability);
            multiplyProbabilityThroughNextNode(edge.getDestination(), newList, pathProbabilites, edge.getWeight());
        });
    }

    public Map<List<Vertex>, Double> solve(Parameters params) {
        findPathProbabilities();
        Solution result = compute(params.getMaxCost(), params.getMinReliability());
        return pathProbabilities;
    }

    private Solution compute(Double maxCost, Double minReliability) {
        double reliability = calculateStructureReliability();
        double cost = calculateStructureCost();
        System.out.format("Initial. Reliability: %.5f Cost: %.5f %n", reliability, cost).println();
        while (cost > maxCost || reliability < minReliability) {
            randomReliabilities();
            reliability = calculateStructureReliability();
            cost = calculateStructureCost();
        }
        System.out.format("Solution found. Reliability: %.5f, Cost: %.5f", reliability, cost).println();
        return new Solution(reliability, cost);
    }

    //TODO
    private void randomReliabilities() {
        double rangeMin = 0.985;
        double rangeMax = 1.0;
        Random r = new Random();

        findTheMostExpensiveVertex().forEach(node -> {
            double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            node.setReliability(randomValue);
        });
    }

    //TODO
    private List<Vertex> findTheMostExpensiveVertex() {
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

}
