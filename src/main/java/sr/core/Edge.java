package sr.core;

import java.util.Objects;

//TODO DONE
public class Edge {
    private static final double DEFAULT_WEIGHT = 1.0;

    private Node v1, v2;
    private double weight;

    public Edge(Node v1, Node v2) {
        this(v1, v2, DEFAULT_WEIGHT);
    }

    public Edge(Node v1, Node v2, double weight) {
        super();
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public Node getV1() {
        return v1;
    }

    public Node getV2() {
        return v2;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge)) return false;

        Edge _obj = (Edge) obj;
        return this.v1.equals(_obj.getV1()) && v2.equals(_obj.getV2()) && this.weight == _obj.getWeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2, weight);
    }
}