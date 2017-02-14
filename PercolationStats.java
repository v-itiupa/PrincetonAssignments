import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
/**
 * Created by v-itiupa on 12/2/2016.
 */
public class PercolationStats {

    private double[] percolationThresholdPerExpNum;
    private int totalElementsCount;
    private int totalTrials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "Please check input parameters." +
                            "They should be greater than 0. " +
                            "You set grid size(n) to " + n + " " +
                            "and trials to " + trials);
        }

        totalElementsCount = n * n;
        totalTrials = trials;
        percolationThresholdPerExpNum = new double[totalTrials];

        for (int expNumber = 0; expNumber < trials; expNumber++) {

            Percolation p = new Percolation(n);
            int counter = 0;
            // open sites until system is percolates ans store count of opened site under index that corresponds to experiment number
            while (!p.percolates()) {
                int x = StdRandom.uniform(n) + 1;
                int y = StdRandom.uniform(n) + 1;
                if (!p.isOpen(x, y)) {
                    p.open(x, y);
                    counter++;
                }
            }
            percolationThresholdPerExpNum[expNumber] = (double) counter/totalElementsCount;

        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholdPerExpNum);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholdPerExpNum);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(totalTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(totalTrials);
    }

    // test client (described below)
    public static void main(String[] args) {

    }

}
