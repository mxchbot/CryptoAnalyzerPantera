package com.javarush.khmelov.view.javafx;

import com.javarush.khmelov.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFxApplication extends Application {
    public static void show(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        MainController controller = new MainController();
        FxForm fxForm = new FxForm(stage, controller);
        fxForm.initialization();
    }
}
