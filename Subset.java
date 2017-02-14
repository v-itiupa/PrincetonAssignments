/**
 * Created by v-itiupa on 12/9/2016.
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String input = StdIn.readString(); // use Ctrl + D to finish input
            rq.enqueue(input);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }

    }
}




