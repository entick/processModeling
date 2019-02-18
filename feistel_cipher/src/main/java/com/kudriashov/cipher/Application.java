package com.kudriashov.cipher;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Cipher feistelCipher = new FeistelCipherModel();
        System.out.print("Enter number 0 <= i < " + 0x10000 + ": ");
        Scanner scanner = new Scanner(System.in);
        int plaintext = scanner.nextInt();
        int ciphertext = feistelCipher.encrypt(plaintext, Constants.DEFAULT_KEY);
        System.out.println("Encrypted:" + ciphertext);
        System.out.println("-----------------------");
        System.out.println("Decrypted:" + feistelCipher.decrypt(ciphertext, Constants.DEFAULT_KEY));

    }

}