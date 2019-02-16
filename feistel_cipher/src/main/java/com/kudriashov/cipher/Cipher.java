package com.kudriashov.cipher;

public interface Cipher {

    int encrypt(int plaintext, int key);

    int decrypt(int ciphertext, int key);
}
