package com.kodilla.StockTaking;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends Application {

    private String pathTeams;
    private String pathPNData;
    private String pathStockWM;
    private String pathUoM;
    private String ptahLanguage;
    private final Path pathSavedSettings;

    Stage window = new Stage();

    public AdminPanel(FileUploaderSavedData savedData) {
        this.pathTeams = savedData.getTeams();
        this.pathPNData = savedData.getPnData();
        this.pathStockWM = savedData.getWmStock();
        this.pathUoM = savedData.getUoM();
        this.ptahLanguage = savedData.getLanguage();
        this.pathSavedSettings = Paths.get(savedData.getSavedSettings());
    }

    @Override

    public void start(Stage primaryStage) {

    }

    public Scene getAdminPanel() {

        final FileChooser fileChooser = new FileChooser();

        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });

        TextField fieldTeams = new TextField(pathTeams);
        fieldTeams.setMinSize(400, 10);
        Button buttonTeams = new Button("Get Teams.");
        buttonTeams.setMinSize(150, 10);
        buttonTeams.setOnAction(event -> {
            try{
            pathTeams = fileChooser.showOpenDialog(window).getAbsolutePath();
            } catch (Exception e) {

            }
            fieldTeams.setText(pathTeams);
        });

        TextField fieldStockWM = new TextField(pathStockWM);
        fieldStockWM.setMinSize(400, 10);
        Button buttonStockWM = new Button("Get WM stock.");
        buttonStockWM.setMinSize(150, 10);
        buttonStockWM.setOnAction(event -> {
            try{
            pathStockWM = fileChooser.showOpenDialog(window).getAbsolutePath();
            } catch (Exception e) {

            }
            fieldStockWM.setText(pathStockWM);
        });

        TextField fieldPNData = new TextField(pathPNData);
        fieldPNData.setMinSize(400, 10);
        Button buttonPNData = new Button("Get PN data.");
        buttonPNData.setMinSize(150, 10);
        buttonPNData.setOnAction(event -> {
            try{
            pathPNData = fileChooser.showOpenDialog(window).getAbsolutePath();
            } catch (Exception e) {

            }
            fieldPNData.setText(pathPNData);
        });

        TextField fieldUom = new TextField(pathUoM);
        fieldUom.setMinSize(400, 10);
        Button buttonUoM = new Button("Get Unit of Measures.");
        buttonUoM.setMinSize(150, 10);
        buttonUoM.setOnAction(event -> {
            try {
                pathUoM = fileChooser.showOpenDialog(window).getAbsolutePath();
            } catch (Exception e) {

            }
            fieldUom.setText(pathUoM);
        });

        TextField fieldLanguage = new TextField(ptahLanguage);
        fieldLanguage.setMinSize(400, 10);
        Button buttonLanguage = new Button("Get extra language.");
        buttonLanguage.setMinSize(150, 10);
        buttonLanguage.setOnAction(event -> {
            try {
                ptahLanguage = fileChooser.showOpenDialog(window).getAbsolutePath();
            } catch (Exception e) {

            }
            fieldLanguage.setText(ptahLanguage);
        });

        Button buttonSaveSettings = new Button("Save settings");
        buttonSaveSettings.setMinSize(200, 20);
        buttonSaveSettings.setFont(Font.font(20));
        buttonSaveSettings.setOnAction(event -> {
            List<String> list = new ArrayList<>();
            list.add(pathTeams);
            list.add(pathStockWM);
            list.add(pathPNData);
            list.add(pathUoM);
            list.add(ptahLanguage);
            try {
                Files.write(pathSavedSettings,list, StandardCharsets.UTF_8,
                        Files.exists(pathSavedSettings) ? StandardOpenOption.WRITE : StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        GridPane pane1 = new GridPane();
        pane1.setPadding(new Insets(10, 10, 10, 10));
        pane1.setVgap(10);
        pane1.setHgap(8);

        pane1.add(fieldTeams, 1, 1);
        pane1.add(fieldStockWM, 1, 2);
        pane1.add(fieldPNData,1,3);
        pane1.add(fieldUom, 1, 4);
        pane1.add(fieldLanguage, 1, 5);

        pane1.add(buttonTeams, 2, 1);
        pane1.add(buttonStockWM, 2, 2);
        pane1.add(buttonPNData, 2, 3);
        pane1.add(buttonUoM, 2, 4);
        pane1.add(buttonLanguage, 2, 5);

        GridPane pane2 = new GridPane();
        pane2.setPadding(new Insets(10, 10, 10, 10));
        pane2.setVgap(10);
        pane2.setHgap(8);
        pane2.setAlignment(Pos.CENTER);
        pane2.add(buttonSaveSettings,1,1);

        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 30, 10));
        layout.getChildren().addAll(pane1, pane2);

        return new Scene(layout);
    }

    public void closeProgram() {
        boolean result = ConfirmBox.display("Close Alert", "Sure you want to exit?");

        if (result) {
            window.close();
        }
    }
}