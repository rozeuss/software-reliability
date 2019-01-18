package sr.optimization.problem;

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

    public Map<Path, Double> getPathProbabilities() {
        return pathProbabilities;
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

    private void calculatePathsProbabilities() {
        Map<Vertex, Boolean> vertexVisited = new HashMap<>();
        graph.getVertices().forEach(v -> vertexVisited.put(v, false));
        List<Vertex> paths = new ArrayList<>();
        paths.add(graph.getFirstVertex());
        findPathsProbabilities(graph.getFirstVertex(), graph.getLastVertex(), vertexVisited, paths, new ArrayList<>());
    }

    private void findPathsProbabilities(Vertex source, Vertex destination, Map<Vertex, Boolean> vertexVisited,
                                        List<Vertex> localPaths, List<Edge> localEdges) {
        vertexVisited.put(source, true);
        if (source.equals(destination)) {
            Double cost = localEdges.stream().map(Edge::getWeight).reduce(1.0, (e1, e2) -> e1 * e2);
            pathProbabilities.put(new Path(new ArrayList<>(localPaths), new ArrayList<>(localEdges)), cost);
            System.out.println(localPaths.stream().map(Vertex::getId).collect(toList()));
            vertexVisited.put(source, false);
            return;
        }
        for (Edge edge : graph.getEdgesIncidentToVertex(source)) {
            Vertex dest = edge.getDestination();
            if (!vertexVisited.get(dest)) {
                localPaths.add(dest);
                localEdges.add(edge);
                findPathsProbabilities(dest, destination, vertexVisited, localPaths, localEdges);
                localPaths.remove(dest);
                localEdges.remove(edge);
            }
        }
        vertexVisited.put(source, false);
    }

    public Solution solve(Parameters params) {
        calculatePathsProbabilities();
        return compute(params.getMaxCost(), params.getMinReliability());
    }

    private Solution compute(Double maxCost, Double minReliability) {
        double reliability = calculateStructureReliability();
        double cost = calculateStructureCost();
        while (cost > maxCost || reliability < minReliability) {
            searchSolution();
            reliability = calculateStructureReliability();
            cost = calculateStructureCost();
        }
        return new Solution(reliability, cost);
    }

    private void searchSolution() {
        getMostExpensiveVertices().forEach(node -> node.setReliability(Opt.calculate()));
    }

    private List<Vertex> getMostExpensiveVertices() {
        return graph.getVertices().stream().sorted(Comparator.comparing(Vertex::getCost).reversed())
                .limit(3).collect(toList());
    }

    static class Opt {
        private static final Random rand = new Random();
        private static final double rangeMin = 0.985;
        private static final double rangeMax = 1.0;

        static double calculate() {
            return rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        }
    }

}
