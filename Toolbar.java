package org.example;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar{
    private boolean run = false;
    private MainView mainView;

    public Toolbar(MainView mainView){
        this.mainView = mainView;
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        Button pause = new Button("Pause");
        pause.setOnAction(this::handlePause);
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);
        Button end = new Button("End");
        end.setOnAction(this::handleEnd);

        this.getItems().addAll(start, pause, step, end);
    }

    private void handleEnd(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void handleStep(ActionEvent actionEvent) {
        if(!this.mainView.isPlaying()) {
            this.mainView.getSimulation().nextGen();
            mainView.draw();
        }
    }

    private void handlePause(ActionEvent actionEvent) {
        this.mainView.getPlay().stop();
        this.mainView.pause();
    }

    private void handleStart(ActionEvent actionEvent) {
       this.mainView.getPlay().start();
       this.mainView.play();
    }


}
