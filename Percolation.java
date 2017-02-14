/**
 Created by v-itiupa on 12/2/2016.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int rowElementsCount;
    private int totalElementsCount;
    private int reservedIndexForFirstRow, reservedIndexForLastRow;
    private WeightedQuickUnionUF oneDimRepresentation;


    // create n-by-n grid, with all sites blocked (set to 0 - means blocked)
    public Percolation(int n) {
        if (n <= 0) throw new java.lang.IllegalArgumentException("Check that array dimension is greater than 0");
        rowElementsCount = n;
        totalElementsCount = n * n;
        reservedIndexForFirstRow = totalElementsCount;
        reservedIndexForLastRow = totalElementsCount + 1;
        // last 2 indexes will store artificial root for elements in the first row and last row
        oneDimRepresentation = new WeightedQuickUnionUF(totalElementsCount + 2);

        grid = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
    }

    /*
    Get value in grid  using x,y coordinate.
    Note that input is X=[1..N],Y=[1..N].
    Array indexes starts from 0 but we will operate with all X,Y >=1
    */
    private int getXYValue(int row, int col) {
        return (row - 1) * rowElementsCount + (col - 1);
    }


    // open site (row, col) if it is not open already. Do connection with neighbors if they are open.
    public void open(int row, int col) {
        if (row < 1 || row > rowElementsCount || col < 1 || col > rowElementsCount) {
            throw new java.lang.IndexOutOfBoundsException("Open: Check that row and col are between 1 and " + rowElementsCount);
        }

        if (isOpen(row, col)) return;

        grid[row-1][col-1] = 1;

        int inputElementIndex = getXYValue(row, col);
        int topNeighboorIndex =  getXYValue(row - 1, col);
        int downNeighboorIndex = getXYValue(row + 1, col);
        int rightNeighboorIndex = getXYValue(row, col + 1);
        int leftNeighboorIndex = getXYValue(row, col - 1);

        if (row == 1) {
            oneDimRepresentation.union(inputElementIndex, reservedIndexForFirstRow);
        }

        if (row == rowElementsCount) {
            oneDimRepresentation.union(inputElementIndex, reservedIndexForLastRow);
        }

        if ((row + 1) <= rowElementsCount) {
            if (isOpen(row + 1, col))
                oneDimRepresentation.union(inputElementIndex, downNeighboorIndex);
        }
        if ((row - 1) >= 1) {
            if (isOpen(row - 1, col))
                oneDimRepresentation.union(inputElementIndex, topNeighboorIndex);
        }
        if ((col + 1) <= rowElementsCount) {
            if (isOpen(row, col + 1))
                oneDimRepresentation.union(inputElementIndex, rightNeighboorIndex);
        }
        if ((col - 1) >= 1) {
            if (isOpen(row, col - 1))
                oneDimRepresentation.union(inputElementIndex, leftNeighboorIndex);
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (row < 1 || row > rowElementsCount || col < 1 || col > rowElementsCount) {
            throw new IndexOutOfBoundsException("IsOpen: Check that row and col are between 1 and " + rowElementsCount);
        }

        return (grid[row-1][col-1] == 1);
    }

    // is site (row, col) full?
    // A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites.
    public boolean isFull(int row, int col) {
        if (row < 1 || row > rowElementsCount || col < 1 || col > rowElementsCount) {
            throw new IndexOutOfBoundsException("isFull: Check that row and col are between 1 and " + rowElementsCount);
        }
        return oneDimRepresentation.connected(getXYValue(row, col), reservedIndexForFirstRow);
    }

    // does the system percolate?
    // is there at least 1 full site at the last row?
    public boolean percolates() {
        return oneDimRepresentation.connected(reservedIndexForFirstRow, reservedIndexForLastRow);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
