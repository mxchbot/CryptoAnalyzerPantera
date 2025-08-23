package com.javarush.chebotarev;

import java.io.*;

public class Cipher {

    public void encrypt(RandomAccessFile fileReader, BufferedWriter fileWriter, int key) {
        encryptDecrypt(fileReader, fileWriter, key);
    }

    public void decrypt(RandomAccessFile fileReader, BufferedWriter fileWriter, int key) {
        encryptDecrypt(fileReader, fileWriter, -key);
    }

    public void decryptByBruteForce(RandomAccessFile fileReader, BufferedWriter fileWriter) {
        int value;
        char character;
        int index;
        int[] charCounters = new int[alphabet.getLength()];
        try {
            BufferedReader bufferedFileReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(fileReader.getFD())
                            ));

            while ((value = bufferedFileReader.read()) > -1) {
                character = (char) value;
                index = alphabet.getIndex(character);
                if (index > -1) {
                    charCounters[index]++;
                }
            }

            fileReader.seek(0);
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }

        int maxCounter = 0;
        int maxCounterIndex = -1;
        for (int i = 0; i < charCounters.length; i++) {
            if (charCounters[i] > maxCounter) {
                maxCounter = charCounters[i];
                maxCounterIndex = i;
            }
        }

        int key = maxCounterIndex - alphabet.getIndex(' ');
        decrypt(fileReader, fileWriter, key);
    }

    private void encryptDecrypt(RandomAccessFile fileReader, BufferedWriter fileWriter, int key) {
        int value;
        char character;
        char altCharacter;
        int index;
        int offsetIndex;
        int alphabetLength = alphabet.getLength();
        int normalizedKey = key % alphabetLength;
        try {
            BufferedReader bufferedFileReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(fileReader.getFD())
                            ));

            while ((value = bufferedFileReader.read()) > -1) {
                character = (char) value;
                index = alphabet.getIndex(character);
                if (index > -1) {
                    offsetIndex = index + normalizedKey;
                    offsetIndex = (offsetIndex >= 0)
                            ? offsetIndex % alphabetLength
                            : offsetIndex + alphabetLength;
                    altCharacter = alphabet.getCharacter(offsetIndex);
                } else {
                    altCharacter = character;
                }

                fileWriter.write(altCharacter);
            }
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    Alphabet alphabet = Alphabet.getInstance();
}
