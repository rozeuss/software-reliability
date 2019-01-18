package sr.optimization.graph;

import java.util.Objects;

public class Vertex {
    private final String id;
    private double alpha;
    private double beta;
    private double costConstant;
    private double reliability = 0.99999999;
    private double cost;

    Vertex(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Vertex{" +
            "id='" + id + '\'' +
            ", alpha=" + alpha +
            ", beta=" + beta +
            ", costConstant=" + costConstant +
            ", reliability=" + reliability +
            ", cost=" + cost +
            '}';
    }

    //TODO danger to modify - used in algorithm
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vertex)) return false;

        Vertex _obj = (Vertex) obj;
        return this.id.equals(_obj.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
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

    public double getCostConstant() {
        return costConstant;
    }

    public void setCostConstant(double costConstant) {
        this.costConstant = costConstant;
    }
}