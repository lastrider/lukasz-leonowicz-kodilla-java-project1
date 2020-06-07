package com.kodilla.StockTaking;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label label = new Label();
        label.setText(message);
        label.setFont(Font.font(20));

        Button yesButton = new Button("Yes");
        yesButton.setMinSize(60,20);

        Button noButton = new Button("No");
        noButton.setMinSize(60,20);

        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout,250,130);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}