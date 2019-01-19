package sr.optimization.graph;

import java.util.List;
import java.util.Objects;

public class Path {
    private List<Vertex> vertices;
    private List<Edge> edges;

    public Path(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return Objects.equals(vertices, path.vertices) &&
                Objects.equals(edges, path.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, edges);
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
