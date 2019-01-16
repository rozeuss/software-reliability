import sr.algorithm.Algorithm;
import sr.algorithm.OptimizationParams;
import sr.core.Graph;
import sr.core.GraphFactory;
import sr.utils.GraphPrinter;

public class Main {

    public static void main(String[] args) {
        Graph graph = GraphFactory.createGraph(
                "transition-matrix.txt",
                "alpha.txt",
                "beta.txt",
                "costs.txt",
                "params.txt"
        );
        OptimizationParams params = new OptimizationParams();

        GraphPrinter graphPrinter = new GraphPrinter(graph);
        graphPrinter.printGraph();
        Algorithm algorithm = new Algorithm(graph);
        algorithm.run(graph.getMaxCost(), graph.getMinReliability());
        algorithm.printPathsWithProbabilities();
        graphPrinter.printGraph();
    }
}
