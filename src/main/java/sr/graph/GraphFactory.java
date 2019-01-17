package sr.graph;

import sr.util.FileUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphFactory {
    private static final char FIRST_CHAR = 'A';
    private static GraphFactory instance;

    private GraphFactory() {
    }

    public static GraphFactory getInstance() {
        if (instance == null) {
            instance = new GraphFactory();
        }
        return instance;
    }

    private static Graph fromTransitionMatrix(double[][] matrix) {
        final Graph graph = new Graph();
        IntStream.range(0, matrix.length).forEach(x -> graph.addVertex(String.valueOf((char) (FIRST_CHAR + x))));
        IntStream.range(0, matrix.length).forEach(i ->
                IntStream.range(0, matrix.length).forEach(j -> {
                            if (matrix[i][j] != 0.0) {
                                graph.addEdge(String.valueOf((char) (FIRST_CHAR + i)),
                                        String.valueOf((char) (FIRST_CHAR + j)), matrix[i][j]);
                            }
                        }
                ));
        return graph;
    }

    private static Graph setupGraph(double[][] matrix, List<Double> alphas, List<Double> betas, List<Double> costs) {
        Graph graph = fromTransitionMatrix(matrix);
        graph.getVertices().forEach(vertex -> {
            vertex.setAlpha(alphas.get(vertex.getId().charAt(0) - FIRST_CHAR));
            vertex.setBeta(betas.get(vertex.getId().charAt(0) - FIRST_CHAR));
            vertex.setS(costs.get(vertex.getId().charAt(0) - FIRST_CHAR));
        });
        return graph;
    }

    public Graph createGraph(String transitionMatrix, String coefficient, String cost) {
        double[][] coefficients = FileUtils.readDoubleArray(coefficient);
        double[][] costs = FileUtils.readDoubleArray(cost);
        return setupGraph(FileUtils.readDoubleArray(transitionMatrix),
                Arrays.stream(coefficients[0]).boxed().collect(Collectors.toList()),
                Arrays.stream(coefficients[1]).boxed().collect(Collectors.toList()),
                Arrays.stream(costs[0]).boxed().collect(Collectors.toList()));
    }
}
