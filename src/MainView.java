package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MainView extends VBox {
    private Canvas canvas;

    private Affine affine;
    private Play play;
    private Simulation simulation;
    private Simulation initial;
    boolean run = false;
    private boolean playing;

    public MainView(int height, int width, int count) {
        this.canvas = new Canvas(400, 400);

        Toolbar toolbar = new Toolbar(this);

        this.getChildren().addAll(toolbar, this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(400/height, 400/width);

        this.simulation = new Simulation(height, width, count);
        this.play = new Play(this, this.simulation);
    }

    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

      //  g.setFill(Color.GRAY);
       // g.fillRect(0, 0, 450, 450);

        g.setFill(Color.RED);
        for (int x = 0; x < this.simulation.HEIGHT; x++) {
            for (int y = 0; y < this.simulation.WIDTH; y++) {
                if (simulation.boolGrid.get(x, y)) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }

        g.setFill(Color.GRAY);
        for (int x = 0; x < this.simulation.HEIGHT; x++) {
            for (int y = 0; y < this.simulation.WIDTH; y++) {
                if (!simulation.boolGrid.get(x, y)) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }

        g.setStroke(Color.BLACK);
        g.setLineWidth(0.05);
        for (int x = 0; x <= this.simulation.HEIGHT; x++) {
            g.strokeLine(x, 0, x, this.simulation.WIDTH);
        }

        for (int y = 0; y <= this.simulation.WIDTH; y++) {
            g.strokeLine(0, y, this.simulation.HEIGHT, y);
        }

    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public Play getPlay() {
        return this.play;
    }

    public void play(){
        playing = true;
    }

    public void pause(){
        playing = false;
    }

    public boolean isPlaying(){
        return playing;
    }
}
