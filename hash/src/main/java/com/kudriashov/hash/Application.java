package com.kudriashov.hash;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class Application {

    public static void main(String[] args) {
        System.out.println(avgDifBitCount(1000));
        System.out.println(Arrays.toString(pFunc(1000, 200)));
    }

    public static double avgDifBitCount(int numberOfExperiments) {
        Hash hash = new HashImpl();
        double count = 0;
        for (int k = 0; k < numberOfExperiments; k++) {
            BigInteger bigInteger = new BigInteger(64, new Random());
            int mainHash = hash.hash(bigInteger.toString());
            for (int i = 0; i < 64; i++) {
                BigInteger tmp = bigInteger.flipBit(i);
                int tmpHash = hash.hash(tmp.toString());
                String tmpString = Integer.toBinaryString(mainHash ^ tmpHash);
                for (int j = 0; j < tmpString.length(); j++) {
                    if (tmpString.charAt(j) == '1') {
                        count++;
                    }
                }
            }
        }
        return count / (numberOfExperiments * 64 * 16);
    }

    public static double[] pFunc(int numberOfExperiments, int kMax) {
        Hash hash = new HashImpl();
        double[] p = new double[kMax];
        Random random = new Random();
        for (int k = 0; k < kMax; k++) {
            double count = 0;
            for (int n = 0; n < numberOfExperiments; n++) {
                String[] plaintext = new String[k + 1];
                int[] hashs = new int[k + 1];
                for (int j = 0; j < k + 1; j++) {
                    plaintext[j] = new BigInteger(64, random).toString();
                    hashs[j] = hash.hash(plaintext[j]);
                }
                OUTER_LOOP:
                for (int i = 0; i < k + 1; i++) {
                    for (int j = i + 1; j < k + 1; j++) {
                        if (hashs[i] == hashs[j]) {
                            count++;
                            break OUTER_LOOP;
                        }
                    }
                }
            }
            count /= numberOfExperiments;
            p[k] = count;
        }
        return p;
    }


}
