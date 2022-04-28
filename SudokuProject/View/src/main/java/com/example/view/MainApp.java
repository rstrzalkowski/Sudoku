package com.example.view;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);


    @Override
    public void start(Stage stage) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("com.example.view.Bundle");
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class
                .getResource("FXMLs/scena.fxml"), bundle);
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Sudoku");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("Images/PL.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {


        logger.info("Starting app..");
        launch();
    }
}