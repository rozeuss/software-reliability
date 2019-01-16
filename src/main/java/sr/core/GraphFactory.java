package sr.core;


import sr.core.Graph;
import sr.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphFactory {

    private final static char firstChar = 'A';

    public static Graph createGraph(String transitionMatrix, String alpha,
                                    String beta, String s, String params) {
        return prepareGraph(FileUtils.readDoubleArray(transitionMatrix),
                FileUtils.readLines(alpha),
                FileUtils.readLines(beta),
                FileUtils.readLines(s),
                FileUtils.readLines(params)
        );
    }

    private static Graph prepareGraph(double[][] doubleMatrix,
                                      List<String> alphaParams,
                                      List<String> betaParams,
                                      List<String> sParams,
                                      List<String> optimizationParams) {
        Graph graph = new Graph();
        int matrixSize = doubleMatrix.length;
        for (int i = 0; i < matrixSize; i++) {
            graph.addNode(String.valueOf((char) (firstChar + i)));
            for (int j = 0; j < matrixSize; j++) {
                graph.addEdge(String.valueOf((char) (firstChar + i)),
                        String.valueOf((char) (firstChar + j)),
                        doubleMatrix[i][j]);
            }
        }
        setAlpha(graph, alphaParams);
        setBeta(graph, betaParams);
        setS(graph, sParams);
        setOptimizationParams(graph, optimizationParams);
        return graph;
    }

    private static void setOptimizationParams(Graph graph, List<String> optimizationParams) {
        graph.setMaxCost(Double.parseDouble(optimizationParams.get(0)));
        graph.setMinReliability(Double.parseDouble(optimizationParams.get(1)));
    }

    private static void setAlpha(Graph graph, List<String> alphaMap) {
        Map<String, Double> alphaParams = new HashMap<>();
        for (int i = 0; i < alphaMap.size(); i++) {
            String label = String.valueOf((char) (firstChar + i));
            Double value = Double.parseDouble(alphaMap.get(i));
            alphaParams.put(label, value);
        }

        graph.getVertices().stream()
                .forEach(verticle -> verticle
                        .setAlpha(alphaParams.get(verticle.getId())));
    }

    private static void setBeta(Graph graph, List<String> betaMap) {
        Map<String, Double> alphaParams = new HashMap<>();
        for (int i = 0; i < betaMap.size(); i++) {
            String label = String.valueOf((char) (firstChar + i));
            Double value = Double.parseDouble(betaMap.get(i));
            alphaParams.put(label, value);
        }

        graph.getVertices().stream()
                .forEach(verticle -> verticle
                        .setBeta(alphaParams.get(verticle.getId())));
    }

    private static void setS(Graph graph, List<String> sParams) {
        Map<String, Double> alphaParams = new HashMap<>();
        for (int i = 0; i < sParams.size(); i++) {
            String label = String.valueOf((char) (firstChar + i));
            Double value = Double.parseDouble(sParams.get(i));
            alphaParams.put(label, value);
        }

        graph.getVertices().stream()
                .forEach(verticle -> verticle
                        .setS(alphaParams.get(verticle.getId())));
    }
}
