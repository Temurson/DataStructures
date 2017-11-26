import java.util.NoSuchElementException;

/**
 * Iterator over the collection of type E.
 */
public interface Iterator<E> {
    /**
     * Checks whether there are next elements in the collection.
     *
     * @return true if there is element in the collection, false otherwise
     */
    boolean hasNext();

    /**
     * Returns the next element in the collection.
     *
     * @return the next element if it exists
     * @throws NoSuchElementException if the element doesn't exist
     */
    E next() throws NoSuchElementException;
}
