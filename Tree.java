/**
 * Interface for trees, namely Binary Search Tree,
 * AVL Tree and B-Tree.
 *
 * @param <T> type of elements to be stored
 */
public interface Tree<T extends Comparable<? super T>> {
    /**
     * Find k and return it.
     * If not found, returns null.
     *
     * @param k element to be found
     * @return found element or null
     */
    T find(T k);

    /**
     * Insert element k into the tree.
     *
     * @param k element to be inserted
     */
    void insert(T k);

    /**
     * Remove k from the tree.
     *
     * @param k element to be removed
     */
    void remove(T k);

    /**
     * Returns string with result of inorder traversal of the tree.
     *
     * @return string with inorder traversal
     */
    String traverse();
}
