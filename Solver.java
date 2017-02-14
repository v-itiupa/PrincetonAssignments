/**
 * Created by v-itiupa on 12/25/2016.
 */
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode solutionInitialPuzzle, solutionTwinPuzzle;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) throw new java.lang.NullPointerException("No board defined");

        MinPQ<SearchNode> minPQInitial, minPQTwin;

        if (initial.isGoal()) {
            solutionInitialPuzzle = new SearchNode(initial, null);
        }
        else {
            minPQInitial = new MinPQ<SearchNode>();
            minPQTwin = new MinPQ<SearchNode>();

            minPQInitial.insert(new SearchNode(initial, null));
            minPQTwin.insert(new SearchNode(initial.twin(), null));

            while (true) {

                solutionTwinPuzzle = makeMove(minPQTwin);

                if (solutionTwinPuzzle.board.isGoal()) {
                      solutionInitialPuzzle = null;
                    break;
                }

                solutionInitialPuzzle = makeMove(minPQInitial);
                if (solutionInitialPuzzle.board.isGoal()) {
                    break;
                }
            }
        }

    }

    private SearchNode makeMove(MinPQ<SearchNode> minPQ) {
        SearchNode currentBestNode = minPQ.delMin();
        for (Board neighbor : currentBestNode.board.neighbors()) {
            if ((currentBestNode.prevSearchNode == null) || (!neighbor.equals(currentBestNode.prevSearchNode.board))) {
                minPQ.insert(new SearchNode(neighbor, currentBestNode));
            }
        }
        return currentBestNode;
    }

    private class SearchNode implements Comparable<SearchNode> {

        private Board board;
        private SearchNode prevSearchNode;
        private int moves;
        private int priority;

        public SearchNode(Board board, SearchNode prevSearchNode) {

            this.board = board;
            this.prevSearchNode = prevSearchNode;

            if (this.prevSearchNode != null) {
                this.moves = prevSearchNode.moves + 1;
            } else
                this.moves = 0;

            this.priority = this.board.manhattan() + this.moves;

        }
        @Override
        public int compareTo(SearchNode o) {
            int diff = this.priority - o.priority;
            if (diff < 0) return -1;
            if (diff > 0) return 1;
            return 0;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        if (solutionInitialPuzzle == null) {
            return false;
        }
        return true;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return solutionInitialPuzzle.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        if (!isSolvable()) return null;

        Stack<Board> lastInsertedNode = new Stack<>();
        SearchNode currentSearchNode = solutionInitialPuzzle;

        while (currentSearchNode != null) {
            lastInsertedNode.push(currentSearchNode.board);
            currentSearchNode = currentSearchNode.prevSearchNode;
        }

        return lastInsertedNode;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }

    }
}
