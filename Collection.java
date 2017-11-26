public interface Collection<E> extends Iterable<E> {
    /**
     * Returns the number of elements in the collection.
     *
     * @return number of elements
     */
    int size();

    /**
     * Checks whether the collection is empty
     *
     * @return true if empty, false otherwise
     */
    boolean isEmpty();
}