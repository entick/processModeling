package com.kudriashov.cipher;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FeistalCipherTest {

    private static int POW_2_16 = 0b10000000000000000;

    Cipher cipher;

    @Test
    public void decryptEqualsPlaintext() {
        cipher = new FeistelCipher();
        for (int i = 0; i < POW_2_16; i++) {
            int encrypted = cipher.encrypt(i, Constants.DEFAULT_KEY);
            //System.out.println("-------------------------------------------------");
            int decrypted = cipher.decrypt(encrypted, Constants.DEFAULT_KEY);
            assertEquals(i, decrypted);
        }

    }

    @Test
    public void decryptEqualsPlaintextModel() {
        cipher = new FeistelCipherModel();
        for (int i = 0; i < POW_2_16; i++) {
            int encrypted = cipher.encrypt(i, Constants.DEFAULT_KEY);
            //System.out.println("------------------------------------------------------");
            int decrypted = cipher.decrypt(encrypted, Constants.DEFAULT_KEY);
            assertEquals(i, decrypted);
        }
    }

}
