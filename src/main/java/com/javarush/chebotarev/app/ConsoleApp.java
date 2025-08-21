package com.javarush.chebotarev.app;

import com.javarush.chebotarev.Console;
import com.javarush.chebotarev.Mode;

public class ConsoleApp {

    public static void main(String[] args) {
        ConsoleApp app = new ConsoleApp();
        app.run();
    }

    private void run() {
        console.printGreetings();

        for (;;) {
            console.printMenu();

            Mode mode = console.scanMode();

            switch (mode) {
                case ENCRYPT:
                    doEncrypt();
                    break;
                case DECRYPT:
                    doDecrypt();
                    break;
                case EXIT:
                    return;
            }
        }
    }

    private void doEncrypt() {

    }

    private void doDecrypt() {

    }

    Console console = new Console();
}
