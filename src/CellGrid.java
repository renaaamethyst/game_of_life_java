package org.example;

import java.util.*;

public class CellGrid {
    private boolean[][] grid;
    private int numRows, numCols;
    private static int TEMP = 0;

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
     * Returns the cell at (r, c)
     * @param r is the row
     * @param c is the column
     * @return grid[r][c] if (r, c) is in range else return false
     */
    public boolean get(int r, int c) {
        if (checkInRange(r, c))
            return grid[r][c];
        return false;
    }

    /**
     * Sets the the location at (r, c)
     * @param r is the row
     * @param c is the column
     * @param x is whether the cell is dead or alive
     * @return true if the cell is successfully set, false otherwise
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
     * Sets the cell at (r, c) as dead or false
     * @param r is the row
     * @param c is the column
     * @return true if successfully removed, false otherwise
     */
    public boolean remove(int r, int c) {
        if(set(r, c, false)) return true;
        return false;
    }


    /**
     * Clones the cell grid and returns the clone
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

    public void clear() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                grid[r][c] = false;
            }
        }
    }

    /**
     * Getter method for rows
     * @return the number of rows
     */
    public int getR() {
        return this.numRows;
    }

    /**
     * Getter method for columns
     * @return the number fo columns
     */
    public int getC() {
        return this.numCols;
    }

    /**
     * Converts the grid to a string square "*" for live cells and " " for dead cells
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