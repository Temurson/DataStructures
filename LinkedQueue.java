/**
 * Class implementing Queue with Doubly Linked List.
 *
 * @param <E> type of the stored elements.
 */
class LinkedQueue<E> implements Queue<E> {
    private LinkedList<E> list;

    /**
     * Constructs empty linked queue.
     */
    LinkedQueue() {
        list = new LinkedList<>();
    }

    /**
     * Return size of the queue.
     *
     * @return size
     */
    public int size() {
        return this.list.size();
    }

    /**
     * Check whether the queue is empty.
     *
     * @return empty or not
     */
    public boolean isEmpty() {
        return this.list.size() == 0;
    }

    /**
     * Get the element from the front.
     *
     * @return top element
     * @throws IndexOutOfBoundsException
     * if the queue is empty
     */
    public E first() throws IndexOutOfBoundsException {
        return this.list.get(0);
    }

    /**
     * Insert an element at the rear of the queue.
     *
     * @param e element to be inserted
     */
    public void enqueue(E e) {
        this.list.add(this.list.size(), e);
    }

    /**
     * Remove the element from the front.
     *
     * @throws IndexOutOfBoundsException
     * if the queue is empty
     */
    public E dequeue() throws IndexOutOfBoundsException {
        return this.list.remove(0);
    }
}