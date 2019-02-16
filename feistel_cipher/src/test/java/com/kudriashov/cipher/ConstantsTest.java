package com.kudriashov.cipher;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ConstantsTest {

    @Test
    public void sBoxes() {
        for (int i = 0; i < 0x10; i++) {
            for (int j = 0; j < 0x10; j++) {
                int tmp = Constants.S_BOX[i][j];
                int tmpInv = Constants.INV_S_BOX[tmp & 0b1111][tmp >> 4];
                assertEquals(i, tmpInv & 0b1111);
                assertEquals(j, tmpInv >> 4);
            }
        }
    }

    @Test
    public void symSBox() {
        for (int i = 0; i < 0x10; i++) {
            for (int j = 0; j < 0x10; j++) {
                int tmp = Constants.SYM_S_BOX[i][j];
                int tmpInv = Constants.SYM_S_BOX[tmp & 0b1111][tmp >> 4];
                assertEquals(i, tmpInv & 0b1111);
                assertEquals(j, tmpInv >> 4);
            }
        }
    }

}
