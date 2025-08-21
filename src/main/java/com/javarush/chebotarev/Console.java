package com.javarush.chebotarev;

import java.util.Scanner;

public class Console {

    public void printGreetings() {
        System.out.println(Messages.GREETINGS);
    }

    public void printMenu() {
        System.out.println(Messages.MENU);
    }

    public Mode scanMode() {
        int input;
        Mode mode;

        for (;;) {
            printEnterMode();

            try {
                input = scanner.nextInt();
            } catch (RuntimeException e) {
                printInvalidMode();
                scanner.nextLine();
                continue;
            }

            try {
                mode = Mode.values()[input];
            } catch (ArrayIndexOutOfBoundsException e) {
                printInvalidMode();
                continue;
            }

            return mode;
        }
    }

    private void printEnterMode() {
        System.out.println(Messages.ENTER_MODE);
    }

    private void printInvalidMode() {
        System.out.println(Messages.INVALID_MODE);
    }

    Scanner scanner = new Scanner(System.in);
}
