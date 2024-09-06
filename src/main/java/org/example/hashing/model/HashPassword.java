package org.example.hashing.model;

import com.google.common.hash.Hashing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
public abstract class HashPassword implements Comparable<HashPassword> {

    protected String plain;
    protected String hash;

    public HashPassword(String plain) {
        this.plain = plain;
    }

    protected String encryptToMD5(String plain) {
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

    protected String encryptToSHA256(String plain) {
        return Hashing.sha256()
                .hashString(plain, StandardCharsets.UTF_8)
                .toString();
    }

    @Override
    public int compareTo(HashPassword other) {
        return Comparator.comparing(HashPassword::getHash)
                .compare(this, other);
    }

    @Override
    public String toString() {
        return this.hash;
    }

}