package sr.optimization.algorithm;

import sr.optimization.graph.Edge;
import sr.optimization.graph.Graph;
import sr.optimization.graph.Path;
import sr.optimization.graph.Vertex;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class StructureProblem {

    private Map<Path, Double> pathProbabilities;
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
                .map(e -> calculatePathReliability(e.getKey().getVertices(), e.getValue()))
                .reduce(0.0, Double::sum);
    }

    private double calculateStructureCost() {
        return graph.getVertices().stream()
                .peek(vertex -> vertex.setCost(calculateVertexCost(vertex)))
                .map(Vertex::getCost)
                .reduce(0.0, Double::sum);
    }

    private Map<Path, Double> calculatePathsProbabilities() {
        Map<Vertex, Boolean> isVisited = new HashMap<>();
        graph.getVertices().forEach(v -> isVisited.put(v, false));
        ArrayList<Vertex> pathList = new ArrayList<>();
        pathList.add(graph.getFirstVertex());
        printAllPathsUtil(graph.getFirstVertex(), graph.getLastVertex(), isVisited, pathList, new ArrayList<>());
        return this.pathProbabilities;
    }

    private void printAllPathsUtil(Vertex u, Vertex d, Map<Vertex, Boolean> isVisited, List<Vertex> localPathList,
                                   List<Edge> localEdges) {
        // Mark the current node
        isVisited.put(u, true);

        if (u.equals(d)) {
            double cost = 1;
            for (Edge localEdge : localEdges) {
                cost = cost * localEdge.getWeight();
            }
            pathProbabilities.put(new Path(new ArrayList<>(localPathList), new ArrayList<>(localEdges)), cost);
            System.out.println(localPathList.stream().map(Vertex::getId).collect(toList()));
            // if match found then no need to traverse more till depth
            isVisited.put(u, false);
            return;
        }
        // Recur for all the vertices
        // adjacent to current vertex
        for (Edge i : graph.getEdgesIncidentToVertex(u)) {
            Vertex dest = i.getDestination();
            if (!isVisited.get(dest)) {
                // store current node
                localPathList.add(dest);
                localEdges.add(i);
                printAllPathsUtil(dest, d, isVisited, localPathList, localEdges);
                // remove current node
                localEdges.remove(i);
                localPathList.remove(dest);
            }
        }
        // Mark the current node
        isVisited.put(u, false);
    }

    public Map<Path, Double> solve(Parameters params) {
        calculatePathsProbabilities();
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
        }).limit(3).collect(toList());
    }

}
