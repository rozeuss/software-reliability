package sr.graph;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private final TreeSet<Vertex> vertices;
    private final Set<Edge> edges;
    private final Map<Vertex, Set<Edge>> vertexIncidentEdges;

    Graph() {
        vertices = new TreeSet<>(Comparator.comparing(Vertex::getId));
        edges = new HashSet<>();
        vertexIncidentEdges = new HashMap<>();
    }

    void addEdge(String source, String destination, double weight) {
        Edge edge = new Edge(getVertexById(source), getVertexById(destination), weight);
        edges.add(edge);
        vertexIncidentEdges.get(edge.getSource()).add(edge);
        vertexIncidentEdges.get(edge.getDestination()).add(edge);
    }

    private Vertex getVertexById(String id) {
        return vertices.stream().filter(vertex -> vertex.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Vertex not found = " + id));
    }

    public Set<Edge> getIncidentEdgesForVertex(Vertex vertex) {
        return vertexIncidentEdges.get(vertex).stream()
                .filter(e -> e.getSource().equals(vertex)).collect(Collectors.toSet());
    }

    public Vertex getFirstVertex() {
        return vertices.first();
    }

    public Vertex getLastVertex() {
        return vertices.last();
    }

    public Set<Vertex> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }

    public Set<Edge> getEdges() {
        return Collections.unmodifiableSet(edges);
    }

    void addVertex(String id) {
        Vertex vertex = new Vertex(id);
        vertices.add(vertex);
        vertexIncidentEdges.put(vertex, new HashSet<>());
    }

}
