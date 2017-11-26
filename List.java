/**
 * Interface of List.
 *
 * @param <E> type of the stored elements
 */
interface List<E> extends Collection<E> {
    /**
     * Return size of the list.
     *
     * @return size
     */
    int size();

    /**
     * Check whether the list is empty.
     *
     * @return empty or not
     */
    boolean isEmpty();

    /**
     * Get i-th element.
     *
     * @param i index of the element
     * @return element at the index
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    E get(int i) throws IndexOutOfBoundsException;

    /**
     * Set i-th element.
     * @param i index of the element
     * @param e new value for the element
     * @return the inserted element
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    E set(int i, E e) throws IndexOutOfBoundsException;

    /**
     * Add new element at index i.
     *
     * @param i index of the element
     * @param e element to be added
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    void add(int i, E e) throws IndexOutOfBoundsException;

    /**
     * Remove the element at index i.
     *
     * @param i index of element to be removed.
     * @return the removed element
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    E remove(int i) throws IndexOutOfBoundsException;

    /**
     * Returns an iterator over the elements of the list.
     *
     * @return an iterator
     */
    Iterator<E> iterator();
}