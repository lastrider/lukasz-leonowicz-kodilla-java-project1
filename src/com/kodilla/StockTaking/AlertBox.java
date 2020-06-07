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

public class AlertBox {
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(200);

        Label label = new Label();
        label.setText(message);
        label.setPadding(new Insets(10,10,30,10));
        label.setFont(Font.font(20));

        Button closeButton = new Button("Close the window");
        closeButton.setMinSize(5, 20);
        closeButton.setOnAction(event -> window.close());

        VBox layout = new VBox();
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}