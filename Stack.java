/**
 *  Interface of Stack.
 *
 * @param <E> type of the stored elements
 */
interface Stack<E> {
    /**
     * Return size of the stack.
     *
     * @return size
     */
    int size();

    /**
     * Check whether the stack is empty.
     *
     * @return empty or not
     */
    boolean isEmpty();

    /**
     * Get the element from the front.
     *
     * @return top element
     */
    E top();

    /**
     * Insert an element at the rear of the stack.
     *
     * @param e element to be inserted
     */
    void push(E e);

    /**
     * Remove the element from the front.
     */
    E pop();
}