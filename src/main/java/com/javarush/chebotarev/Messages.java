package com.javarush.chebotarev;

public interface Messages {
    String GREETINGS = "-=Crypto Analyzer=-";
    String LINE = "-".repeat(20);
    String MENU = LINE + '\n' +
            """
            1. Encrypt
            2. Decrypt
            0. Exit
            """
            + LINE;
    String ENTER_MODE = "Please enter mode:";
    String INVALID_MODE = "You entered the invalid mode!";
}
