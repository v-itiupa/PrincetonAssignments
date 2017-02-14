/**
 * Created by v-itiupa on 1/27/2017.
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wn;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet)
    {
        this.wn = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns)
    {
        String result = null;
        int max_distance = 0;
        for (String A: nouns)
        {
            int distance = 0;

            for (String B: nouns)
            {
                if (!A.equals(B)) {
                    distance = distance + wn.distance(A, B);
                }
            }

            int temp_max = distance;
            if (temp_max > max_distance)
            {
                max_distance = temp_max;
                result = A;
            }

        }

        return result;
    }
    public static void main(String[] args)
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}