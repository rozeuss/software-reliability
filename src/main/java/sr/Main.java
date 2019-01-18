package sr;

import sr.optimization.graph.Graph;
import sr.optimization.graph.GraphFactory;
import sr.optimization.graph.Vertex;
import sr.optimization.problem.Parameters;
import sr.optimization.problem.Solution;
import sr.optimization.problem.StructureProblem;
import sr.optimization.util.View;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        Graph graph = GraphFactory.getInstance().createGraph(
                "transition-matrix.txt",
                "coefficients.txt",
                "cost-constants.txt"
        );

        StructureProblem problem = new StructureProblem(graph);
        Solution solution = problem.solve(Parameters.readParams("params.txt"));
        printResult(graph, problem, solution);

        View view = new View(graph);
        view.show();
    }

    private static void printResult(Graph graph, StructureProblem problem, Solution solution) {
        System.out.format("\nFound solution.\nReliability: %.5f\t Cost: %.5f\n",
                solution.getReliability(), solution.getCost()).println();
        System.out.format("%-30s %10s", "Path", "Probability").println();
        problem.getPathProbabilities().forEach((k, v) -> {
            List<String> collect = k.getVertices().stream().map(Vertex::getId).collect(Collectors.toList());
            System.out.format("%-30s %10.5f", collect, v).println();
        });
        System.out.print("\nR* = [ ");
        System.out.print(graph.getVertices().stream().map(v -> String.valueOf(v.getReliability()))
                .collect(Collectors.joining(", ")));
        System.out.print("]");

    }
}
