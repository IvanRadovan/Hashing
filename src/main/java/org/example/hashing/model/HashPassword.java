package org.example.hashing.model;

import com.google.common.hash.Hashing;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
@NoArgsConstructor
public class HashPassword implements Comparable<HashPassword> {

    private String raw;
    private String md5;
    private String sha256;

    public HashPassword(String raw) {
        this.raw = raw;
        this.md5 = encryptToMD5(raw);
        this.sha256 = encryptToSHA256(raw);
    }

    public HashPassword setMD5(String hash) {
        this.md5 = hash;
        return this;
    }

    private String encryptToMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String encryptToSHA256(String input) {
        return Hashing.sha256()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
    }

    @Override
    public int compareTo(HashPassword other) {
        return this.md5.compareTo(other.md5);
    }

    @Override
    public String toString() {
        return "%s:%s".formatted(raw, md5);
    }
}