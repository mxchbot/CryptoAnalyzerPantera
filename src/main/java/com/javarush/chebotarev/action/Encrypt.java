package com.javarush.chebotarev.action;

import com.javarush.chebotarev.component.Alphabet;
import com.javarush.chebotarev.component.AppException;

import java.io.*;

public class Encrypt {

    public Encrypt(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public void doAction(RandomAccessFile fileReader, BufferedWriter fileWriter, int key) {
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

    private final Alphabet alphabet;
}
