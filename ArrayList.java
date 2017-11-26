import java.util.NoSuchElementException;

/**
 * Class implementing Array based List.
 *
 * @param <E> type of the stored elements.
 */
class ArrayList<E> implements List<E> {
    // current size
    private int size;
    private int capacity;
    private Object[] data;

    /**
     * Constructs empty array-based list.
     */
    ArrayList() {
        data = new Object[20];
        capacity = 20;
        size = 0;
    }

    /**
     * Return size of the list.
     *
     * @return size
     */
    public int size() {
        return this.size;
    }

    /**
     * Check whether the list is empty.
     *
     * @return empty or not
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Get i-th element.
     *
     * @param i index of the element
     *          from 0 to size-1
     * @return element at the index
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    @SuppressWarnings("unchecked")
    public E get(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException();
        }

        return (E) data[i];
    }

    /**
     * Set i-th element.
     *
     * @param i index of the element
     *          from 0 to size-1
     * @param e new value for the element
     * @return the replaced (old) element
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    @SuppressWarnings("unchecked")
    public E set(int i, E e) throws IndexOutOfBoundsException {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException();
        }

        data[i] = e;
        return (E) data[i];
    }

    /**
     * Add new element at index i.
     *
     * @param i index of the element
     *          from 0 to size
     * @param e element to be added
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    public void add(int i, E e) throws IndexOutOfBoundsException {
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException();
        }

        if (this.size + 1 > this.capacity) {
            Object[] new_data = new Object[this.capacity * 2];

            for (int j = 0; j < i; j++) {
                new_data[j] = this.data[j];
            }
            new_data[i] = e;
            for (int j = i + 1; j < this.data.length; j++) {
                new_data[j] = this.data[j - 1];
            }

            this.capacity *= 2;
            this.size++;
            this.data = new_data;
            return;
        }

        for (int j = i + 1; j < this.data.length; j++) {
            this.data[j] = this.data[j - 1];
        }
        this.data[i] = e;

        this.size++;
    }

    /**
     * Remove the element at index i.
     *
     * @param i index of the element
     *          from 0 to size-1
     * @return the removed element
     * @throws IndexOutOfBoundsException
     * if index is not in the list
     */
    @SuppressWarnings("unchecked")
    public E remove(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException();
        }

        E removed = (E) data[i];

        for (int j = i; j < this.data.length; j++) {
            this.data[j] = this.data[j + 1];
        }
        this.data[this.size - 1] = null;

        this.size--;

        return removed;
    }

    /**
     * Change the size of the ArrayList removing elements with
     * indexes less than newSize.
     *
     * @param newSize new size of the ArrayList
     */
    public void resize(int newSize) {
        if (this.size < newSize) {
            Object[] newData = new Object[newSize];

            for (int i = 0; i < this.size; i++) {
                newData[i] = this.data[i];
            }

            this.capacity = newSize;
            this.size = newSize;
            this.data = newData;
            return;
        }

        for (int i = newSize - 1; i < this.size; i++) {
            this.data[i] = null;
        }

        this.size = newSize;
    }

    /**
     * Returns an iterator over the elements of the ArrayList.
     *
     * @return an iterator
     */
    public Iterator<E> iterator() {
        return new ArrayListIterator(this);
    }

    /**
     * Iterator over the ArrayList.
     */
    private class ArrayListIterator implements Iterator<E> {
        ArrayList<E> data;
        int nextIndex;

        /**
         * Constructs new iterator over the elements of ArrayList.
         *
         * @param current corresponding ArrayList o
         */
        private ArrayListIterator(ArrayList<E> current) {
            nextIndex = 0;
            data = current;
        }

        /**
         * Checks whether there are next elements in the ArrayList.
         *
         * @return true if there is element in the ArrayList, false otherwise
         */
        public boolean hasNext() {
            return nextIndex < data.size;
        }

        /**
         * Returns the next element in the ArrayList.
         *
         * @return the next element if it exists
         * @throws NoSuchElementException if the element doesn't exist
         */
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return data.get(nextIndex++);
        }
    }
}
