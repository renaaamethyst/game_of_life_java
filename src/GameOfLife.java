import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class of the program PRESS "R" AT ANY TIME FOR SPECIAL EFFECTS
 */
public class GameOfLife extends Canvas implements Runnable {
	private static final long serialVersionUID = 7417043461517810865L;

	public static CellGrid boolGrid;
	public static CellGrid boolSeq;

	public static int boardLength = 50;// the with of the board in cells

	public static int[] popR; // used to populate the board
	public static int[] popC; // used to populate the board
	public static int generations = 0; // keep count of the number of
										// generations
	public static int frames = 0; // inform the framerate
	public int cellZoom = 10; // how many pixels each cell measures
	public double theta = 0; // rotation effect

	public static boolean cpuFree = false; // indicate when the program is ready
											// for a heavy calculation

	private boolean running = false; // if the thread is running
	private Thread thread; // main thread

	public static int WIDTH, HEIGHT; // dimensions of the window
	public static int COUNT = 0; // starting live-cell count, if inputed by user

	private BufferedImage preState = null; // contain the image of a prestate

	public static STATE state = STATE.GAME;

	final static String ESC = "\033["; // for clearing out stuff

	final static int DELAY = 50; // 100ms delay between renders

	Random rand = new Random(); // randomness

	/**
	 * Initializes the game sets the two sparse matrices, populates them, loads up a
	 * spawner for the initial screen
	 */
	public void init() {

		boolGrid = new CellGrid(HEIGHT, WIDTH);
		boolSeq = new CellGrid(HEIGHT, WIDTH);

		popR = new int[boardLength / 10];
		popC = new int[boardLength / 10];

		populate();

	}

	/**
	 * counts the number of neighbors each cell has
	 * 
	 * @param r  location of the cell
	 * @param c  location of the cell
	 * @param sm SparseMatrix<Byte> where the cell (r, c) is located
	 * @return return the number of neighbors, if any
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
	 * 
	 * @param sm
	 * @return the new generation of the the sm param
	 */
	public static CellGrid nextGen(CellGrid sm) {

		ArrayList<int[]> deadIndex = new ArrayList<>();
		CellGrid alive = sm.clone();

		for (int r = 0; r < sm.getR(); r++) {
			for (int c = 0; c < sm.getC(); c++) {
				byte current = countNeighbors(r, c, sm);
				if (sm.get(r, c) == false) {
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
		return alive;

	}

	/**
	 * populates the board with random cells
	 */
	public static void populate() {

		for (int i = 0; i < popR.length; i++) {
			if(COUNT > 0 && i == 0) {
				popR[i] = COUNT;
				popC[i] = COUNT;
			}
			popR[i] = (int) (Math.random() * (popR.length * 10 + 1));
			popC[i] = (int) (Math.random() * (popR.length * 10 + 1));
		}

		float ofset = 0.9f;

		for (int r = 0; r < HEIGHT / 2; r++) {
			for (int c = 0; c < WIDTH; c++) {
				if (Math.random() > ofset) {
					boolGrid.set(r, c, true);
				}
			}
		}
	}

	/**
	 * start the main thread
	 */
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * end the main thread
	 */
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
		}

	}

	/**
	 * Renders the cells
	 */
	private void render() {

		// System.out.print(ESC + "2J");
		System.out.println("\n @@@@@@@@@@ " + generations + " @@@@@@@@@@  \n ");
		System.out.println(boolGrid.toString());
		// boolGrid.toString();
	}

	/**
	 * Popular game loop that does math and calculates a framerate Calls render(); I
	 * did not write this method
	 */
	public void run() {
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				updates++;
				delta--;
			}
			cpuFree = true;
			render();

			/*
			 * if (state == STATE.START) startSeq = nextGen(startSeq);
			 */

			if (state == STATE.GAME) {
				// board = nextGen(board);
				boolGrid = nextGen(boolGrid);
				generations++;
				// this.sleep(100);
				try {
					this.thread.sleep(DELAY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;

				this.frames = frames;
				frames = 0;
				updates = 0;
			}
		}

	}

	/**
	 * Main Method
	 * 
	 * @param args which is a string array of arguments
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean correctNumH = false;
		boolean correctNumW = false;
		boolean correctIn = false;
		String in = "";
		String in2 = "";

		while (!correctIn) {
			System.out
					.println("Would you like to enter the dimensions(Y or N) (Default is Height = 25 and Width = 50)?");
			in = scan.next();
			if (in.equals("Y")) {
				while (!correctNumH) {
					System.out.print("Enter the height: ");
					try {
						HEIGHT = scan.nextInt();
						correctNumH = true;
					} catch (NumberFormatException ne) {
						System.out.println("Please enter a number!");
					}
					if (HEIGHT <= 0) {
						System.out.println("Please enter a positive number!");
						correctNumH = false;
					}
				}
				while (!correctNumW) {
					System.out.print("Enter the width: ");
					try {
						WIDTH = scan.nextInt();
						correctNumW = true;
					} catch (NumberFormatException ne) {
						System.out.println("Please enter a number!");
					}
					if (WIDTH <= 0) {
						System.out.println("Please enter a positive number!");
						correctNumW = false;
					}
				}
				correctIn = true;
			} else if (in.equals("N")) {
				WIDTH = boardLength;
				HEIGHT = boardLength / 2;
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
						COUNT = scan.nextInt();
						correctCount = true;
					} catch (NumberFormatException ne) {
						System.out.println("Please enter a number!");
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
				correctIn = true;
			} else {
				System.out.println("Please enter Y for yes and N for no!");
			}
		}
		new GameOfLife().start();
	}

}
