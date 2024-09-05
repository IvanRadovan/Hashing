package org.example.hashing.model.hash;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MD5Password extends HashPassword {

    public MD5Password(String plain) {
        super(plain);
        this.hash = super.encryptToMD5(plain);
    }
}
