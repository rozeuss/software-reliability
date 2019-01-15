package sr.core;

import java.util.Objects;

// TODO DONE
public class Node {
    private String uniqueLabel;
    private double alpha;
    private double beta;
    private double s;
    private double reliability;
    private double cost;

    public Node(String uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node)) return false;

        Node _obj = (Node) obj;
        return this.uniqueLabel.equals(_obj.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueLabel);
    }

    public String getLabel() {
        return uniqueLabel;
    }

    public void setLabel(String uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
    }

    public String getUniqueLabel() {
        return uniqueLabel;
    }

    public void setUniqueLabel(String uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
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

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }
}