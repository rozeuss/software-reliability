package sr.utils;



import sr.core.Graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphManager {

    private ReaderHelper readerHelper;
    private GraphUtils graphUtils;

    public GraphManager(ReaderHelper readerHelper, GraphUtils graphUtils) {
         this.readerHelper = readerHelper;
         this.graphUtils = graphUtils;
    }

    public Graph createGraph(String filePathToGraphMatrix, String filePathToAlpha,
                             String filePathToBeta, String filePathToS, String filePathToOptimizationParams) {
        List<List<Double>> doubleMatrix = readerHelper.readDoublesFromFile(filePathToGraphMatrix);
        List<String> alphas = readerHelper.readLines(filePathToAlpha);
        List<String> betas = readerHelper.readLines(filePathToBeta);
        List<String> s = readerHelper.readLines(filePathToS);
        List<String> optimizationParams = readerHelper.readLines(filePathToOptimizationParams);
        Graph graph = prepareGraph(doubleMatrix, alphas, betas, s, optimizationParams);
        return graph;
    }

    private Graph prepareGraph(List<List<Double>> doubleMatrix,
                               List<String> alphaParams,
                               List<String> betaParams,
                               List<String> sParams,
                               List<String> optimizationParams) {
        Graph graph = new Graph();
        int matrixSize = doubleMatrix.size();
        for (int i = 0; i < matrixSize; i++) {
            graph.addNode(graphUtils.normalizeLabel(i));
            for (int j = 0; j < matrixSize; j++) {
                graph.addEdge(graphUtils.normalizeLabel(i),
                        graphUtils.normalizeLabel(j),
                        doubleMatrix.get(i).get(j));
            }
        }
        setAlpha(graph, alphaParams);
        setBeta(graph, betaParams);
        setS(graph, sParams);
        setOptimizationParams(graph, optimizationParams);
        return graph;
    }

    private void setOptimizationParams(Graph graph, List<String> optimizationParams) {
        graph.setMaxCost(Double.parseDouble(optimizationParams.get(0)));
        graph.setMinReliability(Double.parseDouble(optimizationParams.get(1)));
    }

    private void setAlpha(Graph graph, List<String> alphaMap) {
        Map<String, Double> alphaParams = new HashMap<>();
        for (int i = 0; i < alphaMap.size(); i++) {
            String label = graphUtils.normalizeLabel(i);
            Double value = Double.parseDouble(alphaMap.get(i));
            alphaParams.put(label, value);
        }

        graph.getVertices().stream()
                .forEach(verticle -> verticle
                        .setAlpha(alphaParams.get(verticle.getLabel())));
    }

    private void setBeta(Graph graph, List<String> betaMap) {
        Map<String, Double> alphaParams = new HashMap<>();
        for (int i = 0; i < betaMap.size(); i++) {
            String label = graphUtils.normalizeLabel(i);
            Double value = Double.parseDouble(betaMap.get(i));
            alphaParams.put(label, value);
        }

        graph.getVertices().stream()
                .forEach(verticle -> verticle
                        .setBeta(alphaParams.get(verticle.getLabel())));
    }

    private void setS(Graph graph, List<String> sParams) {
        Map<String, Double> alphaParams = new HashMap<>();
        for (int i = 0; i < sParams.size(); i++) {
            String label = graphUtils.normalizeLabel(i);
            Double value = Double.parseDouble(sParams.get(i));
            alphaParams.put(label, value);
        }

        graph.getVertices().stream()
                .forEach(verticle -> verticle
                        .setS(alphaParams.get(verticle.getLabel())));
    }
}
