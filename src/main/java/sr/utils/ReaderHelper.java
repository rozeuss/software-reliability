package sr.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReaderHelper {

    private static List<Double> mapStringToDobule(String row) {
        return Arrays.stream(row.split("\\s")).map(number -> Double.parseDouble(number)).collect(Collectors.toList());
    }

    private static List<List<Double>> normalizeInput(List<String> rows) {
        return rows.stream().map(row -> mapStringToDobule(row)).collect(Collectors.toList());
    }

    public List<List<Double>> readDoublesFromFile(String filePathToGraphMatrix) {
        filePathToGraphMatrix = filePathToGraphMatrix.replace("\\", "/");
        List<String> lines = readLines(filePathToGraphMatrix);
        return normalizeInput(lines);
    }

    public List<String> readLines(String filename) {
        Path path = null;
        try {
            path = Paths.get(getClass().getClassLoader().getResource(filename).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<String> values = null;
        try (Stream<String> lines = Files.lines(path)) {
            values = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }
}
