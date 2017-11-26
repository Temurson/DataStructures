/**
 * Class implementing Stack with Doubly Linked List.
 *
 * @param <E> type of the stored elements.
 */
class LinkedStack<E> implements Stack<E> {
    private LinkedList<E> list;

    /**
     * Constructs empty linked stack.
     */
    LinkedStack() {
        list = new LinkedList<>();
    }

    /**
     * Return size of the stack.
     *
     * @return size
     */
    public int size() {
        return this.list.size();
    }

    /**
     * Check whether the stack is empty.
     *
     * @return empty or not
     */
    public boolean isEmpty() {
        return this.list.size() == 0;
    }

    /**
     * Get the element from the top.
     *
     * @return top element
     * @throws IndexOutOfBoundsException
     * if the stack is empty
     */
    public E top() throws IndexOutOfBoundsException {
        return this.list.get(0);
    }

    /**
     * Insert an element on the top of the stack.
     *
     * @param e element to be inserted
     */
    public void push(E e) {
        this.list.add(0, e);
    }

    /**
     * Remove the element from the top.
     *
     * @throws IndexOutOfBoundsException
     * if the stack is empty
     */
    public E pop() throws IndexOutOfBoundsException {
        return this.list.remove(0);
    }
}