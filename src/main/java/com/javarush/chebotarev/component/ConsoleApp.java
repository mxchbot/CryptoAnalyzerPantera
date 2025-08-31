package com.javarush.chebotarev.component;

import com.javarush.chebotarev.action.BruteForce;
import com.javarush.chebotarev.action.Decrypt;
import com.javarush.chebotarev.action.Encrypt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConsoleApp {

    public ConsoleApp(Console console, Encrypt encrypt, Decrypt decrypt, BruteForce bruteForce) {
        this.console = console;
        this.encrypt = encrypt;
        this.decrypt = decrypt;
        this.bruteForce = bruteForce;
    }

    public void run() {
        console.printGreetings();

        for (; ; ) {
            console.printMenu();

            Mode mode = obtainMode();
            if (mode == Mode.EXIT) {
                return;
            }
            doAction(mode);
        }
    }

    private void doAction(Mode mode) {
        String defaultInputFilepath;
        String defaultOutputFilepath;
        if (mode == Mode.ENCRYPT) {
            defaultInputFilepath = Const.DEFAULT_ENCRYPT_INPUT_FILEPATH;
            defaultOutputFilepath = Const.DEFAULT_ENCRYPT_OUTPUT_FILEPATH;
        } else {
            defaultInputFilepath = Const.DEFAULT_DECRYPT_INPUT_FILEPATH;
            defaultOutputFilepath = Const.DEFAULT_DECRYPT_OUTPUT_FILEPATH;
        }

        RandomAccessFile fileReader = obtainFileReader(defaultInputFilepath);
        BufferedWriter fileWriter = obtainFileWriter(defaultOutputFilepath);
        int key = 0;
        if (mode != Mode.BRUTEFORCE) {
            key = obtainKey();
        }

        switch (mode) {
            case ENCRYPT:
                encrypt.doAction(fileReader, fileWriter, key);
                console.printFileEncrypted();
                break;
            case DECRYPT:
                decrypt.doAction(fileReader, fileWriter, key);
                console.printFileDecrypted();
                break;
            case BRUTEFORCE:
                bruteForce.doAction(fileReader, fileWriter);
                console.printFileDecryptedByBruteForce();
        }

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
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
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
            } catch (FileNotFoundException e) {
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
            } catch (IOException e) {
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
            } catch (NumberFormatException e) {
                console.printInvalidKey();
                continue;
            }

            return result;
        }
    }

    private final Console console;
    private final Encrypt encrypt;
    private final Decrypt decrypt;
    private final BruteForce bruteForce;
}
