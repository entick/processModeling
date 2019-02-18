package com.kudriashov.cipher;

public class FeistelCipherModel implements Cipher {

    private int rounds;

    public FeistelCipherModel() {
        this(Constants.DEFAULT_ROUNDS);
    }

    public FeistelCipherModel(int rounds) {
        this.rounds = rounds;
    }

    public int encrypt(int plaintext, int key) {
        int shift = binpow(2, Constants.BLOCK / 2);
        int right = plaintext % shift;
        int left = plaintext / shift;
        for (int i = 0; i < this.rounds; i++) {
            int[] tmp = encryptRound(left, right, roundKey(i, key));
            left = tmp[0];
            right = tmp[1];
        }
        return right * shift + left;
    }

    public int decrypt(int ciphertext, int key) {
        int shift = binpow(2, Constants.BLOCK / 2);
        int right = ciphertext % shift;
        int left = ciphertext / shift;
        for (int i = this.rounds - 1; i >= 0; i--) {
            int[] tmp = decryptRound(right, left, roundKey(i, key));
            left = tmp[0];
            right = tmp[1];
        }
        return right * shift + left;
    }

    private int[] encryptRound(int left, int right, int roundKey) {
        if (Constants.DEBUG) {
            System.out.println(
                    "Start 0x" + Integer.toHexString(left) + " 0x" + Integer
                            .toHexString(right) + " 0x" + Integer
                            .toHexString(roundKey));
        }
        int f = right ^ roundKey;
        f = Constants.S_BOX[f % (Constants.BLOCK / 4)][f / binpow(2,
                Constants.BLOCK / 4)];
        left = left ^ f;
        if (Constants.DEBUG) {
            System.out.println(
                    "End   0x" + Integer.toHexString(left) + " 0x" + Integer
                            .toHexString(right) + " 0x" + Integer
                            .toHexString(roundKey));
        }
        return new int[] { right, left };
    }

    private int[] decryptRound(int left, int right, int roundKey) {
        if (Constants.DEBUG) {
            System.out.println(
                    "Start 0x" + Integer.toHexString(left) + " 0x" + Integer
                            .toHexString(right) + " 0x" + Integer
                            .toHexString(roundKey));
        }
        int f = (left ^ roundKey);
        f = Constants.S_BOX[f % (Constants.BLOCK / 4)][f / binpow(2,
                Constants.BLOCK / 4)];
        right = (right ^ f);
        if (Constants.DEBUG) {
            System.out.println(
                    "End   0x" + Integer.toHexString(left) + " 0x" + Integer
                            .toHexString(right) + " 0x" + Integer
                            .toHexString(roundKey));
        }
        return new int[] { left, right };
    }

    private int roundKey(int nRound, int key) {
        int shift = binpow(2, nRound);
        return ((key / shift + (key % shift) * binpow(2,
                Constants.DEFAULT_ROUNDS - shift))
                / binpow(2, 4)) % binpow(2, 8);
    }

    private int binpow(int x, int pow) {
        int result = 1;
        while (pow > 0) {
            if ((pow & 1) > 0) {
                result *= x;
            }
            x *= x;
            pow >>= 1;
        }
        return result;
    }

}