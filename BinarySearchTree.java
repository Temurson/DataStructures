import java.io.PrintWriter;
import java.util.ArrayDeque;

/**
 * Class that implements Binary Search Tree.
 * Allows only unique elements.
 *
 * @param <T> type of elements to be stored
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements Tree<T> {
    ///// PROTECTED FIELDS /////
    protected Node root;
    protected int size;
    protected PrintWriter out;

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
     * Constructs new Binary Search Tree.
     * Binds the object to standard output for printing.
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
        out = new PrintWriter(System.out);
    }

    /**
     * Constructs new Binary Search Tree.
     * Binds the object to provided PrintWriter for printing.
     *
     * @param writer where to print the tree
     */
    public BinarySearchTree(PrintWriter writer) {
        root = null;
        size = 0;
        out = writer;
    }

    /**
     * Find k and return it.
     * If not found, returns null.
     *
     * @param k element to be found
     * @return found element or null
     */
    public T find(T k) {
        Node found = findClosestNode(k);
        if (found == null)
            return null;
        return found.el == k ? k : null;
    }

    /**
     * Insert element k into the tree.
     *
     * @param k element to be inserted
     */
    public void insert(T k) {
        Node found = findClosestNode(k);

        // no nodes in the tree
        if (found == null) {
            root = new Node(k);
            size++;
            return;
        }

        // found exactly same node
        if (found.el.equals(k)) {
            return;
        }

        // create new node
        Node newNode = new Node(k);
        newNode.parent = found;
        size++;

        // add as left or right child of the closest node
        if (k.compareTo(found.el) < 0)
            found.left = newNode;
        else
            found.right = newNode;

        updateHeights(found);
    }

    /**
     * Remove k from the tree.
     *
     * @param k element to be removed
     */
    public void remove(T k) {
        Node found = findClosestNode(k);

        // nothing to remove is element is not found
        if (found == null)
            return;
        if (!found.el.equals(k))
            return;

        // node has no children
        if (found.left == null && found.right == null) {
            // node is left child
            if (found.parent.left != null)
                if (found.parent.left.el.equals(found.el))
                    found.parent.left = null;

            // node is right child
            if (found.parent.right != null)
                if (found.parent.right.el.equals(found.el))
                    found.parent.right = null;
        }
        // node has right child
        else if (found.left == null) {
            // node is left child
            if (found.parent.left != null)
                if (found.parent.left.el.equals(found.el))
                    found.parent.left = found.right;

            // node is right child
            if (found.parent.right != null)
                if (found.parent.right.el.equals(found.el))
                    found.parent.right = found.right;
        }
        // node has left child
        else if (found.right == null) {
            // node is left child
            if (found.parent.left != null)
                if (found.parent.left.el.equals(found.el))
                    found.parent.left = found.left;

            // node is right child
            if (found.parent.right != null)
                if (found.parent.right.el.equals(found.el))
                    found.parent.right = found.left;
        }
        // node has 2 children
        else {
            // replace the node with successor from inorder traversal

            Node successor = found.right;
            while (successor.left != null || successor.right != null) {
                successor = successor.left;
            }

            remove(successor.el);

            found.el = successor.el;

            // don't update heights, because it is already done in
            // remove(successor.el) above
            return;
        }

        updateHeights(found);
    }

    /**
     * Returns string with result of inorder traversal of the tree.
     *
     * @return string with inorder traversal
     */
    public String traverse() {
        return traverse(root);
    }

    /**
     * Prints the tree in form "parent leftNode rightNode" for all nodes.
     * Uses breadth first search to traverse the tree.
     */
    public void print() {
        ArrayDeque<Node> q = new ArrayDeque<>();
        q.add(root);

        out.print("BST:");

        while (!q.isEmpty()) {
            Node current = q.poll();

            if (current.left == null && current.right == null)
                continue;

            out.println();

            String res = "";
            res += current.toString();
            if (current.left != null) {
                q.add(current.left);
                res += " " + current.left.toString();
            }
            if (current.right != null) {
                q.add(current.right);
                res += " " + current.right.toString();
            }
            out.print(res);
        }
    }

    /**
     * Prints the mirrored tree in form "parent rightNode leftNode" for all nodes.
     * Uses breadth first search to traverse the tree.
     */
    public void mirror() {
        ArrayDeque<Node> q = new ArrayDeque<>();
        q.add(root);

        out.print("BSMT:");

        while (!q.isEmpty()) {
            Node current = q.poll();

            if (current.left == null && current.right == null)
                continue;

            out.println();

            String res = "";
            res += current.toString();
            if (current.right != null) {
                q.add(current.right);
                res += " " + current.right.toString();
            }
            if (current.left != null) {
                q.add(current.left);
                res += " " + current.left.toString();
            }

            out.print(res);
        }
    }

    ///// PROTECTED METHODS /////

    /**
     * Auxiliary method that returns the result of inorder traversal of subtree
     * with provided root.
     *
     * @param root root of the subtree
     * @return string with inorder traversal
     */
    protected String traverse(Node root) {
        if (root == null)
            return "";

        String result = "";

        String left = traverse(root.left);
        String right = traverse(root.right);

        if (!left.isEmpty())
            result += left + " ";

        result += root.toString();

        if (!right.isEmpty())
            result += " " + right;

        return result;
    }

    /**
     * Finds the node whose element is the closest to k.
     * If the tree is empty returns null, the node otherwise.
     * If there is node with element k then returns it.
     *
     * @param k element to find
     * @return closest node or null
     */
    protected Node findClosestNode(T k) {
        Node node = root;

        while (node != null) {
            // found element
            if (node.el.equals(k)) {
                return node;
            }

            // go left
            if (k.compareTo(node.el) < 0) {
                if (node.left == null)
                    return node;
                node = node.left;
            }
            // go right
            else {
                if (node.right == null)
                    return node;
                node = node.right;
            }
        }

        return null;
    }

    /**
     * Update heights of ancestors of start node.
     *
     * @param start node to start update
     */
    protected void updateHeights(Node start) {
        while (start != null) {
            int leftHeight = start.left == null ? 0 : start.left.height;
            int rightHeight = start.right == null ? 0 : start.right.height;
            start.height = Math.max(leftHeight, rightHeight) + 1;

            start = start.parent;
        }
    }

    ///// PRIVATE CLASSES /////

    /**
     * Auxiliary class for node of the tree.
     * Stores references to parent, left and right children of the node.
     * Also stores height of the node counted from the bottom.
     */
    protected class Node {
        T el;
        Node left;
        Node right;
        Node parent;
        int height;

        /**
         * Constructs empty node.
         */
        Node() {
            height = 0;
        }

        /**
         * Constructs new node.
         *
         * @param el element of the node
         */
        Node(T el) {
            this.el = el;
            this.height = 1;
        }

        /**
         * Returns string representation of the node.
         *
         * @return string view of the node
         */
        @Override
        public String toString() {
            return el.toString();
        }
    }
}
