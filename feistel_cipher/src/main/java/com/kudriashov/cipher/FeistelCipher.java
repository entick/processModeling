package com.kudriashov.cipher;

public class FeistelCipher implements Cipher {
    private int rounds;

    public FeistelCipher() {
        this(Constants.DEFAULT_ROUNDS);
    }

    public FeistelCipher(int rounds) {
        this.rounds = rounds;
    }

    public int encrypt(int plaintext, int key) {
        int right = (plaintext & 0b11111111);
        int left = (plaintext >> (Constants.BLOCK / 2));
        for (int i = 0; i < this.rounds; i++) {
            int[] tmp = encryptRound(left, right, roundKey(i, key));
            left = tmp[0];
            right = tmp[1];
        }
        return (right << (Constants.BLOCK / 2)) + left;
    }

    public int decrypt(int ciphertext, int key) {
        int right = (ciphertext & 0b11111111);
        int left = (ciphertext >> (Constants.BLOCK / 2));
        for (int i = this.rounds - 1; i >= 0; i--) {
            int[] tmp = decryptRound(right, left, roundKey(i, key));
            left = tmp[0];
            right = tmp[1];
        }
        return (right << (Constants.BLOCK / 2)) + left;
    }

    private int[] encryptRound(int left, int right, int roundKey) {
        //System.out.println("Start " + Integer.toHexString(left) + " " + Integer.toHexString(right) + " " + Integer.toHexString(roundKey));
        int f = right ^ roundKey;
        f = Constants.SYM_S_BOX[f & 0b1111][f >> (Constants.BLOCK / 4)];
        left = left ^ f;
        //System.out.println("End " + Integer.toHexString(right) + " " + Integer.toHexString(left) + " " + Integer.toHexString(roundKey));
        return new int[]{right, left};
    }

    private int[] decryptRound(int left, int right, int roundKey) {
        //System.out.println("Start " + Integer.toHexString(left) + " " + Integer.toHexString(right) + " " + Integer.toHexString(roundKey));
        int f = (left ^ roundKey);
        f = Constants.SYM_S_BOX[f & 0b1111][f >> (Constants.BLOCK / 4)];
        right = (right ^ f);
        //System.out.println("End " + Integer.toHexString(right) + " " + Integer.toHexString(left) + " " + Integer.toHexString(roundKey));
        return new int[]{left, right};
    }

    private int roundKey(int nRound, int key) {
        key = (key >>> nRound) | (key << (Short.SIZE - nRound));
        key &= Constants.ROUND_KEY_BITS;
        return key >> Constants.ROUND_KEY_BITS_SHIFT;
    }

}
