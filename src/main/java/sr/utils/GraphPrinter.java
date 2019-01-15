package sr.utils;


import org.graphstream.graph.implementations.SingleGraph;
import sr.core.Graph;
import sr.core.Node;


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
            addNodeToGraphToBePrinted(edge.getV1());
            addNodeToGraphToBePrinted(edge.getV2());
            graphToBePrinted.addEdge(
                    Double.toString(edge.getWeight()) + edge.getV1().getLabel() + edge.getV2().getLabel(),
                    edge.getV1().getLabel(),
                    edge.getV2().getLabel(), true);
        });
    }

    private void addNodeToGraphToBePrinted(Node node) {
        try {
            graphToBePrinted.addNode(node.getLabel());
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
            System.out.println(node.getLabel() + " reliability: " + node.getReliability() + ", ");
        });
        System.out.print(" ]");
    }

    private void addAttributeLabelsToNodes() {
        DecimalFormat df = new DecimalFormat("#.00");
        graph.getVertices().stream().forEach(node -> {
            org.graphstream.graph.Node e1 = graphToBePrinted.getNode(node.getLabel());
            e1.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 50px; text-alignment: center;");
            e1.setAttribute("ui.label", node.getLabel() + ", "
                    + df.format(node.getAlpha()) + ", "
                    + df.format(node.getBeta())  + ", "
                    + df.format(node.getS()));
        });
    }

    private void addCostAndReliabilityLabelsToNodes() {
        DecimalFormat df = new DecimalFormat("#.000");
        graph.getVertices().stream().forEach(node -> {
            org.graphstream.graph.Node e1 = graphToBePrinted.getNode(node.getLabel());
            e1.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 50px; text-alignment: center;");
            e1.setAttribute("ui.label", node.getLabel() + ", "
                    + df.format(node.getReliability()) + ", "
                    + df.format(node.getCost()));
        });
    }

    private void addLabelsToEnges() {
        graph.getEdges().stream().forEach(edge -> {
            org.graphstream.graph.Edge e1 = graphToBePrinted.getEdge(
                    Double.toString(edge.getWeight()) + edge.getV1().getLabel() + edge.getV2().getLabel());
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
