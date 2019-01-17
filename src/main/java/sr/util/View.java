package sr.util;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import sr.graph.Graph;
import sr.graph.Vertex;

import java.util.function.Consumer;

public class View {

    private static final String ALPHA = "\u03B1";
    private static final String BETA = "\u03B2";
    private org.graphstream.graph.Graph guiGraph = new DefaultGraph("GUI");
    private Graph dataGraph;

    public View(Graph dataGraph) {
        this.dataGraph = dataGraph;
        setupGuiGraph();
    }

    private void setupGuiGraph() {
        dataGraph.getVertices().forEach(setupVertices());
        colorLastNode();
        dataGraph.getEdges().forEach(setupEdges());
        guiGraph.addAttribute("ui.stylesheet", "url('file://" + FileUtils.getStylesheetPath() + "')");
    }

    private void colorLastNode() {
        Node node = guiGraph.getNode(dataGraph.getVertices().size() - 1);
        node.addAttribute("ui.style", "fill-color: red, #254; fill-mode: gradient-diagonal2;");
    }

    private Consumer<sr.graph.Edge> setupEdges() {
        return edge -> {
            Edge e = guiGraph.addEdge(edge.getSource().getId() + edge.getDestination().getId(),
                    edge.getSource().getId(), edge.getDestination().getId(), true);
            e.addAttribute("ui.label", Double.toString(edge.getWeight()));
        };
    }

    private Consumer<Vertex> setupVertices() {
        return vertex -> {
            Node n = guiGraph.addNode(vertex.getId());
            n.addAttribute("ui.label", "(" + vertex.getId() + ") "
                    + String.format(ALPHA + ": %.02f", vertex.getAlpha()) + ", "
                    + String.format(BETA + ": %.02f", vertex.getBeta()) + ", "
                    + String.format("S: %.02f", vertex.getS()) + ", "
                    + String.format("R: %.02f", vertex.getReliability()) + ", "
                    + String.format("C: %.02f", vertex.getCost()));
        };
    }

    public void show() {
        guiGraph.display();
    }
}
