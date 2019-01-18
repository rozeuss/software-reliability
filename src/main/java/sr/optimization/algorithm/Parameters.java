package sr.optimization.algorithm;

import sr.optimization.util.FileUtils;

import java.util.List;

public class Parameters {
    private final Double maxCost;
    private final Double minReliability;

    private Parameters(Double maxCost, Double minReliability) {
        this.maxCost = maxCost;
        this.minReliability = minReliability;
    }

    public static Parameters readParams(String filename) {
        List<String> params = FileUtils.readLines(filename);
        return new Parameters(Double.parseDouble(params.get(0)), Double.parseDouble(params.get(1)));
    }

    Double getMaxCost() {
        return maxCost;
    }

    Double getMinReliability() {
        return minReliability;
    }
}
