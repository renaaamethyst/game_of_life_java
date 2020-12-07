package org.example;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Height extends VBox {
    Button submit;
    int height;
    int width;

    public Height() throws Exception{
        TextField h = new TextField();

        submit = new Button("Submit");
        submit.setOnAction(e -> isValid(h.getText()));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(h, submit);
    }

    private boolean isValid(String text) {
        return true;
    }
}
