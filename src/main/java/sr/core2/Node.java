package sr.core2;

import java.util.Objects;

public class Node { //TODO Vertex
    private final String name;
    private double alpha;
    private double beta;
    private double s;
    private double reliability;
    private double cost;

    public Node(String name) {
        this.name = name;
    }

    public String getLabel() {
        return name;
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

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Double.compare(node.alpha, alpha) == 0 &&
                Double.compare(node.beta, beta) == 0 &&
                Double.compare(node.s, s) == 0 &&
                Double.compare(node.reliability, reliability) == 0 &&
                Double.compare(node.cost, cost) == 0 &&
                Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alpha, beta, s, reliability, cost);
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", alpha=" + alpha +
                ", beta=" + beta +
                ", s=" + s +
                ", reliability=" + reliability +
                ", cost=" + cost +
                '}';
    }
}