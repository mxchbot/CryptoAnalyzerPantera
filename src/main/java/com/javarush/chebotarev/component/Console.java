package com.javarush.chebotarev.component;

import java.nio.file.Path;
import java.util.Scanner;

public class Console {

    public Console(Scanner scanner) {
        this.scanner = scanner;
    }

    public String scanLine() {
        return scanner.nextLine().trim();
    }

    public void printGreetings() {
        System.out.println(Const.GREETINGS);
    }

    public void printMenu() {
        System.out.println(Const.MENU);
    }

    public void printEnterMode() {
        System.out.println(Const.ENTER_MODE);
    }

    public void printInvalidMode() {
        System.out.println(Const.INVALID_MODE);
    }

    public void printEnterInputFilepath(String defaultInputFilepath) {
        System.out.printf(Const.ENTER_INPUT_FILEPATH_FORMAT, defaultInputFilepath);
    }

    public void printEnterOutputFilepath(String defaultOutputFilepath) {
        System.out.printf(Const.ENTER_OUTPUT_FILEPATH_FORMAT, defaultOutputFilepath);
    }

    public void printFailedToOpenFileForReading(Path path) {
        printFailedToOpenFile(path, Const.FAILED_TO_OPEN_FILE_FOR_READING_FORMAT);
    }

    public void printFailedToOpenFileForWriting(Path path) {
        printFailedToOpenFile(path, Const.FAILED_TO_OPEN_FILE_FOR_WRITING_FORMAT);
    }

    public void printEnterKey() {
        System.out.println(Const.ENTER_KEY);
    }

    public void printInvalidKey() {
        System.out.println(Const.INVALID_KEY);
    }

    public void printFileEncrypted() {
        System.out.println(Const.FILE_ENCRYPTED);
    }

    public void printFileDecrypted() {
        System.out.println(Const.FILE_DECRYPTED);
    }

    public void printFileDecryptedByBruteForce() {
        System.out.println(Const.FILE_DECRYPTED_BY_BRUTE_FORCE);
    }

    public void printCurrentWorkingDirectory() {
        Path path = Path.of("");
        Path absolutePath = path.toAbsolutePath();
        System.out.printf(Const.CURRENT_WORKING_DIRECTORY_FORMAT, absolutePath);
    }

    private void printFailedToOpenFile(Path path, String messageFormat) {
        Path absolutePath = path.toAbsolutePath();
        Path normalizedAbsolutePath = absolutePath.normalize();
        System.out.printf(messageFormat, normalizedAbsolutePath);
    }

    private final Scanner scanner;
}
