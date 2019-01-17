import sr.algorithm.Algorithm;
import sr.algorithm.OptimizationParams;
import sr.graph.Graph;
import sr.graph.GraphFactory;
import sr.graph.Vertex;
import sr.util.View;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        Graph graph = GraphFactory.getInstance().createGraph(
                "transition-matrix.txt",
                "coefficients.txt",
                "costs.txt"
        );

        Map<List<Vertex>, Double> result = new Algorithm(graph)
                .run(OptimizationParams.readOptimizationParams("params.txt"));

        System.out.format("%-30s %10s", "Paths", "Probabilities").println();
        result.forEach((k, v) -> {
            List<String> collect = k.stream().map(Vertex::getId).collect(Collectors.toList());
            System.out.format("%-30s %10.5f", collect, v).println();
        });

        System.out.println("\nReliabilities: ");
        graph.getVertices().forEach(node -> System.out.println("("+node.getId() + ") ->  " + node.getReliability()));

        View view = new View(graph);
        view.show();
    }
}
