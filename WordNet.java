/**
 * Created by v-itiupa on 1/25/2017.
* Corner cases.  All methods and the constructor should throw a java.lang.NullPointerException if any argument is null.
* The constructor should throw a java.lang.IllegalArgumentException if the input does not correspond to a rooted DAG.
* The distance() and sap() methods should throw a java.lang.IllegalArgumentException unless both of the noun arguments are WordNet nouns.
*/

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.TreeMap;

public class WordNet {

    private Digraph G;
    private Topological T;
    private TreeMap<String, ArrayList<Integer>> nountoToSynsetMap;
    private SAP sap;
    private TreeMap<Integer, String> synsetIdToSynonymMap;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new java.lang.NullPointerException("Input parameter(s) are null(s)");
        }


        In inputSynsets = new In(synsets);
        In inputHypernyms = new In(hypernyms);

        String sline = null;
        String hline = null;

        nountoToSynsetMap = new TreeMap<String, ArrayList<Integer>>();
        synsetIdToSynonymMap = new TreeMap<Integer, String>();

        int synsetId = 0;


        while ((sline = inputSynsets.readLine()) != null)
        {
            String[] columns = sline.split(",");
            synsetId = Integer.parseInt(columns[0]);
            synsetIdToSynonymMap.put(synsetId, columns[1]);
            String[] subcolumns = columns[1].split(" ");
            SET<String> set = new SET<>();
            for (String noun : subcolumns) {
              //  System.out.println(noun);
                set.add(noun);
              //  System.out.println("added to set");
            }

            for (String noun: subcolumns) {
                ArrayList<Integer> val = new ArrayList<Integer>();


                if (nountoToSynsetMap.containsKey(noun))
                {

                    val = nountoToSynsetMap.get(noun);
                    val.add(synsetId);
                    nountoToSynsetMap.put(noun, val);
                }
                else
                {
                   // System.out.println("check key 2");
                    val.add(synsetId);
                    nountoToSynsetMap.put(noun, val);
                }

            }
        }

       // System.out.println(synsetId);
        if (synsetId != 0)
        {
        this.G = new Digraph(synsetId + 1);
        }

        this.sap = new SAP(G);

        while ((hline = inputHypernyms.readLine()) != null)
        {
            String[] col = hline.split(",");
            int v = Integer.parseInt(col[0]);
            for (int i = 1; i < col.length; i++)
            {
                G.addEdge(v, Integer.parseInt(col[i]));

            }
        }

        this.T = new Topological(G);
        if (!T.hasOrder()) {
            throw new java.lang.IllegalArgumentException("It is not a DAG");
        }

    }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
         return nountoToSynsetMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new java.lang.NullPointerException("Input parameter is null");
        }

        return nountoToSynsetMap.containsKey(word);

    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new java.lang.NullPointerException("Input parameter(s) are null(s)");
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new java.lang.IllegalArgumentException("Arguments are non WordNet nouns");
        }

        ArrayList<Integer> nounASynsetIds = nountoToSynsetMap.get(nounA);
        ArrayList<Integer> nounBSynsetIds = nountoToSynsetMap.get(nounB);

        return sap.length(nounASynsetIds, nounBSynsetIds);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new java.lang.NullPointerException("Input parameter(s) are null(s)");
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new java.lang.IllegalArgumentException("Arguments are non WordNet nouns");
        }

        ArrayList<Integer> nounASynsetIds = nountoToSynsetMap.get(nounA);
        // System.out.println(nounASynsetIds);
        ArrayList<Integer> nounBSynsetIds = nountoToSynsetMap.get(nounB);
        // System.out.println(nounBSynsetIds);
        int ancestorAB = sap.ancestor(nounASynsetIds, nounBSynsetIds);
        return synsetIdToSynonymMap.get(ancestorAB);
    }

    // do unit testing of this class
    public static void main(String[] args) {

        WordNet wn = new WordNet(args[0], args[1]);
        //System.out.println(wn.G.V());
        //System.out.println(wn.G.E());
        //System.out.println(wn.isNoun("a"));
        //System.out.println(wn.isNoun("b"));
        System.out.println("TEST");
        System.out.println(wn.distance("a","b"));

        System.out.println("END TEST");
        System.out.println(wn.sap("a","b"));
        /*
        for (String n: wn.nouns())
        {
            System.out.println(n);
        }
        */

    }
}
