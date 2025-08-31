package com.javarush.chebotarev.jfx;

import com.javarush.chebotarev.action.BruteForce;
import com.javarush.chebotarev.action.Decrypt;
import com.javarush.chebotarev.action.Encrypt;
import com.javarush.chebotarev.component.Alphabet;
import com.javarush.chebotarev.component.AppException;
import com.javarush.chebotarev.component.Const;
import com.javarush.chebotarev.component.Mode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

public class FXForm {

    public FXForm(Stage stage, Alphabet alphabet, Encrypt encrypt, Decrypt decrypt, BruteForce bruteForce) {
        this.stage = stage;
        this.alphabet = alphabet;
        this.encrypt = encrypt;
        this.decrypt = decrypt;
        this.bruteForce = bruteForce;
        Scene scene = buildSceneAndAddActions();
        stage.setScene(scene);
        stage.setTitle(TITLE);
    }

    public void initialization() {
        stage.show();
    }

    private Scene buildSceneAndAddActions() {
        BorderPane pane = new BorderPane();
        pane.setTop(getTopNode());
        pane.setCenter(getCenterNode());
        pane.setBottom(getBottomNode());
        updateKeyLabel();
        return new Scene(pane, W_SIZE * SCALE, H_SIZE * SCALE);
    }

    private Node getTopNode() {
        GridPane gridPane = new GridPane();
        Button[] buttons = createTopPanelButtons();
        gridPane.setHgap(PADDING.getLeft());
        gridPane.setVgap(PADDING.getTop());
        gridPane.setPadding(PADDING);
        int rowIndex = 0;
        for (int colIndex = 0; colIndex < buttons.length; colIndex++) {
            gridPane.add(buttons[colIndex], colIndex, rowIndex);
        }

        keyLabel = new Label();
        key = new Slider(1, alphabet.getLength() - 1, 1);
        key.valueProperty().addListener(e -> updateKeyLabel());

        Label inputEncryptLabel = new Label(INPUT_ENCRYPT);
        inputEncrypt = new TextField();
        inputEncrypt.setText(getNormalizedAbsolutePath(Const.DEFAULT_ENCRYPT_INPUT_FILEPATH).toString());
        Label outputEncryptLabel = new Label(OUTPUT_ENCRYPT);
        outputEncrypt = new TextField();
        outputEncrypt.setText(getNormalizedAbsolutePath(Const.DEFAULT_ENCRYPT_OUTPUT_FILEPATH).toString());
        Label inputDecryptLabel = new Label(INPUT_DECRYPT);
        inputDecrypt = new TextField();
        inputDecrypt.setText(getNormalizedAbsolutePath(Const.DEFAULT_DECRYPT_INPUT_FILEPATH).toString());
        Label outputDecryptLabel = new Label(OUTPUT_DECRYPT);
        outputDecrypt = new TextField();
        outputDecrypt.setText(getNormalizedAbsolutePath(Const.DEFAULT_DECRYPT_OUTPUT_FILEPATH).toString());
        Label inputBruteForceLabel = new Label(INPUT_BRUTE_FORCE);
        inputBruteForce = new TextField();
        inputBruteForce.setText(getNormalizedAbsolutePath(Const.DEFAULT_ENCRYPT_OUTPUT_FILEPATH).toString());
        Label outputBruteForceLabel = new Label(OUTPUT_BRUTE_FORCE);
        outputBruteForce = new TextField();
        outputBruteForce.setText(getNormalizedAbsolutePath(Const.DEFAULT_DECRYPT_OUTPUT_FILEPATH).toString());

        VBox vBox = new VBox(gridPane,
                keyLabel,
                key,
                inputEncryptLabel,
                inputEncrypt,
                outputEncryptLabel,
                outputEncrypt,
                inputDecryptLabel,
                inputDecrypt,
                outputDecryptLabel,
                outputDecrypt,
                inputBruteForceLabel,
                inputBruteForce,
                outputBruteForceLabel,
                outputBruteForce);
        vBox.setPadding(PADDING);

        return vBox;
    }

    private Parent getCenterNode() {
        textArea = new TextArea();
        return textArea;
    }

    private Node getBottomNode() {
        message = new Label();
        message.setPadding(PADDING);
        message.setFont(Font.font(DEFAULT_FONT, FontWeight.BOLD, DEFAULT_FONT_SIZE));
        return message;
    }

    private Path getNormalizedAbsolutePath(String filepath) {
        return Path.of(filepath).toAbsolutePath().normalize();
    }

    private void updateKeyLabel() {
        keyLabel.setText("Key: " + getKey());
    }

    private String getKey() {
        return String.valueOf(getKeyValue());
    }

    private int getKeyValue() {
        return (int) Math.round(key.getValue());
    }

    private Button[] createTopPanelButtons() {
        Button original = createButton(ORIGINAL, e -> ShowOriginalFile());
        Button encode = createButton(ENCRYPT, e -> doAction(Mode.ENCRYPT));
        Button decode = createButton(DECRYPT, e -> doAction(Mode.DECRYPT));
        Button bruteForce = createButton(BRUTE_FORCE, e -> doAction(Mode.BRUTEFORCE));
        return new Button[]{original, encode, decode, bruteForce};
    }

    private Button createButton(String name, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(name);
        button.setOnAction(eventHandler);
        return button;
    }

    private void showTextFromFile(String filepath) {
        Path path = getNormalizedAbsolutePath(filepath);
        try {
            textArea.setText(Files.readString(path));
        } catch (IOException e) {
            String msg = String.format(Const.FAILED_TO_OPEN_FILE_FOR_READING_FORMAT, path);
            message.setText(msg);
            message.setTextFill(Color.RED);
        }
    }

    private void ShowOriginalFile() {
        showTextFromFile(inputEncrypt.getText());
        message.setText("");
    }

    private void doAction(Mode mode) {
        String inputFilepath;
        String outputFilepath;
        switch (mode) {
            case ENCRYPT:
                inputFilepath = inputEncrypt.getText();
                outputFilepath = outputEncrypt.getText();
                break;
            case DECRYPT:
                inputFilepath = inputDecrypt.getText();
                outputFilepath = outputDecrypt.getText();
                break;
            case BRUTEFORCE:
                inputFilepath = inputBruteForce.getText();
                outputFilepath = outputBruteForce.getText();
                break;
            default:
                return;
        }

        RandomAccessFile fileReader = obtainFileReader(inputFilepath);
        if (fileReader == null) {
            return;
        }

        BufferedWriter fileWriter = obtainFileWriter(outputFilepath);
        if (fileWriter == null) {
            try {
                fileReader.close();
            } catch (IOException e) {
                throw new AppException(e.getMessage(), e);
            }
            return;
        }

        String resultMessage = "";
        String resultFilepath = "";
        switch (mode) {
            case ENCRYPT:
                encrypt.doAction(fileReader, fileWriter, getKeyValue());
                resultMessage = Const.FILE_ENCRYPTED;
                resultFilepath = outputEncrypt.getText();
                break;
            case DECRYPT:
                decrypt.doAction(fileReader, fileWriter, getKeyValue());
                resultMessage = Const.FILE_DECRYPTED;
                resultFilepath = outputDecrypt.getText();
                break;
            case BRUTEFORCE:
                bruteForce.doAction(fileReader, fileWriter);
                resultMessage = Const.FILE_DECRYPTED_BY_BRUTE_FORCE;
                resultFilepath = outputBruteForce.getText();
                break;
        }

        message.setTextFill(Color.BLUE);
        message.setText(resultMessage);
        showTextFromFile(resultFilepath);

        try {
            fileReader.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    private RandomAccessFile obtainFileReader(String filepath) {
        RandomAccessFile result;
        Path path = Path.of(filepath);
        try {
            result = new RandomAccessFile(path.toFile(), "r");
        } catch (FileNotFoundException e) {
            String msg = String.format(Const.FAILED_TO_OPEN_FILE_FOR_READING_FORMAT, getNormalizedAbsolutePath(filepath));
            message.setText(msg);
            message.setTextFill(Color.RED);
            return null;
        }
        return result;
    }

    private BufferedWriter obtainFileWriter(String filepath) {
        BufferedWriter result;
        Path path = Path.of(filepath);
        try {
            result = Files.newBufferedWriter(path);
        } catch (IOException e) {
            String msg = String.format(Const.FAILED_TO_OPEN_FILE_FOR_WRITING_FORMAT, getNormalizedAbsolutePath(filepath));
            message.setText(msg);
            message.setTextFill(Color.RED);
            return null;
        }
        return result;
    }

    private static final String TITLE = Const.GREETINGS + " powered by Java FX";
    private static final String DEFAULT_FONT = "System";
    private static final String ORIGINAL = "Original";
    private static final String ENCRYPT = "Encrypt";
    private static final String DECRYPT = "Decrypt";
    private static final String BRUTE_FORCE = "Brute-force";
    private static final String INPUT_ENCRYPT = "Encryption input:";
    private static final String OUTPUT_ENCRYPT = "Encryption output:";
    private static final String INPUT_DECRYPT = "Decryption input:";
    private static final String OUTPUT_DECRYPT = "Decryption output:";
    private static final String INPUT_BRUTE_FORCE = "Brute-force input:";
    private static final String OUTPUT_BRUTE_FORCE = "Brute-force output:";

    private static final Insets PADDING = new Insets(5);
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final int W_SIZE = 16;
    private static final int H_SIZE = 15;
    private static final int SCALE = 50;

    private TextArea textArea;
    private Label message;
    private Slider key;
    private Label keyLabel;
    private TextField inputEncrypt;
    private TextField outputEncrypt;
    private TextField inputDecrypt;
    private TextField outputDecrypt;
    private TextField inputBruteForce;
    private TextField outputBruteForce;

    private final Stage stage;
    private final Alphabet alphabet;
    private final Encrypt encrypt;
    private final Decrypt decrypt;
    private final BruteForce bruteForce;
}
