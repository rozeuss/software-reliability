package sr.core;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private Set<Vertex> vertices;
    private Set<Edge> edges;
    private Map<Vertex, Set<Edge>> adjList;
    private Double maxCost;
    private Double minReliability;

    public Graph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
        adjList = new HashMap<>();
    }

    public boolean addNode(String label) {
        return vertices.add(new Vertex(label));
    }

    public Vertex addNodeIfNotExist(String label) {
        Vertex newNode = new Vertex(label);
        if (vertices.contains(newNode)) {
            return vertices.stream()
                    .filter(vertice ->
                            vertice.getId()
                                    .equals(label))
                    .findFirst().get();
        } else {
            vertices.add(newNode);
        }
        return newNode;
    }

    public boolean addEdge(Edge e) {
        if (!edges.add(e)) return false;

        adjList.putIfAbsent(e.getSource(), new HashSet<>());
        adjList.putIfAbsent(e.getDestination(), new HashSet<>());

        adjList.get(e.getSource()).add(e);
        adjList.get(e.getDestination()).add(e);

        return true;
    }

    public boolean addEdge(String NodeLabel1, String NodeLabel2, double weight) {
        if (weight != 0.0)
            addEdge(new Edge(addNodeIfNotExist(NodeLabel1),
                    addNodeIfNotExist(NodeLabel2), weight));
        return false;
    }

    public boolean removeEdge(Edge e) {
        if (!edges.remove(e)) return false;
        Set<Edge> edgesOfV1 = adjList.get(e.getSource());
        Set<Edge> edgesOfV2 = adjList.get(e.getDestination());

        if (edgesOfV1 != null) edgesOfV1.remove(e);
        if (edgesOfV2 != null) edgesOfV2.remove(e);

        return true;
    }

    public Set<Edge> getAdjEdgesNew(Vertex v) {
        return adjList.get(v).stream()
                .filter(e -> e.getSource().equals(v))
                .collect(Collectors.toSet());
    }

    public Vertex getStartNode() {
        return vertices.stream().findFirst().get();
    }

    public Vertex getLastNode() {
        List<Vertex> tempList = new ArrayList<>();
        tempList.addAll(vertices);
        return tempList.get(tempList.size()-1);
    }

    public Set<Vertex> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }

    public Set<Edge> getEdges() {
        return Collections.unmodifiableSet(edges);
    }

    public Double getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(Double maxCost) {
        this.maxCost = maxCost;
    }

    public Double getMinReliability() {
        return minReliability;
    }

    public void setMinReliability(Double minReliability) {
        this.minReliability = minReliability;
    }
}
