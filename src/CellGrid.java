import java.util.*;

public class CellGrid {

	// private ArrayList<Cell<anyType>> list;
	private boolean[][] grid;
	// private int numElements;
	private int numRows, numCols;
	// private char blank = '-';
	private static int TEMP = 0;
	/**
	 * constructor that initializes grid and sets private data members to the values passed-in
	 * 
	 * @param r denoting rows 
	 * @param c denoting columns
	 */
	public CellGrid(int r, int c) {
		grid = new boolean[r][c];
		numRows = r;
		numCols = c;
	}

	/**
	 * checks if cell is in range
	 * 
	 * @param r denoting rows param c denoting columns
	 * @return true or false
	 */
	private boolean checkInRange(int r, int c) {
		if ((r >= 0 && r < numRows) || (c >= 0 && c < numCols)) {
			return true;
		}
		return false;
	}

	/**
	 * checks if parameters are in range of grid. If true, returns boolean value in 
	 * that position. Else, returns false.
	 * @param r denoting row
	 * @param c denoting column
	 * @return boolean value
	 */
	public boolean get(int r, int c) {
		if (checkInRange(r, c))
			return grid[r][c];
		return false;
	}

	/**
	 * checks if parameters are in range of grid. If true, sets grid position to 
	 * given value
	 * @param r denoting row
	 * @param c denoting column
	 * @param x denoting a boolean value
	 */
	public boolean set(int r, int c, boolean x) {
		// TODO Auto-generated method stub
		if (checkInRange(r, c)) {
			grid[r][c] = x;
			return true;
		}
		return false;
	}

	/**
	  * removes "cell" by changing boolean value to false
	 * @param r denoting row
	 * @param c denoting column
	 * @return removed boolean value
	 */
	public boolean remove(int r, int c) {
		boolean val = get(r, c);
		set(r, c, false);
		return val;
	}
	
	/**
	 * getter
	 * @return number of rows in grid
	 */
	public int getR() {
		return numRows;
	}

	/**
	 * getter
	 * @return number of columns in grid
	 */
	public int getC() {
		return numCols;
	}

	/**
	 * clones grid
	 * @return new grid
	 */
	public CellGrid clone() {
		CellGrid newGrid = new CellGrid(this.getR(), this.getC());
		for (int r = 0; r < this.getR(); r++) {
			for (int c = 0; c < this.getC(); c++) {
				if (grid[r][c])
					newGrid.set(r, c, true);
				else
					newGrid.set(r, c, false);
			}
		}
		return newGrid;
	}

	
	/**
	 * Checks the grid at (r, c) if its filled or not
	 * @param r is the row
	 * @param c is the column
	 * @return true if its filled, false if its not
	 */
	public boolean full(int r, int c) {
		if(grid[r][c]!=true) {
			return false;
		}else return true;
	}

	/**
	 * clears grid by making all values false
	 */
	public void clear() {
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				grid[r][c] = false;
			}
		}
	}

	/**
	 * returns grid
	 */
	public String toString() {
		String ans = "";
		for (int r = 0; r < numRows; r++) {
			ans += "#";
			for (int c = 0; c < numCols; c++) {

				if (grid[r][c]) {
					ans += "*" + " ";
				} else {
					ans += "  ";

				}
			}
			if (r + 1 == numRows)
				ans += "#";
			else
				ans += "#\n";
		}
		return ans;

	}

}
