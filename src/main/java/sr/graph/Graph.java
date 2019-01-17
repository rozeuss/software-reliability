package sr.graph;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Graph {
    private final TreeSet<Vertex> vertices;
    private final Set<Edge> edges;
    private final Map<Vertex, Set<Edge>> adjacencyMap;

    Graph() {
        vertices = new TreeSet<>(Comparator.comparing(Vertex::getId));
        edges = new HashSet<>();
        adjacencyMap = new HashMap<>();
    }

    //TODO
    private void addEdge(Edge e) {
        edges.add(e);
        adjacencyMap.putIfAbsent(e.getSource(), new HashSet<>());
        adjacencyMap.putIfAbsent(e.getDestination(), new HashSet<>());
        adjacencyMap.get(e.getSource()).add(e);
        adjacencyMap.get(e.getDestination()).add(e);
    }

    //TODO
    void addEdge(String source, String destination, double weight) {
        addEdge(new Edge(
            vertices.stream().filter(vertice -> vertice.getId().equals(source)).findFirst().get(),
            vertices.stream().filter(vertice -> vertice.getId().equals(destination)).findFirst().get(),
            weight));
    }

    public Set<Edge> getAdjacentEdges(Vertex vertex) {
        return adjacencyMap.get(vertex).stream().filter(e -> e.getSource().equals(vertex)).collect(Collectors.toSet());
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
        vertices.add(new Vertex(id));
    }

}
