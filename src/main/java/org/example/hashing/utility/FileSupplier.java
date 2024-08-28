package org.example.hashing.utility;

import lombok.AllArgsConstructor;
import org.example.hashing.model.HashPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.nio.file.Files.newBufferedReader;

@AllArgsConstructor
public class FileSupplier {

    private static final Logger LOG = LoggerFactory.getLogger(FileSupplier.class);

    public static Optional<String> getFile(String dirPath, String fileName) {
        URL resource = FileSupplier.class.getClassLoader().getResource(dirPath);

        try (Stream<Path> stream = Files.list(Paths.get(Objects.requireNonNull(resource).toURI()))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toFile().getName().equals(fileName))
                    .findFirst()
                    .map(file -> file.getFileName().toString());
        } catch (IOException | URISyntaxException e) {
            LOG.error("Error while accessing the file: {}", fileName, e);
            return Optional.empty();
        }
    }

    private static List<String> readFile(String uri) {

        var path = Paths.get(Objects.requireNonNull(uri));

        if (!Files.exists(path))
            throw new RuntimeException("File not found: " + uri);

        List<String> lines;
        try (var reader = newBufferedReader(path)) {
            lines = reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

    private static String writeToFile(String uri, List<String> lines) {
        var path = Paths.get(Objects.requireNonNull(uri));

        try (var writer = Files.newBufferedWriter(path)) {
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                if (i < lines.size() - 1) {
                    writer.newLine();
                }
            }
            return path.toFile().getName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hashFileContent(String uri) {
        var lines = readFile(uri)
                .stream()
                .map(password -> new HashPassword(password).toString())
                .toList();

        return writeToFile("hashed-passwords.txt", lines);
    }


    public static String decryptPassword(String hash) {
        var lines = readFile("hashed-passwords.txt")
                .stream()
                .map(line -> new HashPassword(line.substring(0, line.indexOf(":"))))
                .sorted()
                .toList();

        var index = Collections.binarySearch(lines, new HashPassword().setMD5(hash));
        if (index < 0) {
            return "No match";
        }
        return lines.get(index).getRaw();
    }

}
