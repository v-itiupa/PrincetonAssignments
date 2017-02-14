/**
 * Created by v-itiupa on 12/8/2016.
 *
 * Corner cases. Throw a java.lang.NullPointerException if the client attempts to add a null item;
 * throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque;
 * throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
 * throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and
 * there are no more items to return.
 * Performance requirements.
 * Your deque implementation must support each deque operation in constant worst-case time.
 * A deque containing n items must use at most 48n + 192 bytes of memory.
 * and use space proportional to the number of items currently in the deque.
 * Additionally, your iterator implementation must support each operation (including construction)
 * in constant worst-case time.
 */
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    private Node<Item> first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;

    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

     // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }
    // return the number of items on the deque
    public int size() {
        return size;
    }



    // add the item to the front
    public void addFirst(Item item) {
        checkNull(item);

        Node<Item> newNode = new Node<Item>();
        newNode.item = item;

        // if there were no nodes before adding this
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            first.previous = newNode;
            newNode.next = first;
            first = newNode;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        checkNull(item);

        Node<Item> newNode = new Node<Item>();
        newNode.item = item;

        // if there were no nodes before adding this
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            newNode.previous = last;
            newNode.next = null;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("No such element");
        }
        Item value = first.item;
        if (first == last) {
            last = null;
        }
        else {
            first = first.next;
            first.previous = null;
        }
        size--;
        return value;
    }
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("No such element");
        }
        Item value = last.item;
        if (first == last) {
            first = null;
        }
        else {
            last.previous.next = null;
            last = last.previous;
        }
        size--;
        return value;
    }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // iterator
    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current = first;
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) {
            throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
/*
        Deque<String> d = new Deque<String> ();
        d.addFirst("one");
        d.addLast("two");
        d.addLast("three");
        d.addFirst("minusOne");

        System.out.println(d.size());
        System.out.println(d.removeFirst());
        System.out.println(d.removeLast());
        System.out.println(d.size());
        System.out.println(d.removeLast());


        for(String item : d) {
            System.out.println(item + " ");
        }
*/

    }

}