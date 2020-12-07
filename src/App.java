package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * JavaFX App
 */
public class App extends Application {
    ButtonType yes;
    ButtonType no;
    Button submit;
    Alert alert;
    int height;
    int width;
    int count;
    boolean check;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception{
       alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Dimensions");
        alert.setContentText("Would you like to input the dimensions?");
        yes = new ButtonType("Yes");
        no = new ButtonType("Randomize");
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes){
            Height();
            Width();
        } else if (result.get() == no) {
            height = (int) (Math.random()*50)+5;
            width = (int) (Math.random()*50)+5;
       }
        count = (int) ((Math.random()*(height*width-(height*width)/5)) + (height*width)/10);

        MainView mainView = new MainView(height, width, count);
        Scene scene = new Scene(mainView, 640, 480);
        stage.setScene(scene);
        stage.show();

        mainView.draw();
    }

    private void count() {
        Scene scene;
        Stage window = new Stage();
        window.setTitle("Number of Live cells");

        TextField h = new TextField();
        submit = new Button("Submit");
        submit.setOnAction(e -> ErrorChecking(h.getText()));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(h, submit);

        scene = new Scene(layout, 200, 200);
        window.setScene(scene);
        window.show();


        if(check == true){
            count = Integer.parseInt(h.getText());
            window.close();
        }
    }

    private void Width() {
        Scene scene;
        Stage window = new Stage();
        window.setTitle("Width");

        TextField h = new TextField();
        submit = new Button("Submit");
        submit.setOnAction(e -> ErrorChecking(h.getText()));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(h, submit);

        scene = new Scene(layout, 200, 200);
        window.setScene(scene);
        window.show();

        if(check == true){
            height = Integer.parseInt(h.getText());
        }
    }

    private int ErrorChecking(String l) {
        System.out.println(check + " here " + l);
        boolean correct = false;
        Alert a = new Alert(Alert.AlertType.ERROR);
        int num = 0;
        try {
            num = Integer.parseInt(l);
            correct = true;
        } catch (NumberFormatException ne) {
            EventHandler<ActionEvent> event1 = new
                    EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    a.setContentText("Enter a Positive Number!");
                    // show the dialog
                    a.show();
                }
            };
        }
        if (num <= 0) {
            EventHandler<ActionEvent> event1 = new
                    EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                            a.setContentText("Enter a Positive Number!");
                            // show the dialog
                            a.show();
                        }
                    };
        }
        return num;
    }

    public void Height(){
        Scene scene;
        Stage window = new Stage();
        window.setTitle("Height");

        TextField h = new TextField();
        submit = new Button("Submit");
        submit.setOnAction(e -> ErrorChecking(h.getText()));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(h, submit);

        scene = new Scene(layout, 200, 200);
        window.setScene(scene);
        window.show();

        if(check == true){
            height = Integer.parseInt(h.getText());
        }
    }
}