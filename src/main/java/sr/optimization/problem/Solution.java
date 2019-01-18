package sr.optimization.problem;

public class Solution {
    private double reliability;
    private double cost;

    public Solution(double reliability, double cost) {
        this.reliability = reliability;
        this.cost = cost;
    }

    public double getReliability() {
        return reliability;
    }

    public void setReliability(double reliability) {
        this.reliability = reliability;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
