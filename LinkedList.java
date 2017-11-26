import java.util.NoSuchElementException;

/**
 * Class implementing Doubly Linked List.
 *
 * @param <E> type of the stored elements.
 */
class LinkedList<E> implements List<E> {
    // current size
    private int size;
    private Node header;
    private Node trailer;

    /**
     * Constructs empty linked list.
     */
    LinkedList() {
        header = new Node();
        trailer = new Node();

        header.next = trailer;
        trailer.prev = header;

        size = 0;
    }

    /**
     * Node element for Doubly Linked List.
     */
    private class Node {
        E el;
        Node next;
        Node prev;

        /**
         * Creates new node with el pointing to null.
         */
        Node() {
            this.el = null;
            next = null;
            prev = null;
        }

        /**
         * Creates new node with value of el.
         *
         * @param el value to be stored
         */
        Node(E el) {
            this.el = el;
            next = null;
            prev = null;
        }
    }

    /**
     * Return size of the list.
     *
     * @return size
     */
    public int size() {
        return this.size;
    }

    /**
     * Check whether the list is empty.
     *
     * @return empty or not
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Get i-th element.
     *
     * @param i index of the element
     *          from 0 to size-1
     * @return element at the index
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    public E get(int i) throws IndexOutOfBoundsException {
        return find(i).el;
    }

    /**
     * Set i-th element.
     *
     * @param i index of the element
     *          from 0 to size-1
     * @param e new value for the element
     * @return the replaced (old) element
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    public E set(int i, E e) throws IndexOutOfBoundsException {
        Node found = find(i);
        E old = found.el;
        found.el = e;
        return old;
    }

    /**
     * Add new element at index i.
     *
     * @param i index of the element
     *          from 0 to size
     * @param e element to be added
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    public void add(int i, E e) throws IndexOutOfBoundsException {
        Node found;
        if (i != this.size) {
            found = find(i);
        }
        else {
            found = this.trailer;
        }

        Node newNode = new Node(e);

        newNode.prev = found.prev;
        newNode.next = found;

        found.prev.next = newNode;
        found.prev = newNode;

        this.size++;
    }

    /**
     * Remove the element at index i.
     *
     * @param i index of the element
     *          from 0 to size-1
     * @return the removed element
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    public E remove(int i) throws IndexOutOfBoundsException {
        Node found = find(i);

        found.next.prev = found.prev;
        found.prev.next = found.next;

        this.size--;

        return found.el;
    }

    /**
     * Find the node at specified index.
     *
     * @param i index of the element
     *          from 0 to size-1
     * @return Node object at given index
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    private Node find(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException();
        }

        int current;
        Node res;

        // check where it is faster to go from
        // from the beginning
        if (i < this.size - i - 1) {
            current = 0;
            res = this.header.next;
            while (current != i) {
                res = res.next;
                current++;
            }
        }
        // from the end
        else {
            current = this.size - 1;
            res = this.trailer.prev;
            while (current != i) {
                res = res.prev;
                current--;
            }
        }

        return res;
    }

    /**
     * Returns an iterator over the elements of the LinkedList.
     *
     * @return an iterator
     */
    public Iterator<E> iterator() {
        return new LinkedListIterator(this);
    }

    /**
     * Iterator over the LinkedList.
     */
    private class LinkedListIterator implements Iterator<E> {
        final LinkedList<E> data;
        Node currentNode;

        /**
         * Constructs new iterator over the elements of LinkedList.
         *
         * @param current corresponding LinkedList
         */
        private LinkedListIterator(LinkedList<E> current) {
            data = current;
            currentNode = data.header;
        }

        /**
         * Checks whether there are next elements in the LinkedList.
         *
         * @return true if there is element in the LinkedList, false otherwise
         */
        public boolean hasNext() {
            return currentNode.next != null && currentNode.next != trailer;
        }

        /**
         * Returns the next element in the LinkedList.
         *
         * @return the next element if it exists
         * @throws NoSuchElementException if the element doesn't exist
         */
        @SuppressWarnings("unchecked")
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentNode = currentNode.next;
            return currentNode.el;
        }
    }
}