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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.Files.*;

@AllArgsConstructor
public class FileSupplier {

    private static final Logger LOG = LoggerFactory.getLogger(FileSupplier.class);
    private static final String USER_DIRECTORY_PROPERTY = "user.dir";

    private static Path resolveFilePath(String filePath) {
        String currentDirectory = System.getProperty(USER_DIRECTORY_PROPERTY);
        Path projectPath = Paths.get(currentDirectory);
        return projectPath.resolve(filePath);
    }

    public static Optional<Path> getFile(String filePath) {
        String dirPath = resolveFilePath(filePath).getParent().toString();
        String fileName = resolveFilePath(filePath).getFileName().toString();

        try (var stream = list(Paths.get(dirPath))) {
            return stream.filter(path -> !isDirectory(path))
                    .filter(path -> path.getFileName().toString().equals(fileName))
                    .findFirst();
        } catch (IOException e) {
            LOG.error("Error while accessing the file '{}': {}.", filePath, e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<Path> getResourceFile(String filePath) {
        String dirPath = resolveFilePath(filePath).getParent().toString();
        String fileName = resolveFilePath(filePath).getFileName().toString();

        URL resource = FileSupplier.class.getClassLoader().getResource(dirPath);

        if (resource == null) {
            LOG.error("Directory not found for path '{}'.", dirPath);
            return Optional.empty();
        }

        try (var stream = list(Paths.get(resource.toURI()))) {
            return stream.filter(path -> !isDirectory(path))
                    .filter(path -> path.getFileName().toString().equals(fileName))
                    .findFirst();
        } catch (IOException | URISyntaxException e) {
            LOG.error("Error while accessing the resource file '{}'.", filePath);
            return Optional.empty();
        }
    }

    public static List<String> readFile(String filePath) {
        var path = getFile(filePath);

        if (path.isEmpty()) {
            LOG.error("No resource could be read for path '{}'.", filePath);
            return Collections.emptyList();
        }

        try (var reader = newBufferedReader(path.get())) {
            return reader.lines().toList();
        } catch (IOException e) {
            LOG.error("Error while reading from file '{}': {}.", path.get(), e.getMessage());
            return Collections.emptyList();
        }
    }

    public static Optional<Path> writeFile(String fileName, List<String> content) {
        Path filePath = resolveFilePath(fileName);
        Path dirPath = filePath.getParent();

       if (!Files.exists(dirPath)) {
           try {
               Files.createDirectories(dirPath);
               LOG.info("Successfully created parent directory '{}'.", dirPath);
           } catch (IOException e) {
               LOG.error("Error while creating parent directory '{}'.", dirPath);
               return Optional.empty();
           }
       }

        try (var writer = newBufferedWriter(filePath)) {
            String lines = content.stream().collect(Collectors.joining(System.lineSeparator()));
            writer.write(lines);
            LOG.info("Successfully wrote to file '{}'.", filePath.toFile().getName());
            return Optional.of(filePath);
        } catch (IOException e) {
            LOG.error("Error while writing to file '{}': {}.", filePath.toFile().getName(), e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<Path> hashFileContent(String sourceFilePath, String destinationFilePath) {
        var content = readFile(sourceFilePath)
                .stream()
                .map(password -> new HashPassword(password).toString())
                .toList();

        return writeFile(destinationFilePath, content);
    }

    public static String decryptHash(String hash, String keyFilePath) {
        var lines = readFile(keyFilePath)
                .stream()
                .map(line -> new HashPassword(line.substring(0, line.indexOf(":"))))
                .sorted()
                .toList();

        var index = Collections.binarySearch(lines, new HashPassword().setHash(hash));
        return (index < 0) ? "No Match Found" : lines.get(index).getPlain();
    }

}