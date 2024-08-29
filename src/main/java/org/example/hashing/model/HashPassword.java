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

    private String plain;
    private String md5;
    private String sha256;

    public HashPassword(String plain) {
        this.plain = plain;
        this.md5 = encryptToMD5(plain);
        this.sha256 = encryptToSHA256(plain);
    }

    public HashPassword setMD5(String hash) {
        this.md5 = hash;
        return this;
    }

    private String encryptToMD5(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(plain.getBytes());
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

    private String encryptToSHA256(String plain) {
        return Hashing.sha256()
                .hashString(plain, StandardCharsets.UTF_8)
                .toString();
    }

    @Override
    public int compareTo(HashPassword other) {
        return this.md5.compareTo(other.md5);
    }

    @Override
    public String toString() {
        return "%s:%s".formatted(plain, md5);
    }
}