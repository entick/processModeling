package com.kudriashov.hash;

import com.kudriashov.hash.Hash;
import com.kudriashov.hash.HashImpl;
import com.kudriashov.hash.HashModelImpl;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class HashTest {

    @Test
    public void hashAndHashModelShouldBeEquals() {
        Hash hashModel = new HashModelImpl();
        Hash hash = new HashImpl();
        BigInteger bigInteger = BigInteger.ZERO;
        BigInteger bigInteger1 = new BigInteger("18446744073709551616");
        while (bigInteger.compareTo(bigInteger1) < 0) {
            long timeStartModel = System.nanoTime();
            String value = bigInteger.toString();
            int hashModelValue = hashModel.hash(value);
            long timeEndModel = System.nanoTime();
            int hashValue = hash.hash(value);
            long timeEndImpl = System.nanoTime();
            assertEquals(hashValue, hashModelValue);
            bigInteger = bigInteger.add(BigInteger.ONE);
            String binary = bigInteger.toString(2);
            if (binary.indexOf('1') == binary.lastIndexOf('1')) {
                System.out.println(bigInteger.toString() + " Model time: " + (timeEndModel - timeStartModel) + "; Impl time: " + (timeEndImpl - timeEndModel));
            }
        }
    }

}
