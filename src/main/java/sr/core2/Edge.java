package sr.core2;

import java.util.Objects;

public class Edge  {
//    private final String id;
    private final Node v1; //TODO source
    private final Node v2; //TODO destination
    private final double weight;

    public Edge(Node v1, Node v2, double weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public Node getV2() {
        return v2;
    }
    public Node getV1() {
        return v1;
    }
    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return v1 + " " + v2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.weight, weight) == 0 &&
                Objects.equals(v1, edge.v1) &&
                Objects.equals(v2, edge.v2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2, weight);
    }
}