package sr.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    private FileUtils() {
    }

    public static double[][] readDoubleArray(String filename) {
        List<String> lines = readLines(filename);
        return lines.stream()
                .map(s -> s.split(" "))
                .map(strings -> Arrays.stream(strings).mapToDouble(Double::valueOf).toArray())
                .toArray(double[][]::new);
    }

    public static List<Double> readLinesAsDoubles(String filename) {
        return readLines(filename).stream().map(Double::valueOf).collect(Collectors.toList());
    }

    public static List<String> readLines(String filename) {
        Path path = null;
        try {
            path = Paths.get(FileUtils.class.getClassLoader().getResource(filename).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<String> values = null;
        try (Stream<String> lines = Files.lines(path)) {
            values = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(values).orElse(Collections.emptyList());
    }
}
