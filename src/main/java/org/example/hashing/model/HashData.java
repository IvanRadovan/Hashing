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
public class HashData {

    private String input;
    private String md5;
    private String sha256;

    public void setInput(String input) {
        this.input = input;
        this.md5 = md5Generator(input);
        this.sha256 = sha256Generator(input);
    }

    private String sha256Generator(String input) {
        return Hashing.sha256()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
    }

    private String md5Generator(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
