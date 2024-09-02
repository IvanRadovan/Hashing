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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.newBufferedReader;

@AllArgsConstructor
public class FileSupplier {

    private static final Logger LOG = LoggerFactory.getLogger(FileSupplier.class);

    public static Optional<Path> getFile(String fileName, String dirPath) {
        URL resource = FileSupplier.class.getClassLoader().getResource(dirPath);

        try (Stream<Path> stream = Files.list(Paths.get(Objects.requireNonNull(resource).toURI()))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toFile().getName().equals(fileName))
                    .findFirst()
                    .map(file -> Path.of(file.getFileName().toString()));
        } catch (IOException | URISyntaxException e) {
            LOG.error("Error while accessing the file '{}': {}.", fileName,e.getMessage());
            return Optional.empty();
        }
    }

    public static List<String> readFile(String fileName, String dirPaths) {
        var path = getFile(fileName, dirPaths);

        if (path.isEmpty()) {
            LOG.error("Cannot read for an nonexistent file '{}'.", fileName);
            return Collections.emptyList();
        }

        try (var reader = newBufferedReader(path.get())) {
            return reader.lines().toList();
        } catch (IOException e) {
            LOG.error("Error while reading from file '{}': {}.", path, e.getMessage());
        }
        return Collections.emptyList();
    }

    public static Optional<Path> writeToFile(String uri, List<String> lines, String... dirPaths) {
        var path = createFileInDir(uri, dirPaths);

        // Create the file at root level if no dir is specified
        path = path.or(() -> Optional.of(Paths.get(uri)));

        try (var writer = Files.newBufferedWriter(path.get())) {
            String content = lines.stream().collect(Collectors.joining(System.lineSeparator()));
            writer.write(content);
            LOG.info("Successfully wrote to file '{}'.", path);
            return path;
        } catch (IOException e) {
            LOG.error("Error while writing to file: '{}': {}.", path, e.getMessage());
            return Optional.empty();
        }
    }

    private static Optional<Path> createFileInDir(String fileName, String... dirPaths) {
        String projectRoot = System.getProperty("user.dir");
        Path relativeDir = Paths.get(projectRoot, dirPaths);
        Path filePath = relativeDir.resolve(fileName);

        try {
            if (!Files.exists(relativeDir)) {
                Files.createDirectories(relativeDir);
                LOG.info("Directory {} created successfully.", relativeDir);
            }

            Files.createFile(filePath);
            return Optional.of(filePath);
        } catch (IOException e) {
            LOG.error("Error while creating file '{}' to directory '{}': {}.", fileName, relativeDir, e.getMessage());
            return Optional.empty();
        }
    }




    public static Optional<Path> hashFileContent(String fileToRead, String toReadDirPath, String newFile, String... dirPaths) {
        var lines = readFile(fileToRead, toReadDirPath)
                .stream()
                .map(password -> new HashPassword(password).toString())
                .toList();

        return writeToFile("hashed-passwords.txt", lines, dirPaths);
    }


    public static String decryptHash(String hash) {
        var lines = readFile("hashed-passwords.txt", "/password/")
                .stream()
                .map(line -> new HashPassword(line.substring(0, line.indexOf(":"))))
                .sorted()
                .toList();

        var index = Collections.binarySearch(lines, new HashPassword().setMD5(hash));

        return (index < 0) ? "No Match Found" : lines.get(index).getPlain();
    }

}