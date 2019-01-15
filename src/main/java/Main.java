import sr.algorithm.Algorithm;
import sr.core.Graph;
import sr.utils.GraphManager;
import sr.utils.GraphPrinter;
import sr.utils.GraphUtils;
import sr.utils.ReaderHelper;

public class Main {

    public static void main(String[] args) {
        ReaderHelper readerHelper = new ReaderHelper();
        GraphUtils graphUtils = new GraphUtils();
        GraphManager graphManager = new GraphManager(readerHelper, graphUtils);
        Graph graph = graphManager.createGraph(
                "transition-matrix.txt",
                "alpha.txt",
                "beta.txt",
                "costs.txt",
                "params.txt");

        GraphPrinter graphPrinter = new GraphPrinter(graph);
        graphPrinter.printGraph();

        Algorithm algorithm = new Algorithm(graph);
        algorithm.run(graph.getMaxCost(), graph.getMinReliability());
        algorithm.printPathsWithProbabilities();

        graphPrinter.printGraph();
    }
}
