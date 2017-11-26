/**
 *  Interface of Queue.
 *
 * @param <E> type of the stored elements
 */
interface Queue<E> {
    /**
     * Return size of the queue.
     *
     * @return size
     */
    int size();

    /**
     * Check whether the queue is empty.
     *
     * @return empty or not
     */
    boolean isEmpty();

    /**
     * Get the element from the front.
     *
     * @return top element
     */
    E first();

    /**
     * Insert an element at the rear of the queue.
     *
     * @param e element to be inserted
     */
    void enqueue(E e);

    /**
     * Remove the element from the front.
     */
    E dequeue();
}