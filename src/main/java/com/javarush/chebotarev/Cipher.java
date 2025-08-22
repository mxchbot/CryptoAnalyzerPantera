package com.javarush.chebotarev;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Cipher {

    public void encrypt(BufferedReader fileReader, BufferedWriter fileWriter, int key) {
        encryptDecrypt(fileReader, fileWriter, key);
    }

    public void decrypt(BufferedReader fileReader, BufferedWriter fileWriter, int key) {
        encryptDecrypt(fileReader, fileWriter, -key);
    }

    private void encryptDecrypt(BufferedReader fileReader, BufferedWriter fileWriter, int key) {
        int value;
        char character;
        char alternate;
        int index;
        int offsetIndex;
        int alphabetLength = alphabet.getLength();
        int normalizedKey = key % alphabetLength;

        try {
            while ((value = fileReader.read()) > -1) {
                character = (char) value;
                if (alphabet.contains(character)) {
                    index = alphabet.getIndex(character);
                    offsetIndex = index + normalizedKey;
                    offsetIndex = (offsetIndex >= 0)
                            ? offsetIndex % alphabetLength
                            : offsetIndex + alphabetLength;
                    alternate = alphabet.getCharacter(offsetIndex);
                } else {
                    alternate = character;
                }

                fileWriter.write(alternate);
            }
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    Alphabet alphabet = Alphabet.getInstance();
}
