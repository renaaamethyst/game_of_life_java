package org.example;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Play {

    private Timeline timeline;
    private MainView mainView;
    private Simulation simulation;

    public Play(MainView mainView, Simulation simulation) {
        this.mainView = mainView;
        this.simulation = simulation;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }
    /**
     * draws simulation generation by generation
     * @param actionEvent
     */
    private void doStep(ActionEvent actionEvent) {
        this.simulation.nextGen();
        this.mainView.draw();
    }
    /**
     * starts program
     */
    public void start() {
        this.timeline.play();
    }
    /**
     * stops program
     */
    public void stop() {
        this.timeline.stop();
    }
}
