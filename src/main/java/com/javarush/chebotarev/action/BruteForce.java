package com.javarush.chebotarev.action;

import com.javarush.chebotarev.component.Alphabet;
import com.javarush.chebotarev.component.AppException;

import java.io.*;

public class BruteForce {

    public BruteForce(Decrypt decrypt, Alphabet alphabet) {
        this.decrypt = decrypt;
        this.alphabet = alphabet;
    }

    public void doAction(RandomAccessFile fileReader, BufferedWriter fileWriter) {
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
        decrypt.doAction(fileReader, fileWriter, key);
    }

    private final Decrypt decrypt;
    private final Alphabet alphabet;
}
