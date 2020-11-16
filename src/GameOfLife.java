
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


/**
 * Main class of the program
 
 * PRESS "R" AT ANY TIME FOR SPECIAL EFFECTS
 */
public class GameOfLife extends Canvas implements Runnable {
	private static final long serialVersionUID = 7417043461517810865L;

	public static CellGrid boolGrid;
	public static CellGrid boolSeq;
	
	
	public static int boardLength = 50;// set to 150 // the with of the board in cells

	public static int[] popR; // used to populate the board
	public static int[] popC; // used to populate the board
	public static int generations = 0; // keep count of the number of
										// genenrations
	public static int frames = 0; // inform the framerate
	public int cellZoom = 10; // how many pixels each cell measures
	public double theta = 0; // rotation effect

	public static boolean cpuFree = false; // indicate when the program is ready
											// for a heavy calculation

	private boolean running = false; // if the thread is running
	private Thread thread; // main thread

	public static int WIDTH, HEIGHT; // dimensions of the window

	private BufferedImage preState = null; // contain the image of a prestate
	//public static STATE state = STATE.START; // keeps the state of the program
	public static STATE state = STATE.GAME;
	
	final static String ESC = "\033["; // for clearing out stuff
	
	final static int DELAY = 100; // 100ms delay between renders
	
	Random rand = new Random(); // randomness

	/**
	 * Initializes the game sets the two sparse matices, populates them loads up
	 * a spawner for the initial screen asigns mouse contol to different classes
	 */
	public void init() {
		
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		
		boolGrid = new CellGrid(boardLength / 2, boardLength);
		boolSeq = new CellGrid(boardLength / 2, boardLength);
		
		popR = new int[boardLength / 10];
		popC = new int[boardLength / 10];

		//********************
		/*
		for (int r = 0; r < 80; r++) {
			for (int c = 0; c < 80; c++) {
				if (startSeq.get(r, c) != null) {
					temp.add(c, r, (byte) 0);
				}
			}
		}
		*/
		//********************		
		populate();	
		
	}



	/**
	 * counts the number of neighboors each cell has
	 * 
	 * @param r
	 *            location of the cell
	 * @param c
	 *            location of the cell
	 * @param sm
	 *            SparseMatrix<Byte> where the cell (r, c) is located
	 * @return return the number of neighbors, if any
	 */
	public static byte coutnNeighbors(int r, int c, CellGrid sm) {
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
	 *            
	 * @return the new generaion of the the sm Param
	 */
	public static CellGrid nextGen(CellGrid  sm) {
		//SparseMatrix<Byte> alive = sm.clone();

		ArrayList<int[]> deadIndex = new ArrayList<>();		
		CellGrid alive = sm.clone();	
		
		for (int r = 0; r < sm.getR(); r++) {
			for (int c = 0; c < sm.getC(); c++) {
				byte current = coutnNeighbors(r, c, sm);
				if (sm.get(r, c) == false) {
					if (current == (byte) 3) {
						//alive.add(r, c, current);
						alive.set(r, c, true);
					}
				} else {
					if (current < (byte) 2 || current > (byte) 3)
						deadIndex.add(new int[] { r, c });
					else
						//alive.set(r, c, current);
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
			popR[i] = (int) (Math.random() * (popR.length * 10 + 1));
			popC[i] = (int) (Math.random() * (popR.length * 10 + 1));
		}
		float ofset = 0.9f;

		for (int r = 0; r < boardLength/2; r++) {
			//for (int c = 0; c < boardLength; c++) {
			for (int c = 0; c < boardLength; c++) {
				if (Math.random() > ofset) {
					//board.add(r, c, (byte) 0);
					boolGrid.set(r, c, true);					
				}				
			}
		}
	}



	/**
	 * subtitutes the board with a new board form an inamge
	 * 
	 * @param image
	 *            Buffered image contaiining a pattern. Only Black And White
	 */
	private static void loadPreState(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		System.out.println("width, height: " + w + " " + h);

		for (int xx = 0; xx < h; xx++) {
			for (int yy = 0; yy < w; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				if (red == 255 && green == 255 && blue == 255) {
					//board.add(xx, yy, (byte) 0);
				}
				if (red == 0 && green == 0 && blue == 255) {
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
	 * Pauses the game Un-used
	 */
	public static void pause() {
		state = STATE.PAUSE;

	}
	
	/**
	 * Renders the cells
	 */
	private void render() {

		//System.out.print(ESC + "2J"); 
		System.out.println("\n @@@@@@@@@@ " + generations + " @@@@@@@@@@  \n ");
		System.out.println(boolGrid.toString());
		//boolGrid.toString();
	}

	/**
	 * Popular game loop that does math and calculates a framerate Calls
	 * render(); I did not write this method
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
			if (state == STATE.START)
				startSeq = nextGen(startSeq);
			*/

			if (state == STATE.GAME) {
				//board = nextGen(board);
				boolGrid = nextGen(boolGrid);
				generations++;
				//this.sleep(100);
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
	 * @param args
	 */
	public static void main(String[] args) {
		//new Window(1500, 800, "CS Final 6/9/16", new GameOfLife());
		//new GameOfLife().init();
		new GameOfLife().start();
	}

}
