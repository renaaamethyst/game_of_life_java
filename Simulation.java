package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Simulation {
    public static CellGrid boolGrid;

    public static int boardLength = 50;// set to 150 // the with of the board in cells


    public static int generations = 0; // keep count of the number of
    // generations
    public static int frames = 0; // inform the framerate
    public int cellZoom = 10; // how many pixels each cell measures
    public double theta = 0; // rotation effect

    public static boolean cpuFree = false; // indicate when the program is ready
    // for a heavy calculation

    private static boolean running = false; // if the thread is running
    private Thread thread; // main thread

    public static int WIDTH, HEIGHT; // dimensions of the window
    public static int COUNT = 0; // starting live-cell count, if inputed by user


    // public static STATE state = STATE.START; // keeps the state of the program
    public static STATE state = STATE.GAME;

    final static String ESC = "\033["; // for clearing out stuff

    final static int DELAY = 50; // 100ms delay between renders

    Random rand = new Random(); // randomness


    public Simulation(int h, int w, int c) {
        WIDTH = w;
        HEIGHT = h;
        COUNT = c;
        boolGrid = new CellGrid(h, w);
        populate();
    }

    /**
     * counts the number of live neighbors each cell has
     * @param r  location of the cell
     * @param c  location of the cell
     * @param sm is the grid where the cell (r, c) is located
     * @return return the number of live neighbors, if any
     */
    public static byte countNeighbors(int r, int c, CellGrid sm) {
        byte neighbors = 0;

        if (r == 0 || c == 0 || r == sm.getR() - 1 || c == sm.getC() - 1) {
            for (int row = r - 1; row <= r + 1; row++) {
                for (int col = c - 1; col <= c + 1; col++) {
                    int curR = row;
                    int curC = col;

                    curR = (curR == -1) ? sm.getR() - 1 : curR;
                    curC = (curC == -1) ? sm.getC() - 1 : curC;
                    curR = (curR == sm.getR()) ? 0 : curR;
                    curC = (curC == sm.getC()) ? 0 : curC;

                    if (curR == r && curC == c)
                        continue;
                    if (sm.get(curR, curC) == false)
                        continue;
                    else
                        neighbors++;
                }
            }

        } else {
            for (int row = r - 1; row <= r + 1; row++) {
                for (int col = c - 1; col <= c + 1; col++) {
                    if (row == r && col == c)
                        continue;
                    if (sm.get(row, col) == false)
                        continue;
                    else
                        neighbors++;
                }
            }
        }
        return neighbors;
    }

    /**
     * Generates the next generation of cells
     * @return the new generation of sm
     */
    public static void nextGen() {
        ArrayList<int[]> deadIndex = new ArrayList<>();
        CellGrid alive = boolGrid.clone();

        for (int r = 0; r < boolGrid.getR(); r++) {
            for (int c = 0; c < boolGrid.getC(); c++) {
                byte current = countNeighbors(r, c, boolGrid);
                if (boolGrid.get(r, c) == false) {
                    if (current == (byte) 3) {

                        alive.set(r, c, true);
                    }
                } else {
                    if (current < (byte) 2 || current > (byte) 3)
                        deadIndex.add(new int[] { r, c });
                    else
                        alive.set(r, c, true);

                }

            }
        }
        for (int[] i : deadIndex) {
            alive.remove(i[0], i[1]);
        }
        boolGrid = alive.clone();
    }

    /**
     *Populates the board with the number of live cells the user chooses, or a random number
     */
    public static void populate() {
        while(COUNT > 0) {
            for (int r = 0; r < HEIGHT; r++) {
                for(int c = 0; c < WIDTH; c++) {
                    if(Math.random() > 0.95 && COUNT>0 && !boolGrid.full(r, c)) {
                        boolGrid.set(r, c, true);
                        COUNT = COUNT - 1;
                   }
                }
            }
        }
        for (int r = 0; r < HEIGHT; r++) {
            for(int c = 0; c < WIDTH; c++) {
                if(!boolGrid.full(r, c)) {
                    boolGrid.set(r, c, false);
                }
            }
        }
    }

    /**
     * Main Method
     * Checks for user input as well as exception handling and then calls the start of the grid once all the information has been filled out
     * @param args which is a string array of arguments
     */
    /*
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean correctNumH = false;
        boolean correctNumW = false;
        boolean correctIn = false;
        String in = "";
        String in2 = "";
        while (!correctIn) {
            System.out.println("Would you like to enter the dimensions(Y or N) (Default is using a randomly-generated dimensions of max 50 and min 5)?");
            in = scan.next();
            if (in.equals("Y")) {
                while (!correctNumH) {
                    System.out.print("Enter the height: ");
                    try {
                        HEIGHT = Integer.parseInt(scan.next());
                        correctNumH = true;
                    } catch (NumberFormatException ne) {
                        System.out.println("Please enter a number!");
                        continue;
                    }
                    if (HEIGHT <= 0) {
                        System.out.println("Please enter a positive number!");
                        correctNumH = false;
                    }
                }
                while (!correctNumW) {
                    System.out.print("Enter the width: ");
                    try {
                        WIDTH = Integer.parseInt(scan.next());
                        correctNumW = true;
                    } catch (NumberFormatException ne) {
                        System.out.println("Please enter a number!");
                        continue;
                    }
                    if (WIDTH <= 0) {
                        System.out.println("Please enter a positive number!");
                        correctNumW = false;
                    }
                }
                correctIn = true;
            } else if (in.equals("N")) {
                boardLength = (int) ((Math.random()*50) + 5);
                WIDTH = boardLength;
                boardLength = (int) ((Math.random()*50) + 5);
                HEIGHT = boardLength;
                correctIn = true;
            } else {
                System.out.println("Please enter Y for yes and N for no!");
            }
        }
        correctIn = false;
        boolean correctCount = false;
        while (!correctIn) {
            System.out.println(
                    "Would you like to enter the starting live-cell count?(Y or N) (Default is using a randomly-generated count.");
            in2 = scan.next();
            if (in2.equals("Y")) {
                while (!correctCount) {
                    System.out.print("Enter the starting live-cell count (Enter a number <= the grid area: " + (HEIGHT * WIDTH) + "): ");
                    try {
                        COUNT = Integer.parseInt(scan.next());
                        correctCount = true;
                    } catch (NumberFormatException ne) {
                        System.out.println("Please enter a number!");
                        continue;
                    }
                    if (COUNT <= 0) {
                        System.out.println("Please enter a positive number!");
                        correctCount = false;
                    }
                    if(COUNT > HEIGHT * WIDTH) {
                        System.out.println("Please enter a staring live-count that is less than or equal to the grid area (" + (HEIGHT * WIDTH) + "): ");
                        correctCount = false;
                    }
                }
                correctIn = true;
            } else if (in2.equals("N")) {
                COUNT = (int) ((Math.random()*(HEIGHT*WIDTH-(HEIGHT*WIDTH)/10)) + (HEIGHT*WIDTH)/10);
                correctIn = true;
            } else {
                System.out.println("Please enter Y for yes and N for no!");
            }
        }

    }

     */

}
