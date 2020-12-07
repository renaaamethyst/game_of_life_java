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
    /**
     * ends program
     * @param actionEvent
     */
    private void handleEnd(ActionEvent actionEvent) {
        System.exit(0);
    }
    /**
     * draws each generation
     * @param actionEvent
     */
    private void handleStep(ActionEvent actionEvent) {
        if(!this.mainView.isPlaying()) {
            this.mainView.getSimulation().nextGen();
            mainView.draw();
        }
    }
    /**
     * pauses simulation of cells dying and regenerating
     * @param actionEvent
     */
    private void handlePause(ActionEvent actionEvent) {
        this.mainView.getPlay().stop();
        this.mainView.pause();
    }
    /**
     * starts simulation of cells dying and regenerating
     * @param actionEvent
     */
    private void handleStart(ActionEvent actionEvent) {
       this.mainView.getPlay().start();
       this.mainView.play();
    }


}
