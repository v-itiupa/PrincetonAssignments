import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/**
 * Created by v-itiupa on 1/27/2017.
 */
public class SAP {

    private Digraph G;

    private BreadthFirstDirectedPaths BDFP1;
    private BreadthFirstDirectedPaths BDFP2;


    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        this.G = new Digraph(G);

    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
        {
            throw new IndexOutOfBoundsException();
        }

        ArrayList<Integer> v1 = new ArrayList<>();
        v1.add(v);

        ArrayList<Integer> w1 = new ArrayList<>();
        w1.add(w);

        int[] resultLengthAncestor = findShortestDistAndAncestor(v1, w1);
        return resultLengthAncestor[0]; // return shortest path

    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)
    {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
        {
            throw new IndexOutOfBoundsException();
        }

        ArrayList<Integer> v1 = new ArrayList<>();
        v1.add(v);

        ArrayList<Integer> w1 = new ArrayList<>();
        w1.add(w);

        int[] resultLengthAncestor = findShortestDistAndAncestor(v1, w1);
        return resultLengthAncestor[1]; // return ancestor
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null || w == null) {
            throw new java.lang.NullPointerException("Input parameter(s) are null(s)");
        }

        for (int valuesV: v)
        {
            if (valuesV < 0 || valuesV >= G.V())
            {  throw new IndexOutOfBoundsException();
            }
        }
        for (int valuesW: w)
        {
            if (valuesW < 0 || valuesW >= G.V())
            {  throw new IndexOutOfBoundsException();
            }
        }

        int[] resultLengthAncestor = findShortestDistAndAncestor(v, w);
        return resultLengthAncestor[0]; // return shortest path

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null || w == null) {
            throw new java.lang.NullPointerException("Input parameter(s) are null(s)");
        }

        for (int valuesV: v)
        {
            if (valuesV < 0 || valuesV >= G.V())
            {  throw new IndexOutOfBoundsException();
            }
        }
        for (int valuesW: w)
        {
            if (valuesW < 0 || valuesW >= G.V())
            {  throw new IndexOutOfBoundsException();
            }
        }

        int[] resultLengthAncestor = findShortestDistAndAncestor(v, w);
        return resultLengthAncestor[1]; // return ancestor
    }




    private int[] findShortestDistAndAncestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        BDFP1 = new BreadthFirstDirectedPaths(this.G, v);
        BDFP2 = new BreadthFirstDirectedPaths(this.G, w);
        System.out.println(BDFP1.distTo(0) + " " + BDFP1.distTo(1));
        System.out.println(BDFP2.distTo(0) + " " + BDFP2.distTo(1));
        int ancestorVerticeId = -1;
        int totalVerticesCnt = G.V();
        int shortestLength = totalVerticesCnt + 1;

        for (int i = 0; i < totalVerticesCnt; i++)
        {
            if (BDFP1.hasPathTo(i) && BDFP2.hasPathTo(i))
            {

                int potentialMinDist = BDFP1.distTo(i) + BDFP2.distTo(i);
                System.out.println("Result: " + potentialMinDist);
                if (potentialMinDist < shortestLength)
                {
                    shortestLength = potentialMinDist;
                    ancestorVerticeId = i;
                }

            }
        }
        System.out.println("Result: " + shortestLength);

        if (shortestLength == totalVerticesCnt + 1)
            shortestLength = -1;
        int[] result = {shortestLength, ancestorVerticeId};
        return result;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();

            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}