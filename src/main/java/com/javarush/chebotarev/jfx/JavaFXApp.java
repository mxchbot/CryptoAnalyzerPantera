package com.javarush.chebotarev.jfx;

import com.javarush.chebotarev.action.BruteForce;
import com.javarush.chebotarev.action.Decrypt;
import com.javarush.chebotarev.action.Encrypt;
import com.javarush.chebotarev.component.Alphabet;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    public static void show(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Alphabet alphabet = new Alphabet();
        Encrypt encrypt = new Encrypt(alphabet);
        Decrypt decrypt = new Decrypt(encrypt);
        BruteForce bruteForce = new BruteForce(decrypt, alphabet);
        FXForm fxForm = new FXForm(stage, alphabet, encrypt, decrypt, bruteForce);
        fxForm.initialization();
    }
}
