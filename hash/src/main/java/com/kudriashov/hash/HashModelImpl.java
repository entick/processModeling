package com.kudriashov.hash;

import java.math.BigInteger;

public class HashModelImpl implements Hash {

    @Override
    public int hash(String plaintext) {
        BigInteger bigInteger = new BigInteger(plaintext);
        int[] parts = splitPlaintext(bigInteger);
        return or(and(parts[0], parts[1], Constants.BIT_COUNT), xor(parts[2], parts[3], Constants.BIT_COUNT), Constants.BIT_COUNT);
    }

    private int[] splitPlaintext(BigInteger plaintext) {
        int[] parts = new int[4];
        for (int i = 0; i < parts.length; i++) {
            parts[i] = plaintext.mod(Constants._2_16).intValue(); // safe
            plaintext = plaintext.divide(Constants._2_16);
        }
        return parts;
    }

    private int xor(int val1, int val2, int bitCount) {
        int result = 0;
        int divider = 1;
        for (int i = 0; i < bitCount; i++) {
            result += ((val1 / divider + val2 / divider) % 2) * divider;
            divider *= 2;
        }
        return result;
    }

    private int and(int val1, int val2, int bitCount) {
        int result = 0;
        int divider = 1;
        for (int i = 0; i < bitCount; i++) {
            result += (((val1 / divider)  % 2) * ((val2 / divider) % 2)) * divider;
            divider *= 2;
        }
        return result;
    }

    private int or(int val1, int val2, int bitCount) {
        int result = 0;
        int divider = 1;
        for (int i = 0; i < bitCount; i++) {
            result += Math.round((((double)((val1 / divider) % 2 + (val2 / divider) % 2)) / 2)) * divider;
            divider *= 2;
        }
        return result;
    }

}
