package org.example.hashing.utility;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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
}
