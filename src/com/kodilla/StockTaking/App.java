package com.kodilla.StockTaking;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    FileUploaderSavedData savedData = new FileUploaderSavedData("C:\\Users\\lleon\\Desktop\\JAVA\\Settings\\Settings.txt");  // plik ze ścieżkami do innych plikow
    private static String outcome = "C:\\Users\\lleon\\Desktop\\JAVA\\Settings\\Outcome.txt";                                     // ścieżka do zapisu pliku wynikowego
    private String team = "";
    private Stage window;

    public static String getOutcomePath() {
        return outcome;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        String user = System.getProperty("user.name");
        if (user.equals("lleon")) {                         // niezbędna podmiana użytkownika
            team = "Team06";                                // Reczne ustawienie zakresu  Team01 - Team08
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("Stock taking");

        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });

        window.setTitle("Stock taking application");

        Label label = new Label("Please, choose your role");
        label.setFont(new Font(16));

        Button buttonAdministration = new Button("Administrator");
        buttonAdministration.setMinSize(150, 40);
        buttonAdministration.setOnAction(event -> {
            AdminPanel adminPanel = new AdminPanel(savedData);
            Scene sceneAdminPanel = adminPanel.getAdminPanel();
            window.setScene(sceneAdminPanel);
        });

        Button buttonTeamLeader = new Button("Team Leader");
        buttonTeamLeader.setMinSize(150, 40);
        buttonTeamLeader.setOnAction(event -> {
            InventorySheet inventorySheet = new InventorySheet(savedData, team);
            Scene sceneInventorySheet = inventorySheet.getScene();
            window.setScene(sceneInventorySheet);
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, buttonAdministration, buttonTeamLeader);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 300);

        //sceneAdminPanel.getStylesheets().add("ThemeAdminPanel.css");

        window.setScene(scene);
        window.show();
    }

    public void closeProgram() {
        boolean result = ConfirmBox.display("Close Alert", "Sure you want to exit?");

        if (result) {
            window.close();
        }
    }
}