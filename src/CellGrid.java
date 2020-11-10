import java.util.ArrayList;

public class CellGrid {
	
	//private ArrayList<Cell<anyType>> list;
	private boolean[][] grid;
	//private int numElements;
	private int numRows, numCols;
	//private char blank = '-';
	private static int TEMP = 0;
	
	public CellGrid(int r, int c) {
		grid = new boolean[r][c];
		//numElements = 0;
		numRows = r;
		numCols = c;
	}
	
	
	
	private boolean checkInRange(int r, int c) {
		if((r >= 0 && r < numRows) || (c >= 0 && c < numCols)) {
			return true;
		}
		return false;
	}

	public boolean get(int r, int c) {
		if(checkInRange(r, c))
			return grid[r][c];
		return false;
		// TODO Auto-generated method stub
		//return null;
	}

	public boolean set(int r, int c, boolean x) {
		// TODO Auto-generated method stub
		if(checkInRange(r,c)) {
			grid[r][c] = x;
			return true;
		}
		return false;
	}

	
	public void add(int r, int c, boolean x) {
		set(r,c,x);
		// TODO Auto-generated method stub
		
	}
	

	
	public boolean remove(int r, int c) {
		// TODO Auto-generated method stub
		boolean val = get(r,c);
		set(r,c,false);
		return val;
	}
	

	/*
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	*/

	public int numRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int numColumns() {
		// TODO Auto-generated method stub
		return numCols;
	}

	
	public CellGrid clone() {
		CellGrid newGrid = new CellGrid(this.getR(),this.getC());
		for(int r = 0; r < this.getR(); r++) {
			for(int c = 0; c < this.getC(); c++) {
				if(grid[r][c])
					newGrid.set(r, c, true);
				else
					newGrid.set(r, c, false);
			}
		}
		return newGrid;		
	}
	/*
	public boolean contains(Object x) {
		// TODO Auto-generated method stub
		return false;
	}
	*/

	/*
	public int[] getLocation(Object x) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	/*

	public Object[][] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	*/

	/*
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	*/

	public void clear() {
		// TODO Auto-generated method stub
		for(int r = 0; r < numRows; r ++) {
			for(int c = 0; c < numCols; c ++) {
				grid[r][c] = false;
			}
		}
	}

	/*
	public void setBlank(char blank) {
		// TODO Auto-generated method stub
		
	}
	*/
	public int getR() {
		return this.numRows;
	}

	public int getC() {
		return this.numCols;
	}
	
	public String toString() {
		String ans = "";
		for (int r = 0; r < numRows; r++) {
			ans += "#";
			for (int c = 0; c < numCols; c++) {
				//anyType val = this.get(r, c);
				/*
				if (val != null) {
					ans += val + " ";
				} else {
					ans += "  ";

				}
				*/
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
