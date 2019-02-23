package com.kudriashov.hash;

import java.math.BigInteger;

public class HashImpl implements Hash {
    @Override
    public int hash(String plaintext) {
        long plainLong = new BigInteger(plaintext).longValue();
        int[] parts = splitPlaintext(plainLong);
        return (parts[0] & parts[1]) | (parts[2] ^ parts[3]);
    }

    private int[] splitPlaintext(Long plaintext) {
        int[] parts = new int[4];
        for (int i = 0; i < parts.length; i++) {
            parts[i] = (int) (plaintext & 0b1111111111111111); // safe
            plaintext >>>= 16;
        }
        return parts;
    }
}
