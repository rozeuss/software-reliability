package sr.utils;


import org.graphstream.graph.implementations.SingleGraph;
import sr.core.Graph;
import sr.core.Vertex;


import java.text.DecimalFormat;

public class GraphPrinter {

    private Graph graph;
    private org.graphstream.graph.Graph graphToBePrinted;

    public GraphPrinter(Graph graph) {
        graphToBePrinted = new SingleGraph("Graph");
        this.graph = graph;
        initializeGraphToBePrinted();
    }

    private void initializeGraphToBePrinted() {
        graph.getEdges().stream().forEach(edge -> {
            addNodeToGraphToBePrinted(edge.getSource());
            addNodeToGraphToBePrinted(edge.getDestination());
            graphToBePrinted.addEdge(
                    Double.toString(edge.getWeight()) + edge.getSource().getId() + edge.getDestination().getId(),
                    edge.getSource().getId(),
                    edge.getDestination().getId(), true);
        });
    }

    private void addNodeToGraphToBePrinted(Vertex node) {
        try {
            graphToBePrinted.addNode(node.getId());
        } catch (Exception e) {
            System.out.println("Node already exists");
        }
    }

    public void printGraph() {
        if (areReliabilitySet()) {
            addCostAndReliabilityLabelsToNodes();
        } else {
            addAttributeLabelsToNodes();
        }
        addLabelsToEnges();
        printReliabilityOnConsole();
        graphToBePrinted.display();
    }

    private void printReliabilityOnConsole() {
        System.out.println("R = [ ");
        graph.getVertices().stream().forEach(node -> {
            System.out.println(node.getId() + " reliability: " + node.getReliability() + ", ");
        });
        System.out.print(" ]");
    }

    private void addAttributeLabelsToNodes() {
        DecimalFormat df = new DecimalFormat("#.00");
        graph.getVertices().stream().forEach(node -> {
            org.graphstream.graph.Node e1 = graphToBePrinted.getNode(node.getId());
            e1.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 50px; text-alignment: center;");
            e1.setAttribute("ui.label", node.getId() + ", "
                    + df.format(node.getAlpha()) + ", "
                    + df.format(node.getBeta())  + ", "
                    + df.format(node.getS()));
        });
    }

    private void addCostAndReliabilityLabelsToNodes() {
        DecimalFormat df = new DecimalFormat("#.000");
        graph.getVertices().stream().forEach(node -> {
            org.graphstream.graph.Node e1 = graphToBePrinted.getNode(node.getId());
            e1.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 50px; text-alignment: center;");
            e1.setAttribute("ui.label", node.getId() + ", "
                    + df.format(node.getReliability()) + ", "
                    + df.format(node.getCost()));
        });
    }

    private void addLabelsToEnges() {
        graph.getEdges().stream().forEach(edge -> {
            org.graphstream.graph.Edge e1 = graphToBePrinted.getEdge(
                    Double.toString(edge.getWeight()) + edge.getSource().getId() + edge.getDestination().getId());
            e1.setAttribute("ui.label", Double.toString(edge.getWeight()));
        });
    }

    private boolean areReliabilitySet() {
        return graph.getVertices().stream().findFirst().get().getReliability() != 0.0;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public org.graphstream.graph.Graph getGraphToBePrinted() {
        return graphToBePrinted;
    }
}
