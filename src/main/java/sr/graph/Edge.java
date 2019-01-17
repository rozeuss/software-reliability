package sr.graph;

import java.util.Objects;

public class Edge {
    private final Vertex source;
    private final Vertex destination;
    private final double weight;

    public Edge(Vertex source, Vertex destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "source=" + source +
                ", destination=" + destination +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge vertex = (Edge) o;
        return Double.compare(vertex.weight, weight) == 0 &&
                Objects.equals(source, vertex.source) &&
                Objects.equals(destination, vertex.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination, weight);
    }
}