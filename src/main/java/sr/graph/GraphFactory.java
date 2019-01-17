package sr.graph;


import sr.util.FileUtils;

import java.util.List;
import java.util.stream.IntStream;

public class GraphFactory {
    private final static char firstChar = 'A';
    private static GraphFactory INSTANCE;

    private GraphFactory() {
    }

    public static GraphFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GraphFactory();
        }
        return INSTANCE;
    }

    private static Graph fromTransitionMatrix(double[][] matrix) {
        final Graph graph = new Graph();
        IntStream.range(0, matrix.length).forEach(i -> {
            graph.addNode(String.valueOf((char) (GraphFactory.firstChar + i)));
            IntStream.range(0, matrix.length).forEach(j ->
                    graph.addEdge(String.valueOf((char) (GraphFactory.firstChar + i)),
                            String.valueOf((char) (GraphFactory.firstChar + j)), matrix[i][j]));
        });
        return graph;
    }

    public Graph createGraph(String transitionMatrix, String alpha, String beta, String cost) {
        return setupGraph(FileUtils.readDoubleArray(transitionMatrix), FileUtils.readLines(alpha),
                FileUtils.readLines(beta), FileUtils.readLines(cost));
    }

    private static Graph setupGraph(double[][] matrix, List<String> alphas, List<String> betas, List<String> costs) {
        Graph graph = fromTransitionMatrix(matrix);
        graph.getVertices().forEach(vertex -> {
            vertex.setAlpha(Double.valueOf(alphas.get(vertex.getId().charAt(0) - firstChar)));
            vertex.setBeta(Double.valueOf(betas.get(vertex.getId().charAt(0) - firstChar)));
            vertex.setS(Double.valueOf(costs.get(vertex.getId().charAt(0) - firstChar)));
        });
        return graph;
    }
}
