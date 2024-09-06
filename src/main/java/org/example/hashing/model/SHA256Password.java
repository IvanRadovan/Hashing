package org.example.hashing.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SHA256Password extends HashPassword {

    public SHA256Password(String plain) {
        super(plain);
        this.hash = super.encryptToSHA256(plain);
    }
}
