/**
 * Created by v-itiupa on 12/10/2016.
 */


import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rq;
    private int totalItems = 0; // number of items

    // construct an empty randomized queue
    public RandomizedQueue() {
        rq = (Item[]) new Object[1];
    }
    // is the queue empty?
    public boolean isEmpty() {
        return totalItems == 0;
    }
    // return the number of items on the queue
    public int size() {
        return totalItems;
    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < rq.length; i++)
            if (rq[i] != null)
            copy[i] = rq[i];

        rq = copy;
    }
    // add the item
    public void enqueue(Item item) {
        checkNull(item);
        if (totalItems == rq.length) resize(2 * rq.length);
        rq[totalItems] = item;
        totalItems++;

    }
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int random = StdRandom.uniform(totalItems);
        Item item = rq[random];
        totalItems--;
        rq[random] = rq[totalItems];
        rq[totalItems] = null;
        if (totalItems > 0 && totalItems == rq.length/4) resize(rq.length/2);
        return item;

    }
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int random = StdRandom.uniform(totalItems);

        return rq[random];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {


        private int shuffledN, currentN;
        private final Item[] shuffled;

        public RandomIterator() {
            shuffled = (Item[]) new Object[size()];
            shuffledN = 0;
            currentN = 0;

            for (Item item : rq) {
                if (item != null) {
                    shuffled[shuffledN++] = item;
                }
            }

            StdRandom.shuffle(shuffled);
        }

        @Override
        public boolean hasNext() {
            return currentN < shuffledN;

        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            // return sample();
            return shuffled[currentN++];

        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    public static void main(String[] args) {
    /*
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        for (int i = 0; i < 7; ++i) {
            q.enqueue(StdRandom.uniform(7));
        }
        System.out.println(q.size());
        q.dequeue();
        System.out.println(q.size());
        q.dequeue();
        System.out.println(q.size());

        Iterator<Integer> orderOne = q.iterator();
        Iterator<Integer> orderTwo = q.iterator();

        while (orderOne.hasNext() && q.N<=5 ) {
            System.out.println(orderOne.next());
        }

        while (orderTwo.hasNext() && q.N<= ) {
            System.out.println(orderTwo.next());
        }
    */

    }
}