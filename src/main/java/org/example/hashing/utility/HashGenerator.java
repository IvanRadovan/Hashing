package org.example.hashing.utility;

import org.example.hashing.model.HashPassword;
import org.example.hashing.model.MD5Password;
import org.example.hashing.model.SHA256Password;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

import static org.example.hashing.utility.FileSupplier.readFile;
import static org.example.hashing.utility.FileSupplier.writeFile;

public class HashGenerator {

    private static final int BIT_32 = 32;
    private static final int BIT_64 = 64;
    private static final String COLON = ":";
    private static final String EMPTY_CONSTRUCTOR = "";

    public static Optional<Path> hashFileContent(String sourceFilePath, String destinationFilePath) {
        var content = readFile(sourceFilePath)
                .stream()
                .map(password -> new StringBuilder(password)
                        .append(COLON)
                        .append(new MD5Password(password))
                        .append(COLON)
                        .append(new SHA256Password(password))
                        .toString())
                .toList();

        return writeFile(destinationFilePath, content);
    }

    public static String decryptHash(String hash, String keyFilePath) {
        return switch (hash.length()) {
            case BIT_32 -> binarySearch(hash, keyFilePath, MD5Password::new);
            case BIT_64 -> binarySearch(hash, keyFilePath, SHA256Password::new);
            default -> "Unrecognized hash";
        };
    }

    private static <T extends HashPassword> String binarySearch(String hash, String keyFilePath, Function<String, T> factory) {
        var lines = readFile(keyFilePath)
                .stream()
                .map(line -> factory.apply(line.substring(0, line.indexOf(COLON))))
                .sorted()
                .toList();

        T passwordObject = factory.apply(EMPTY_CONSTRUCTOR);
        passwordObject.setHash(hash);
        var index = Collections.binarySearch(lines, passwordObject);

        return (index < 0) ? "No Match Found" : lines.get(index).getPlain();
    }

}
