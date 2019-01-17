package sr.algorithm;

import sr.util.FileUtils;

import java.util.List;

public class OptimizationParams {
    private final Double maxCost;
    private final Double minReliability;

    private OptimizationParams(Double maxCost, Double minReliability) {
        this.maxCost = maxCost;
        this.minReliability = minReliability;
    }

    public static OptimizationParams createOptimizationParams(String filename) {
        List<String> params = FileUtils.readLines(filename);
        return new OptimizationParams(Double.parseDouble(params.get(0)), Double.parseDouble(params.get(1)));
    }

    public Double getMaxCost() {
        return maxCost;
    }

    public Double getMinReliability() {
        return minReliability;
    }
}
