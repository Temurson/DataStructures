/**
 * Node element for Doubly Linked List.
 *
 * @param <E> the type to be stored
 */
class Node<E> {
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