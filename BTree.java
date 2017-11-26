import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that implements B-Tree.
 * Allows only unique elements.
 *
 * @param <T> type of elements to be stored
 */
public class BTree<T extends Comparable<? super T>> implements Tree<T> {
    ///// PRIVATE FIELDS /////
    private int size;
    private Node root;
    private int order;

    ///// PUBLIC METHODS /////

    /**
     * Get number of elements in the tree.
     *
     * @return number of elements in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Constructs new B-Tree of provided order.
     *
     * @param order order of B-Tree (>=2)
     * @throws IllegalArgumentException if order is less than 2
     */
    public BTree(int order) throws IllegalArgumentException {
        if (order < 2)
            throw new IllegalArgumentException("Minimal order of B-Tree is 2.");

        this.order = order;
        size = 0;
        root = new Node();
    }

    /**
     * Find k and return it.
     * If not found, returns null.
     *
     * @param k element to be found
     * @return found element or null
     */
    public T find(T k) {
        Node node = root;

        while (node != null) {
            // search for k in current node
            int index = Collections.binarySearch(node.data, k);

            // k found
            if (index >= 0)
                return k;
            // k not found and node is leaf
            if (node.isLeaf())
                return null;

            // k not found, go to the right child
            // see Collections.binarySearch documentation for details
            node = node.children.get(-(index + 1));
        }

        return null;
    }

    /**
     * Insert element k into the tree.
     *
     * @param k element to be inserted
     */
    public void insert(T k) {
        Node r = root;
        // check root for overflow
        if (r.size() == 2 * order - 1) {
            // create new root and connect it with old one
            Node s = new Node();
            root = s;
            s.children = new ArrayList<>();
            s.children.add(r);

            // split old root
            splitChild(s, 0);
            // insert element into it
            insertNonFull(s, k);
        }
        else
            // insert element into root in case no overflow occurred
            insertNonFull(r, k);
    }

    /**
     * Remove k from the tree.
     *
     * @param k element to be removed
     */
    public void remove(T k) {

    }

    /**
     * Returns string with result of inorder traversal of the tree.
     *
     * @return string with inorder traversal
     */
    public String traverse() {
        return traverse(root);
    }

    ///// PRIVATE METHODS /////

    /**
     * Returns string representing traversal of the B-Tree.
     *
     * @param x node to start traversal
     * @return result of traversal of x
     */
    private String traverse(Node x) {
        String res = "";

        if (!x.isLeaf())
            res += traverse(x.children.get(0)) + " ";

        for (int i = 1; i <= x.size(); i++) {
            if (i != 1)
                res += " ";
            res += x.data.get(i - 1).toString();
            if (!x.isLeaf())
                if (x.children.get(i) != null)
                    res += " " + traverse(x.children.get(i));
        }

        return res;
    }

    /**
     * Inserts given element into the node if it is a leaf. If not,
     * finds appropriate child, checks it for overflow, splits if necessary and
     * then inserts the element.
     *
     * @param x node to insert into
     * @param k element to insert
     */
    private void insertNonFull(Node x, T k) {
        // last index of the node array
        int i = x.size() - 1;

        if (x.isLeaf()) {
            // add into x if it is a leaf
            x.data.add(null);
            while (i >= 0 && k.compareTo(x.data.get(i)) < 0) {
                x.data.set(i + 1, x.data.get(i));
                i--;
            }
            x.data.set(i + 1, k);
        }
        else {
            // find appropriate place for insertion
            while (i >= 0 && k.compareTo(x.data.get(i)) < 0)
                i--;
            i++;

            // check right child for overflow
            if (x.children.get(i).size() == 2 * order - 1) {
                // split the child
                splitChild(x, i);
                if (k.compareTo(x.data.get(i)) > 0)
                    i++;
            }

            // insert the element into the child node
            insertNonFull(x.children.get(i), k);
        }
    }

    /**
     * Splits the node's i-th child into 2 nodes and adds
     * its median element into parent node x.
     * To be called in case of overflow in i-th child.
     *
     * @param x parent node
     * @param i index of child with overflow
     */
    private void splitChild(Node x, int i) {
        // create new node
        Node z = new Node();
        // get child node
        Node y = x.children.get(i);

        // copy second half of child node into new node
        for (int j = 0; j < order - 1; j++)
            z.data.add(y.data.get(j + order));
        if (!y.isLeaf()) {
            z.children = new ArrayList<>();
            for (int j = 0; j < order; j++)
                z.children.add(y.children.get(j + order));
        }

        // save the middle element
        T mid = y.data.get(order - 1);

        // delete second half of child node
        y.data.subList(order - 1, y.data.size()).clear();
        if (!y.isLeaf())
            y.children.subList(order, y.children.size()).clear();

        // add new child and new element into parent node
        x.data.add(null);
        x.children.add(null);
        for (int j = x.size() - 1; j >= i + 1; j--)
            x.children.set(j + 1, x.children.get(j));
        x.children.set(i + 1, z);
        for (int j = x.size() - 2; j >= i; j--)
            x.data.set(j + 1, x.data.get(j));
        x.data.set(i, mid);
    }

    ///// PRIVATE CLASSES /////

    /**
     * Auxiliary class for node of the B-Tree.
     */
    private class Node {
        ArrayList<T> data;
        ArrayList<Node> children;

        /**
         * Constructs empty node. Leaves children variable to be null since
         * leaves don't have any children.
         */
        private Node() {
            data = new ArrayList<>(order * 2 - 1);
        }

        /**
         * Check whether the node is a leaf.
         *
         * @return true if node is a leaf
         */
        private boolean isLeaf() {
            return children == null;
        }

        /**
         * Get the number of elements in this node.
         *
         * @return number of elements
         */
        private int size() {
            return data.size();
        }
    }
}
