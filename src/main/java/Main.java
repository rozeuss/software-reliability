import sr.algorithm.Algorithm;
import sr.algorithm.OptimizationParams;
import sr.graph.Graph;
import sr.graph.GraphFactory;
import sr.util.View;

public class Main {

    public static void main(String[] args) {
        Graph graph = GraphFactory.getInstance().createGraph(
                "transition-matrix.txt",
                "coefficients.txt",
                "costs.txt"
        );
        Algorithm algorithm = new Algorithm(graph);
        algorithm.run(OptimizationParams.readOptimizationParams("params.txt"));
        algorithm.printPathsWithProbabilities();
        View view = new View(graph);
        view.show();
    }
}
