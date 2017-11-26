/**
 * Interface of Set.
 * All elements are unique.
 *
 * @param <E> type of elements
 */
public interface Set<E> extends Collection<E> {
    /**
     * Return size of the set.
     *
     * @return size
     */
    int size();

    /**
     * Returns true if this set contains no elements.
     *
     * @return <tt>true</tt> if this set contains no elements
     */
    boolean isEmpty();

    /**
     * Returns true if the set contains specified element.
     *
     * @param o object to be checked
     * @return true if there is such element in the set, false otherwise.
     */
    boolean contains(Object o);

    /**
     * Returns an iterator over the elements of the set.
     *
     * @return iterator over elements
     */
    Iterator<E> iterator();
}