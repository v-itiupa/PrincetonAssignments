/**
 * Created by v-itiupa on 12/21/2016.
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class Board {

    private int dimension;
    private final int[][] board;
    private int zeroRow, zeroCol;
    private Queue<Board> neighbors;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)

    public Board(int[][] blocks) {

        dimension = blocks.length;
        board  = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = blocks[i][j];

                if (board[i][j] == 0)
                {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

    }
    // board dimension n
    public int dimension() {
        return dimension;
    }
    // number of blocks out of place

    public int hamming() {
        int hammingCnt = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] != j + i * dimension + 1 && board[i][j] != 0)
                {
                    hammingCnt = hammingCnt + 1;
                }
            }
        }
        return hammingCnt;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattanCnt = 0;
        int rowInGoal, colInGoal;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                int boardValue = board[i][j];
                if (boardValue != j + i * dimension + 1 && board[i][j] != 0) {
                    rowInGoal = Math.abs((boardValue - 1) / dimension);
                    if (boardValue <= dimension) rowInGoal = 0;
                    if (boardValue > dimension * dimension - dimension) rowInGoal = dimension - 1;
                    colInGoal = boardValue - 1  - rowInGoal * dimension;
                    if (colInGoal < 0) colInGoal = dimension + colInGoal;
                    manhattanCnt = manhattanCnt + Math.abs(i - rowInGoal) + Math.abs(j - colInGoal);
                }
            }
        }
        return manhattanCnt;
    }


    // is this board the goal board?
    public boolean isGoal() {
        return (manhattan() == 0);
    }


    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twin = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            twin[i] = board[i].clone();
        }

        if (dimension >= 2) {

            int i = 0;
            int j = 0;

            boolean controlCondition = true;

            while (controlCondition) {

                if (twin[i][j] != 0 && twin[i][j + 1] != 0 && i < dimension) {
                    int temp = twin[i][j];
                    twin[i][j] = twin[i][j + 1];
                    twin[i][j + 1] = temp;

                    controlCondition = false;
                }
                else {
                    if (i + 1 < dimension) {
                        i++;
                    }

                }

            }

        }

        return new Board(twin);
    }

    /*
    * Does this board equal y?
    * Two arrays are said to be equal if they are of same type,
    * has same length, contains same elements and in same order.
    * */
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.board, that.board);

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Iterable<Board> result = new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                if (neighbors == null) {
                    neighborsSearch();
                }
                return new BoardsIterator();
            }
        };
        return result;
    }

    private class BoardsIterator implements Iterator<Board> {

        @Override
        public boolean hasNext() {
            return (!neighbors.isEmpty());
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return neighbors.dequeue();
            }
            else {
                throw new NoSuchElementException("No neighbor found.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove method is not implemented");
        }
    }

    private void neighborsSearch() {
        neighbors = new Queue<Board>();
        Board leftNeighbor, rightNeighbor, topNeighbor, downNeighbor;

        // check all 4 neighbors
        if (zeroRow > 0) {
            topNeighbor = new Board(board);
            topNeighbor.board[zeroRow][zeroCol] = topNeighbor.board[zeroRow - 1][zeroCol];
            topNeighbor.board[zeroRow - 1][zeroCol] = 0;
            topNeighbor.zeroRow = zeroRow - 1;
            neighbors.enqueue(topNeighbor);

        }
        if (zeroRow < dimension - 1) {
            downNeighbor = new Board(board);
            downNeighbor.board[zeroRow][zeroCol] = downNeighbor.board[zeroRow + 1][zeroCol];
            downNeighbor.board[zeroRow + 1][zeroCol] = 0;
            downNeighbor.zeroRow = zeroRow + 1;
            neighbors.enqueue(downNeighbor);
        }

        if (zeroCol > 0) {
            leftNeighbor = new Board(board);
            leftNeighbor.board[zeroRow][zeroCol] = leftNeighbor.board[zeroRow][zeroCol - 1];
            leftNeighbor.board[zeroRow][zeroCol - 1] = 0;
            leftNeighbor.zeroCol = zeroCol - 1;
            neighbors.enqueue(leftNeighbor);
        }

        if (zeroCol < dimension - 1) {
            rightNeighbor = new Board(board);
            rightNeighbor.board[zeroRow][zeroCol] = rightNeighbor.board[zeroRow][zeroCol + 1];
            rightNeighbor.board[zeroRow][zeroCol + 1] = 0;
            rightNeighbor.zeroCol = zeroCol + 1;
            neighbors.enqueue(rightNeighbor);
        }
    }

    // string representation of this board (in the output format specified below)
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append(dimension());
        s.append("\n");
        for (int[] row : board) {
            for (int elem : row) {
                s.append(elem);
                s.append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

        /*
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Board initial2 = new Board(blocks);


        System.out.println(initial.manhattan());
        System.out.println(initial2.manhattan());
        System.out.println(initial.hamming());
        System.out.println(initial2.hamming());
        System.out.println("================");

        initial.board[1][1] = 16;
        System.out.println(initial.manhattan());
        System.out.println(initial2.manhattan());
        System.out.println(initial.hamming());
        System.out.println(initial2.hamming());

        System.out.println("================");

        System.out.println(initial.toString());
        System.out.println("================");
        System.out.println(initial.manhattan());
        System.out.println("================");
        System.out.println(initial2.toString());
        System.out.println("================");
        initial.board[1][1] = 16;
        System.out.println(initial.toString());
        System.out.println(initial2.toString());

        System.out.println(initial.hamming());
        System.out.println("================");
        System.out.println(initial.isGoal());
        System.out.println("================");
        // System.out.println(Arrays.deepToString(initial.goal).replace("], ", "]\n"));

        System.out.println("================");
        System.out.println(initial.equals(initial));

        int[][] newArr = new int[3][3];
        newArr[0][0] = 0;
        newArr[0][1] = 1;
        newArr[0][2] = 3;

        newArr[1][0] = 4;
        newArr[1][1] = 2;
        newArr[1][2] = 5;

        newArr[2][0] = 7;
        newArr[2][1] = 8;
        newArr[2][2] = 6;

        Board y = new Board(newArr);
        System.out.println(initial.equals(y));

        Board z = initial.twin();

        System.out.println(z.toString());


        Iterable<Board> ib = initial.neighbors();

        for(Board item : ib) {
            System.out.println(item.toString());
        }

        */

    }

}
