package com.javarush.chebotarev.action;

import java.io.BufferedWriter;
import java.io.RandomAccessFile;

public class Decrypt {

    public Decrypt(Encrypt encrypt) {
        this.encrypt = encrypt;
    }

    public void doAction(RandomAccessFile fileReader, BufferedWriter fileWriter, int key) {
        encrypt.doAction(fileReader, fileWriter, -key);
    }

    private final Encrypt encrypt;
}
