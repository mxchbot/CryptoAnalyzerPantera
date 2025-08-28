package com.javarush.chebotarev.component;

public interface Messages {
    String GREETINGS = "-=Crypto Analyzer=-";
    String LINE = "-".repeat(20);
    String MENU = LINE + '\n' +
            """
            1. Encrypt
            2. Decrypt
            3. Brute-force
            0. Exit
            """
            + LINE;
    String ENTER_MODE = "Enter mode:";
    String INVALID_MODE = "You entered the invalid mode!";
    String ENTER_INPUT_FILEPATH_FORMAT = "Enter input filepath (absolute path OR relative path OR Enter for \"%s\"):\n";
    String ENTER_OUTPUT_FILEPATH_FORMAT = "Enter output filepath (absolute path OR relative path OR Enter for \"%s\"):\n";
    String FAILED_TO_OPEN_FILE_FOR_READING_FORMAT = "Failed to open file for reading: \"%s\"\n";
    String FAILED_TO_OPEN_FILE_FOR_WRITING_FORMAT = "Failed to open file for writing: \"%s\"\n";
    String ENTER_KEY = "Enter key:";
    String INVALID_KEY = "You entered an invalid key!";
    String FILE_ENCRYPTED = "File encrypted successfully!";
    String FILE_DECRYPTED = "File decrypted successfully!";
    String CURRENT_WORKING_DIRECTORY_FORMAT = "Current working directory: \"%s\"\n";
    String FILE_DECRYPTED_BY_BRUTE_FORCE = "File decrypted successfully by brute-force!";
}
