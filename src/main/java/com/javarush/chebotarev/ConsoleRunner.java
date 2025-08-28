package com.javarush.chebotarev;

import com.javarush.chebotarev.action.BruteForce;
import com.javarush.chebotarev.action.Decrypt;
import com.javarush.chebotarev.action.Encrypt;
import com.javarush.chebotarev.component.Alphabet;
import com.javarush.chebotarev.component.Console;
import com.javarush.chebotarev.component.ConsoleApp;

import java.util.Scanner;

public class ConsoleRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Console console = new Console(scanner);
        Alphabet alphabet = new Alphabet();
        Encrypt encrypt = new Encrypt(alphabet);
        Decrypt decrypt = new Decrypt(encrypt);
        BruteForce bruteForce = new BruteForce(decrypt, alphabet);
        ConsoleApp app = new ConsoleApp(console, encrypt, decrypt, bruteForce);
        app.run();
    }
}
