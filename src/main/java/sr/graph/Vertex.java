package sr.graph;

import java.util.Objects;

public class Vertex {
    private final String id;
    private double alpha;
    private double beta;
    private double s;
    private double reliability;
    private double cost;

    public Vertex(String id) {
        this.id = id;
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

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }
}