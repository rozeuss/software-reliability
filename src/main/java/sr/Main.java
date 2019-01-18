package sr;

import sr.optimization.algorithm.Parameters;
import sr.optimization.algorithm.StructureProblem;
import sr.optimization.graph.Graph;
import sr.optimization.graph.GraphFactory;
import sr.optimization.graph.Path;
import sr.optimization.graph.Vertex;
import sr.optimization.util.View;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        Graph graph = GraphFactory.getInstance().createGraph(
                "transition-matrix.txt",
                "coefficients.txt",
                "cost-constants.txt"
        );

        Map<Path, Double> result = new StructureProblem(graph).solve(Parameters.readParams("params.txt"));
        printResult(graph, result);

        View view = new View(graph);
        view.show();
    }

    private static void printResult(Graph graph, Map<Path, Double> result) {
        System.out.format("%-30s %10s", "Path", "Probability").println();
        result.forEach((k, v) -> {
            List<String> collect = k.getVertices().stream().map(Vertex::getId).collect(Collectors.toList());
            System.out.format("%-30s %10.5f", collect, v).println();
        });
        System.out.print("\nR* = [ ");
        System.out.print(graph.getVertices().stream().map(v -> String.valueOf(v.getReliability()))
                .collect(Collectors.joining(", ")));
        System.out.print("]");
        System.out.println("\nReliabilities: ");
        graph.getVertices().forEach(node -> System.out.println("(" + node.getId() + ") ->  " + node.getReliability()));
    }
}
