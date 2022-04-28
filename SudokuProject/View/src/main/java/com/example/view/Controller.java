package com.example.view;

import static java.lang.Integer.parseInt;

import com.example.view.exception.BuilderException;
import com.example.view.exception.FxmlNotFoundException;
import com.example.view.exception.InstanceCreationException;
import com.example.view.exception.LoadException;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.firstjava.BacktrackingSudokuSolver;
import pl.first.firstjava.Dao;
import pl.first.firstjava.Difficulty;
import pl.first.firstjava.PrototypeBoard;
import pl.first.firstjava.SudokuBoard;
import pl.first.firstjava.SudokuBoardDaoFactory;
import pl.first.firstjava.SudokuSolver;
import pl.first.firstjava.exception.DaoException;



public class Controller {

    private final Logger logger = LoggerFactory.getLogger(Controller.class.getName());

    private final SudokuSolver solver = new BacktrackingSudokuSolver();
    private final PrototypeBoard repo = new PrototypeBoard(solver);
    private final SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();

    private SudokuBoard plansza;
    private SudokuBoard original;

    StringConverter overridenConverter = new ModifiedStringConverter();

    private final ResourceBundle bundle = Objects.requireNonNull(
            ResourceBundle.getBundle("com.example.view.Bundle"));
    private final ResourceBundle authors = Objects.requireNonNull(
            ResourceBundle.getBundle("com.example.view.Authors"));

    private final JavaBeanIntegerPropertyBuilder builder = JavaBeanIntegerPropertyBuilder.create();

    private final Image plFlag = new Image(Objects.requireNonNull(getClass()
            .getResourceAsStream("Images/pl.png")));
    private final Image enFlag = new Image(Objects.requireNonNull(getClass()
            .getResourceAsStream("Images/EN.png")));
    private final JavaBeanIntegerProperty[][] fieldProperty = new JavaBeanIntegerProperty[9][9];

    @FXML
    TextField[][] cells = new TextField[9][9];

    @FXML
    Button buttonLoad;

    @FXML
    Button switchButton;

    @FXML
    Button buttonVerify;

    @FXML
    Button buttonSave;

    @FXML
    Button buttonReset;

    public void createScene(Difficulty diff, SudokuBoard sudokuBoard, boolean loaded) {

        makeButtons();

        GridPane board = new GridPane();
        diff.removePieces(sudokuBoard);
        if (!loaded) {
            try {
                this.original = sudokuBoard.clone();
            } catch (CloneNotSupportedException e) {
                logger.warn(new InstanceCreationException(
                        "key.instancecreationexc").getLocalizedMessage());
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new TextField();
                cells[i][j].setStyle("-fx-border-color: black");

                try {
                    fieldProperty[i][j] = builder
                            .bean(new FieldAdapter(sudokuBoard,i,j))
                            .name("FieldValue")
                            .build();
                } catch (NoSuchMethodException e) {
                    logger.warn(new BuilderException("key.builderexc").getLocalizedMessage());
                }

                cells[i][j].textProperty().bindBidirectional(
                        fieldProperty[i][j], overridenConverter);

                board.add(cells[i][j], i, j);

                int finalI = i;
                int finalJ = j;
                cells[i][j].textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        Pattern pattern = Pattern.compile("[1-9]");
                        Pattern pattern2 = Pattern.compile("[1-9]{2}");
                        Pattern pattern3 = Pattern.compile("[1-9][a-zA-Z]");
                        if (pattern.matcher(newValue).matches()) {
                            if (newValue.length() > 1) {
                                cells[finalI][finalJ].setText(oldValue);
                            }
                        } else if (pattern2.matcher(newValue).matches()) {
                            parseInt(newValue);
                            cells[finalI][finalJ].setText(Integer
                                    .toString(parseInt(newValue) % 10));
                        } else if (pattern3.matcher(newValue).matches()) {
                            cells[finalI][finalJ].setText(oldValue);
                        } else {
                            Platform.runLater(() -> {
                                cells[finalI][finalJ].clear();
                            });
                        }
                    }
                });

            }
        }
        configureTextFields(sudokuBoard);

        VBox vbox = new VBox(buttonReset,buttonVerify,buttonSave);
        HBox hbox = new HBox(board,vbox);
        Stage stage = new Stage();
        stage.setTitle("Sudoku");
        Scene scene = new Scene(hbox);
        stage.setScene(scene);

        buttonReset.setOnAction(value ->  {
            resetBoard();
        });


        buttonVerify.setOnAction(value ->  {
            if (sudokuBoard.checkBoard()) {
                buttonVerify.setText(bundle.getString("key.Good"));
                buttonVerify.setStyle("-fx-background-color: #73ea73;"
                        + " -fx-font-size: 15px;-fx-border-color: black;-fx-border-width: 1 1 1 4");
            } else {
                buttonVerify.setText(bundle.getString("key.Bad"));
                buttonVerify.setStyle("-fx-background-color: #bb2424; "
                        + "-fx-font-size: 15px;-fx-border-color: black;-fx-border-width: 1 1 1 4");
            }

        });

        buttonSave.setOnAction(value ->  {
            showSaveDialog();
        });



        stage.show();


    }


    public void createNewBoard(ActionEvent actionEvent) {
        try {
            plansza = repo.createInstance();
        } catch (CloneNotSupportedException e) {
            logger.warn(new InstanceCreationException(
                    "key.instancecreationexc").getLocalizedMessage());
        }
        plansza.solveGame();
        createScene(Difficulty.Testing,plansza,false);

    }

    public void createNewEasy(ActionEvent actionEvent) {
        try {
            plansza = repo.createInstance();
        } catch (CloneNotSupportedException e) {
            logger.warn(new InstanceCreationException(
                    "key.instancecreationexc").getLocalizedMessage());
        }
        plansza.solveGame();
        createScene(Difficulty.Easy,plansza,false);
    }

    public void createNewMedium(ActionEvent actionEvent) {
        try {
            plansza = repo.createInstance();
        } catch (CloneNotSupportedException e) {
            logger.warn(new InstanceCreationException(
                    "key.instancecreationexc").getLocalizedMessage());
        }
        plansza.solveGame();
        createScene(Difficulty.Medium,plansza,false);
    }

    public void createNewHard(ActionEvent actionEvent) {
        try {
            plansza = repo.createInstance();
        } catch (CloneNotSupportedException e) {
            logger.warn(new InstanceCreationException(
                    "key.instancecreationexc").getLocalizedMessage());
        }
        plansza.solveGame();
        createScene(Difficulty.Hard,plansza,false);
    }

    public void resetBoard() {
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                if (original.get(i,j) != plansza.get(i,j)) {
                    cells[i][j].clear();
                }
            }
        }
    }

    public void showSaveDialog() {
        TextField field = new TextField();
        Button button = new Button();
        button.setText(bundle.getString("key.Save"));


        field.setPrefHeight(50);
        field.setStyle("-fx-font-size: 25");
        button.setAlignment(Pos.CENTER);
        button.setPrefSize(400,225);
        button.setStyle("-fx-font-size: 30");
        VBox vbox = new VBox(field,button);
        Scene scene = new Scene(vbox,400, 250);
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("key.Save"));
        stage.setScene(scene);

        button.setOnAction(value ->  {
            if (field.getText() != "" && plansza != null && original != null) {
                saveToDatabase(field.getText(),plansza,original);
                Stage dialog = (Stage) button.getScene().getWindow();
                dialog.close();
            }
        });

        stage.show();
    }

    public void saveToDatabase(String name, SudokuBoard actual, SudokuBoard origin) {
        try {
            Dao<SudokuBoard> jdbcActual = factory.getJdbcDao(name);
            Dao<SudokuBoard> jdbcOriginal = factory.getJdbcDao(name + "_original");

            jdbcActual.write(this.plansza);
            jdbcOriginal.write(this.original);

        }  catch (DaoException e) {
            logger.warn(e.getLocalizedMessage());
        }

    }

    public void loadButtonClicked(ActionEvent event) throws LoadException {
        TextField field1 = new TextField();
        Button button = new Button();
        button.setText(bundle.getString("key.Load"));


        field1.setPrefHeight(50);
        field1.setStyle("-fx-font-size: 25");
        button.setAlignment(Pos.CENTER);
        button.setPrefSize(400,200);
        button.setStyle("-fx-font-size: 30");
        VBox vbox = new VBox(field1,button);
        Scene scene = new Scene(vbox,400, 250);
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("key.Load"));
        stage.setScene(scene);

        button.setOnAction(value ->  {
            {
                readFromDatabase(field1.getText());
                Stage dialog = (Stage) button.getScene().getWindow();
                createScene(Difficulty.Testing,plansza,true);
                dialog.close();
            }
        });

        stage.show();

    }

    public void readFromDatabase(String name) {
        try {
            Dao<SudokuBoard> jdbcActual = factory.getJdbcDao(name);
            Dao<SudokuBoard> jdbcOriginal = factory.getJdbcDao(name + "_original");

            plansza = jdbcActual.read();
            original = jdbcOriginal.read();

        }  catch (DaoException e) {
            logger.warn(e.getLocalizedMessage());
        }

    }

    public void switchLanguage(ActionEvent event) {
        ResourceBundle bun = bundle;
        Stage stage = new Stage();
        if (Locale.getDefault().getLanguage() == "pl") {
            Locale english = new Locale.Builder().setLanguage("en").build();
            bun = ResourceBundle.getBundle("com.example.view.Bundle", english);
            Locale.setDefault(english);
            stage.getIcons().add(enFlag);

        } else if (Locale.getDefault().getLanguage() == "en") {
            Locale polish = new Locale.Builder().setLanguage("pl").build();
            bun = ResourceBundle.getBundle("com.example.view.Bundle", polish);
            Locale.setDefault(polish);
            stage.getIcons().add(plFlag);
        }


        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("FXMLs/scena.fxml"), bun);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
        } catch (IOException e) {
            logger.warn(new FxmlNotFoundException(
                    "key.fxmlexc").getLocalizedMessage());
        }
        stage.setScene(scene);
        stage.setTitle("Sudoku");
        stage.show();
        Stage oldStage = (Stage) switchButton.getScene().getWindow();
        oldStage.close();

    }

    public void showAuthors(ActionEvent event) {
        TextField field1 = new TextField();
        field1.setEditable(false);
        field1.setAlignment(Pos.CENTER);
        field1.setPrefSize(180,120);
        field1.setStyle("-fx-font-size: 25px; -fx-font-family: Verdana");
        field1.setText(authors.getString("1."));

        TextField field2 = new TextField();
        field2.setEditable(false);
        field2.setAlignment(Pos.CENTER);
        field2.setPrefSize(180,120);
        field2.setStyle("-fx-font-size: 25px; -fx-font-family: Verdana");
        field2.setText(authors.getString("2."));

        VBox vbox = new VBox(field1,field2);

        Stage stage = new Stage();
        Scene scene = new Scene(vbox, 360, 240);
        stage.setScene(scene);
        stage.setTitle(authors.getString("Title"));
        stage.show();
    }

    private void makeButtons() {
        buttonVerify = new Button(bundle.getString("key.Check"));
        buttonVerify.setPrefSize(100,520);
        buttonVerify.setStyle(
                "-fx-font-size: 15px;-fx-border-color: black;-fx-border-width: 1 1 1 4");
        buttonVerify.setCursor(Cursor.HAND);

        buttonSave = new Button(bundle.getString("key.Save"));
        buttonSave.setPrefSize(100,100);
        buttonSave.setStyle("-fx-background-color: #bebed3; -fx-font-size: 15px;"
                + "-fx-border-color: black;-fx-border-width: 1 1 1 4");
        buttonSave.setCursor(Cursor.HAND);

        buttonReset = new Button(bundle.getString("key.Reset"));
        buttonReset.setPrefSize(100,100);
        buttonReset.setStyle("-fx-background-color: #bebed3; -fx-font-size: 15px;"
                + "-fx-border-color: black;-fx-border-width: 1 1 1 4");
        buttonReset.setCursor(Cursor.HAND);
    }

    private void configureTextFields(SudokuBoard sudokuBoard) {
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                if (sudokuBoard.get(i,j) != 0 && original.get(i,j) == sudokuBoard.get(i,j)) {
                    cells[i][j].setEditable(false);
                    cells[i][j].setStyle("-fx-background-color: #eaeaea; -fx-border-color: black");

                }
                if (original.get(i,j) != sudokuBoard.get(i,j)) {
                    cells[i][j].setEditable(true);
                    cells[i][j].setStyle("-fx-border-color: black");
                }
                if (sudokuBoard.get(i,j) == 0) {
                    cells[i][j].clear();
                }

                if (i % 3 == 0 && i != 0) {
                    if (sudokuBoard.get(i,j) == original.get(i,j) && sudokuBoard.get(i,j) != 0) {
                        cells[i][j].setStyle("-fx-background-color: #eaeaea;"
                                + " -fx-border-color: black; -fx-border-width: 1 1 1 5");
                    } else {
                        cells[i][j].setStyle("-fx-border-color: black; -fx-border-width: 1 1 1 5");
                    }
                }
                if (j % 3 == 0 && j != 0) {
                    if (sudokuBoard.get(i,j) == original.get(i,j) && sudokuBoard.get(i,j) != 0) {
                        cells[i][j].setStyle("-fx-background-color: #eaeaea; "
                                + "-fx-border-color: black; -fx-border-width: 5 1 1 1");
                    } else {
                        cells[i][j].setStyle("-fx-border-color: black; -fx-border-width: 5 1 1 1");
                    }
                }
                if (i % 3 == 0 && j % 3 == 0 && i != 0 && j != 0) {
                    if (sudokuBoard.get(i,j) == original.get(i,j) && sudokuBoard.get(i,j) != 0) {
                        cells[i][j].setStyle("-fx-background-color: #eaeaea;"
                                + " -fx-border-color: black; -fx-border-width: 5 1 1 5");
                    } else {
                        cells[i][j].setStyle("-fx-border-color: black; -fx-border-width: 5 1 1 5");
                    }
                }
                cells[i][j].setPrefSize(80,80);
                cells[i][j].setAlignment(Pos.CENTER);
                cells[i][j].setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            }
        }
    }


}