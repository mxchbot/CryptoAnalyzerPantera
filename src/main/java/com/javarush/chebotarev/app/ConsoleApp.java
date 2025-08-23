package com.javarush.chebotarev.app;

import com.javarush.chebotarev.*;
import com.javarush.chebotarev.Console;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConsoleApp {

    public static void main(String[] args) {
        ConsoleApp app = new ConsoleApp();
        app.run();
    }

    private void run() {
        console.printGreetings();

        for (; ; ) {
            console.printMenu();

            Mode mode = obtainMode();
            switch (mode) {
                case ENCRYPT:
                    doEncrypt();
                    break;
                case DECRYPT:
                    doDecrypt();
                    break;
                case BRUTEFORCE:
                    doDecryptByBruteForce();
                    break;
                case EXIT:
                    return;
            }
        }
    }

    private void doEncrypt() {
        RandomAccessFile fileReader = obtainFileReader(DEFAULT_ENCRYPT_INPUT_FILEPATH);
        BufferedWriter fileWriter = obtainFileWriter(DEFAULT_ENCRYPT_OUTPUT_FILEPATH);
        int key = obtainKey();

        cipher.encrypt(fileReader, fileWriter, key);
        console.printFileEncrypted();

        try {
            fileReader.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    private void doDecrypt() {
        RandomAccessFile fileReader = obtainFileReader(DEFAULT_DECRYPT_INPUT_FILEPATH);
        BufferedWriter fileWriter = obtainFileWriter(DEFAULT_DECRYPT_OUTPUT_FILEPATH);
        int key = obtainKey();

        cipher.decrypt(fileReader, fileWriter, key);
        console.printFileDecrypted();

        try {
            fileReader.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    private void doDecryptByBruteForce() {
        RandomAccessFile fileReader = obtainFileReader(DEFAULT_DECRYPT_INPUT_FILEPATH);
        BufferedWriter fileWriter = obtainFileWriter(DEFAULT_DECRYPT_OUTPUT_FILEPATH);

        cipher.decryptByBruteForce(fileReader, fileWriter);
        console.printFileDecryptedByBruteForce();

        try {
            fileReader.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    private Mode obtainMode() {
        Mode result;
        int modeIndex;

        for (; ; ) {
            console.printEnterMode();

            try {
                modeIndex = Integer.parseInt(console.scanLine());
                result = Mode.values()[modeIndex];
            } catch (RuntimeException e) {
                console.printInvalidMode();
                continue;
            }

            return result;
        }
    }

    private RandomAccessFile obtainFileReader(String defaultInputFilepath) {
        RandomAccessFile result;
        String filepath;
        Path path;

        for (; ; ) {
            console.printCurrentWorkingDirectory();
            console.printEnterInputFilepath(defaultInputFilepath);

            filepath = console.scanLine();
            if (filepath.isEmpty()) {
                filepath = defaultInputFilepath;
            }
            path = Path.of(filepath);
            try {
                result = new RandomAccessFile(path.toFile(), "r");
            } catch (Exception e) {
                console.printFailedToOpenFileForReading(path);
                continue;
            }

            return result;
        }
    }

    private BufferedWriter obtainFileWriter(String defaultOutputFilepath) {
        BufferedWriter result;
        String filepath;
        Path path;

        for (; ; ) {
            console.printCurrentWorkingDirectory();
            console.printEnterOutputFilepath(defaultOutputFilepath);

            filepath = console.scanLine();
            if (filepath.isEmpty()) {
                filepath = defaultOutputFilepath;
            }
            path = Path.of(filepath);
            try {
                result = Files.newBufferedWriter(path);
            } catch (Exception e) {
                console.printFailedToOpenFileForWriting(path);
                continue;
            }

            return result;
        }
    }

    private int obtainKey() {
        int result;

        for (; ; ) {
            console.printEnterKey();

            try {
                result = Integer.parseInt(console.scanLine());
            } catch (RuntimeException e) {
                console.printInvalidKey();
                continue;
            }

            return result;
        }
    }

    private final Console console = new Console();
    private final Cipher cipher = new Cipher();
    private static final String DEFAULT_ENCRYPT_INPUT_FILEPATH = "./text/text.txt";
    private static final String DEFAULT_ENCRYPT_OUTPUT_FILEPATH = "./text/out.txt";
    private static final String DEFAULT_DECRYPT_INPUT_FILEPATH = DEFAULT_ENCRYPT_OUTPUT_FILEPATH;
    private static final String DEFAULT_DECRYPT_OUTPUT_FILEPATH = "./text/text2.txt";
}
