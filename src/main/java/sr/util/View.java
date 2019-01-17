package sr.util;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import sr.graph.Graph;
import sr.graph.Vertex;

import java.text.DecimalFormat;
import java.util.function.Consumer;

public class View {

    private org.graphstream.graph.Graph guiGraph = new DefaultGraph("GUI");
    private Graph dataGraph;

    public View(Graph dataGraph) {
        this.dataGraph = dataGraph;
        setupGuiGraph();
    }

    private void setupGuiGraph() {
        dataGraph.getVertices().forEach(setupVertices());
        dataGraph.getEdges().forEach(setupEdges());
    }

    private Consumer<sr.graph.Edge> setupEdges() {
        return edge -> {
            Edge e = guiGraph.addEdge(edge.getSource().getId() + edge.getDestination().getId(),
                    edge.getSource().getId(), edge.getDestination().getId(), true);
            e.setAttribute("ui.label", Double.toString(edge.getWeight()));
            e.setAttribute("ui.style", "shape: line; fill-color: #C3C3C3; arrow-size: 3px, 2px;");
        };
    }

    private Consumer<Vertex> setupVertices() {
        return vertex -> {
            Node n = guiGraph.addNode(vertex.getId());
            DecimalFormat df = new DecimalFormat("#.00");
            n.setAttribute("ui.style", "fill-color: #00FFFF; size: 30px;");
            n.setAttribute("ui.label", vertex.getId() + ", "
                    + df.format(vertex.getAlpha()) + ", "
                    + df.format(vertex.getBeta()) + ", "
                    + df.format(vertex.getS()));
        };
    }

    public void show() {
        if (areReliabilitiesSet()) {
            addCostAndReliabilityLabelsToNodes();
        }
        printReliabilities();
        guiGraph.display();
    }

    private void printReliabilities() {
        System.out.println("Reliabilities: ");
        dataGraph.getVertices().forEach(node -> System.out.println(node.getId() + " " + node.getReliability()));
    }

    private void addCostAndReliabilityLabelsToNodes() {
        DecimalFormat df = new DecimalFormat("#.000");
        dataGraph.getVertices().forEach(node -> {
            org.graphstream.graph.Node e1 = guiGraph.getNode(node.getId());
            e1.setAttribute("ui.style", "fill-color: #00FFFF; size: 30px;");
            e1.setAttribute("ui.label", node.getId() + ", "
                    + df.format(node.getReliability()) + ", "
                    + df.format(node.getCost()));
        });
    }

    private boolean areReliabilitiesSet() {
        return dataGraph.getVertices().stream().anyMatch(v -> v.getReliability() != 0.0);
    }
}
