package sr.algorithm;



import sr.core.Edge;
import sr.core.Graph;
import sr.core.Node;

import java.util.*;
import java.util.stream.Collectors;

public class Algorithm {

    private Graph graph;

    Map<List<Node>,Double> properPathProbabilites;

    public Algorithm(Graph graph) {
        this.graph = graph;
        this.properPathProbabilites = new HashMap<>();
    }

    public Map<List<Node>, Double> getProperPathProbabilites() {
        return properPathProbabilites.isEmpty() ? findPathProbabilities() : properPathProbabilites;
    }

    public Map<List<Node>,Double> findPathProbabilities() {
        List<Node> nodeList = new ArrayList<>();
        Map<List<Node>,Double> pathProbabilites = new HashMap<>();
        double currentProbability = 1;
        pathProbabilites.put(nodeList, currentProbability);
        Node startNode = graph.getStartNode();
        Node lastNode = graph.getLastNode();
        multiplyProbabilityThroughNextNode(startNode, nodeList, pathProbabilites, currentProbability);
        properPathProbabilites = filterProperPaths(pathProbabilites, lastNode);
        return properPathProbabilites;
    }

    private Map<List<Node>,Double> filterProperPaths(Map<List<Node>,Double> pathProbabilites, Node endNode) {
       return pathProbabilites.entrySet()
               .stream()
               .filter(entry -> entry.getKey().contains(endNode))
               .collect(Collectors.toMap(entry -> entry.getKey(),
                       entry -> entry.getValue()));
    }

    private void multiplyProbabilityThroughNextNode(Node node,
                                                    List<Node> nodeList,
                                                    Map<List<Node>,Double> pathProbabilites,
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
        edges.stream().forEach( edge -> {
            List<Node> newList = new ArrayList<>();
            newList.addAll(nodeList);
            pathProbabilites.put(newList, newProbability);
            multiplyProbabilityThroughNextNode(edge.getV2(), newList, pathProbabilites, edge.getWeight());
        });
    }

    private Set<Edge> getEdgesRelatedWithNode(Node node) {
        return graph.getAdjEdgesNew(node);
    }

    public void printPathsWithProbabilities() {
        System.out.println("Found paths with probabilities: ");
        properPathProbabilites.entrySet().stream().forEach(entry -> {
            entry.getKey().stream().forEach(key -> System.out.print(key.getLabel() + ", "));
            System.out.println(entry.getValue().toString());
        });
    }

    public void run(Double maxCost, Double minReliability) {
        findPathProbabilities();
        setAllReliabilityToMax();
        runSingle(maxCost, minReliability);
    }

    public void runSingle(Double maxCost, Double minReliability) {
        Double reliability = calculateReliabilityForStructure();
        Double cost = calculateCostForStructure();

        while(cost > maxCost || reliability < minReliability) {
            randomReliabilities();
            reliability = calculateReliabilityForStructure();
            cost = calculateCostForStructure();
            System.out.printf("Reliability: %.5f . Cost: %.3f %n. Continue searching..", reliability, cost);
        }

        System.out.printf("Found reliability: %.5f and Cost: %.3f that fits requirements", reliability, cost);
        System.out.println();
    }

    private void randomReliabilities() {
        double rangeMin = 0.985;
        double rangeMax = 1.0;
        Random r = new Random();

        findTheMostExpensiveNodes().stream().forEach(node -> {
            double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            node.setReliability(randomValue);
        });
    }

    private List<Node> findTheMostExpensiveNodes() {
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
        graph.getVertices().stream().forEach(node -> {
            cost[0] = cost[0] + calculateCostForSingleNode(node);
        });
        return cost[0];
    }

    private Double calculateCostForSingleNode(Node node) {
        Double cost;
        cost = node.getS() + node.getAlpha() * Math.pow(Math.E, node.getBeta() * node.getReliability());
        node.setCost(cost);
        return cost;
    }

    private Double calculateReliabilityForStructure() {
        Map<List<Node>, Double> properPathProbabilites = getProperPathProbabilites();
        Double reliability = 0.0;
        for (Map.Entry<List<Node>, Double> entry : properPathProbabilites.entrySet()) {
            reliability = reliability + calculateReliabilityForSinglePath(entry.getKey(), entry.getValue());
        }
        return reliability;
    }

    private Double calculateReliabilityForSinglePath(List<Node> nodes, Double probability) {
        final double[] reliability = {1};
        nodes.stream().forEach(node -> {
            reliability[0] = reliability[0] * node.getReliability();
        });
        return reliability[0] * probability;
    }

    private void setAllReliabilityToMax() {
        graph.getVertices().stream().forEach(node -> node.setReliability(1.0));
    }
}
