import java.io.PrintWriter;

/**
 * Class implementing Adelson-Velski Landis Tree.
 * Allows only unique elements.
 *
 * @param <T> type to be stored
 */
public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    ///// PUBLIC METHODS /////

    /**
     * Constructs new AVL Tree.
     * Binds the object to standard output for printing.
     */
    public AVLTree() {
        super();
    }

    /**
     * Constructs new AVL Tree.
     * Binds the object to provided PrintWriter for printing.
     *
     * @param writer where to print the tree
     */
    public AVLTree(PrintWriter writer) {
        super(writer);
    }

    /**
     * Insert element k into the tree.
     *
     * @param k element to be inserted
     */
    @Override
    public void insert(T k) {
        super.insert(k);

        Node insertedNode = findClosestNode(k);

        restructure(findRestructureNode(insertedNode));
    }

    /**
     * Remove k from the tree.
     *
     * @param k element to be removed
     */
    @Override
    public void remove(T k) {
        super.remove(k);

        Node closestToDeleted = findClosestNode(k);

        restructure(findRestructureNode(closestToDeleted));
    }

    /**
     * Count number of elements less than k.
     *
     * @param k element to count
     * @return number of nodes
     */
    public Integer countLessThan(T k) {
        return countLessThan(root, k);
    }

    ///// PROTECTED METHODS /////

    /**
     * Count number of nodes whose elements are less than
     * provided value in subtree with root n.
     * Uses depth first search and BST properties.
     *
     * @param n root of the subtree
     * @param value value to compare with
     * @return size of the subtree
     */
    protected int countLessThan(Node n, T value) {
        if (n == null)
            return 0;

        int res = 0;
        if (n.el.compareTo(value) < 0) {
            res++;
            res += countLessThan(n.left, value);
            res += countLessThan(n.right, value);
        }
        else {
            res += countLessThan(n.left, value);
        }

        return res;
    }

    /**
     * Restructures the tree at node x.
     * Uses trinode restructure algorithm.
     *
     * @param x node to restructure
     */
    protected void restructure(Node x) {
        // tree is balanced, no need to restructure
        if (x == null)
            return;

        // z - unbalanced node
        // y - node with bigger height from z's children
        // x - node with bigger height from y's children

        Node y = x.parent;
        Node z = y.parent;

        // y's value is the middle one
        if ((x == y.right) == (y == z.right))
            rotate(y);
        // x's value is the middle one
        else {
            rotate(x);
            rotate(x);
        }
    }

    /**
     * Exchanges node x with its parent, handling all children they have.
     * Needed for trinode restructure algorithm.
     *
     * @param x node to be exchanged
     */
    protected void rotate(Node x) {
        Node y = x.parent;

        // no parent to exchange with
        if (y == null)
            return;

        x.parent = y.parent;
        y.parent = x;

        // x is left child
        if (y.left != null)
            if (y.left.el.equals(x.el)) {
                y.left = x.right;
                x.right = y;
            }

        // x is right child
        if (y.right != null)
            if (y.right.el.equals(x.el)) {
                y.right = x.left;
                x.left = y;
            }

        if (x.parent == null)
            root = x;
        else {
            if (x.parent.left != null)
                if (x.parent.left.el.equals(y.el))
                    x.parent.left = x;

            if (x.parent.right != null)
                if (x.parent.right.el.equals(y.el))
                    x.parent.right = x;
        }

        super.updateHeights(y);
    }

    /**
     * Finds first unbalanced node among node's ancestors and then
     * returns its grandchild with greatest height.
     * If there are no unbalanced nodes returns null.
     * Needed for trinode restructure algorithm.
     *
     * @param node node to find from
     * @return node to be restructured or null
     */
    protected Node findRestructureNode(Node node) {
        // tree is empty
        if (root == null)
            return null;
        // no parents, tree can't be unbalanced
        if (node.parent == null)
            return null;

        Node x = node;
        Node y = node.parent;
        Node z = node.parent.parent;

        while (z != null) {
            int leftHeight = z.left == null ? 0 : z.left.height;
            int rightHeight = z.right == null ? 0 : z.right.height;

            // heights differ by more than 1 -> tree is unbalanced
            if (Math.abs(leftHeight - rightHeight) > 1) {
                return x;
            }

            x = y;
            y = z;
            z = z.parent;
        }

        return null;
    }
}
