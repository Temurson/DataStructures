import java.util.NoSuchElementException;

/**
 * Implementation of Map with Hashes.
 * Uses Quadratic Probing.
 *
 * @param <K> type of keys
 * @param <V> type of values
 */
class HashMap<K, V> implements Map<K, V> {
    ////////// PRIVATE FIELDS //////////
    private int size;
    private int capacity;
    private ArrayList<Entry<K, V>> data;
    private final Entry<K, V> dummy = new Entry<>(null, null);
    // we put dummy if the element is deleted
    private final double maxLoadFactor = 0.75;
    private int maxProbingSequenceLength;

    ////////// PUBLIC METHODS //////////

    /**
     * Constructs empty HashMap.
     */
    HashMap() {
        size = 0;
        data = new ArrayList<>();
        capacity = 16;
        data.resize(capacity);
        maxProbingSequenceLength = 0;
    }

    /**
     * Return number of entries in the HashMap.
     *
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Check whether the HashMap is empty.
     *
     * @return empty or not
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get value with specified key.
     *
     * @param k key to be found
     * @return element with given key or null if it doesn't exist
     */
    public V get(K k) {
        int index = findPlace(k);
        return (data.get(index) == null || data.get(index) == dummy) ? null : data.get(index).value;
    }

    /**
     * Put element with specified key and value and return old value.
     *
     * @param k key
     * @param v value
     * @return old value with specified key or null if it didn't exist
     */
    public V put(K k, V v) {
        int index = findPlace(k);
        V old = null;

        if (data.get(index) == null || data.get(index) == dummy) {
            data.set(index, new Entry<>(k, v));
            size++;
        }
        else {
            old = data.get(index).value;
            data.get(index).value = v;
        }

        resize();

        return old;
    }

    /**
     * Remove the element with specified key and return its value or null
     * if it didn't exist.
     *
     * @param k key
     * @return the removed value or null
     */
    public V remove(K k) {
        int index = findPlace(k);
        V removed = null;

        if (data.get(index) != null && data.get(index) != dummy) {
            removed = data.get(index).value;
            data.set(index, dummy);
            size--;
        }

        return removed;
    }

    /**
     * Return maximal probing sequence in HashMap.
     * Current state of the HashMap is considered, i.e.
     * if maximal probing sequence was longer before
     * rehash, it will be overridden by current one.
     *
     * @return length of maximal probing sequence
     */
    public int getMaxProbingSequenceLength() {
        return maxProbingSequenceLength;
    }

    /// VIEWS OF HASHMAP ///

    /**
     * Returns the set of entries of the HashMap.
     *
     * @return set of entries
     */
    public Set<Entry<K, V>> entrySet() {
        return new EntrySet(this);
    }

    /**
     * Non-mutable set of entries of the HashMap.
     */
    @SuppressWarnings("unchecked")
    private class EntrySet implements Set<Entry<K, V>> {
        final private HashMap<K, V> hashMap;

        /**
         * Constructs an EntrySet view of the given HashMap.
         *
         * @param current HashMap object which is represented by the set
         */
        private EntrySet(HashMap<K, V> current) {
            hashMap = current;
        }

        /**
         * Checks whether the set is empty.
         *
         * @return true if empty, false otherwise
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Returns the size of the set.
         *
         * @return size of the set
         */
        public int size() {
            return size;
        }

        /**
         * Returns an iterator over elements of the set.
         *
         * @return iterator over elements
         */
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator(hashMap);
        }

        /**
         * Checks whether the element is in the set.
         *
         * @param o object to be checked
         * @return true if contains, false otherwise
         */
        public boolean contains(Object o) {
            return get((K) o) != null;
        }
    }

    /**
     * Iterator over the entries of HashMap.
     */
    private class EntryIterator implements Iterator<Entry<K, V>> {
        final private HashMap<K, V> hashMap;
        private int currentIndex;

        /**
         * Constructs new iterator over the entries of HashMap.
         *
         * @param current corresponding HashMap
         */
        private EntryIterator(HashMap<K, V> current) {
            hashMap = current;
            currentIndex = 0;
        }

        /**
         * Checks whether there are next entries in the HashMap.
         *
         * @return true if there is entry in the HashMap, false otherwise
         */
        public boolean hasNext() {
            Entry<K, V> found = null;
            while (currentIndex < hashMap.capacity) {
                if (hashMap.data.get(currentIndex) != null && hashMap.data.get(currentIndex) != dummy) {
                    found = hashMap.data.get(currentIndex);
                    break;
                }
                currentIndex++;
            }

            return found != null;
        }

        /**
         * Returns the next entry in the HashMap.
         *
         * @return the next entry if it exists
         * @throws NoSuchElementException if the element doesn't exist
         */
        public Entry<K, V> next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return hashMap.data.get(currentIndex++);
        }
    }

    /**
     * Returns the set of keys of the HashMap.
     *
     * @return set of keys
     */
    public Set<K> keySet() {
        return new KeySet(this);
    }

    /**
     * Non-mutable set of keys of the HashMap.
     */
    @SuppressWarnings("unchecked")
    private class KeySet implements Set<K> {
        private final Set<Entry<K, V>> data;

        /**
         * Constructs a KeySet view of the given HashMap.
         *
         * @param current HashMap object which is represented by the set
         */
        private KeySet(HashMap<K, V> current) {
            data = current.entrySet();
        }

        /**
         * Checks whether the set is empty.
         *
         * @return true if empty, false otherwise
         */
        public boolean isEmpty() {
            return data.isEmpty();
        }

        /**
         * Returns the size of the set.
         *
         * @return size of the set
         */
        public int size() {
            return data.size();
        }

        /**
         * Returns an iterator over elements of the set.
         *
         * @return iterator over elements
         */
        public Iterator<K> iterator() {
            return new KeyIterator(data);
        }

        /**
         * Checks whether the element is in the set.
         *
         * @param o object to be checked
         * @return true if contains, false otherwise
         */
        public boolean contains(Object o) {
            return data.contains(o);
        }
    }

    /**
     * Iterator over the keys of HashMap.
     * Uses EntryIterator.
     */
    public class KeyIterator implements Iterator<K> {
        private final Iterator<Entry<K, V>> iterator;

        /**
         * Constructs new iterator over the entries of HashMap.
         *
         * @param data corresponding set of entries
         */
        private KeyIterator(Set<Entry<K, V>> data) {
            iterator = data.iterator();
        }

        /**
         * Checks whether there are next keys in the HashMap.
         *
         * @return true if there is key in the HashMap, false otherwise
         */
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * Returns the next key in the HashMap.
         *
         * @return the next key if it exists
         * @throws NoSuchElementException if the key doesn't exist
         */
        public K next() throws NoSuchElementException {
            return iterator.next().key;
        }
    }

    /**
     * Returns the collection of values in the HashMap.
     *
     * @return collection of values
     */
    public Collection<V> values() {
        return new ValueCollection(this);
    }

    /**
     * Non-mutable collection of values of the HashMap.
     */
    @SuppressWarnings("unchecked")
    private class ValueCollection implements Collection<V> {
        private final Set<Entry<K, V>> data;

        /**
         * Constructs a ValueCollection view of the given HashMap.
         *
         * @param current HashMap object which is represented by the set
         */
        private ValueCollection(HashMap<K, V> current) {
            data = current.entrySet();
        }

        /**
         * Checks whether the set is empty.
         *
         * @return true if empty, false otherwise
         */
        public boolean isEmpty() {
            return data.isEmpty();
        }

        /**
         * Returns the size of the set.
         *
         * @return size of the set
         */
        public int size() {
            return data.size();
        }

        /**
         * Returns an iterator over elements of the collection.
         *
         * @return iterator over elements
         */
        public Iterator<V> iterator() {
            return new ValueIterator(data);
        }
    }

    /**
     * Iterator over the values of HashMap.
     * Uses EntryIterator.
     */
    public class ValueIterator implements Iterator<V> {
        private final Iterator<Entry<K, V>> iterator;

        /**
         * Constructs new iterator over the values of HashMap.
         *
         * @param data corresponding set of entries
         */
        private ValueIterator(Set<Entry<K, V>> data) {
            iterator = data.iterator();
        }

        /**
         * Checks whether there are next values in the HashMap.
         *
         * @return true if there is value in the HashMap, false otherwise
         */
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * Returns the next value in the HashMap.
         *
         * @return the next value if it exists
         * @throws NoSuchElementException if the value doesn't exist
         */
        public V next() throws NoSuchElementException {
            return iterator.next().value;
        }
    }

    ////////// PRIVATE METHODS //////////

    /**
     * Compresses hashcode so that it can be used as index.
     *
     * @param hash hash value of the key
     * @return truncated hash
     */
    private int compress(int hash) {
        return Math.abs(hash % capacity);
    }

    /**
     * Finds entry with specified key.
     * Assumes quadratic probing implemented.
     *
     * @param key key to be searched
     * @return entry with specified key or null if it doesn't exist
     */
    private int findPlace(K key) {
        int index = compress(Hasher.getHashCode(key));

        int jump = 1;
        // online counting of probes
        int probesCount = 0;
        while (true) {
            if (data.get(index) == null) {
                maxProbingSequenceLength = Math.max(maxProbingSequenceLength, probesCount);
                return index;
            }

            if (jump > capacity && data.get(index) == dummy) {
                maxProbingSequenceLength = Math.max(maxProbingSequenceLength, probesCount);
                return index;
            }

            if (data.get(index) != dummy) {
                if (data.get(index).key.equals(key)) {
                    maxProbingSequenceLength = Math.max(maxProbingSequenceLength, probesCount);
                    return index;
                }
            }

            // This method showed to have better performance than
            // index = compress(initialIndex + jump^2)
            // while keeping to be quadratic.
            index = compress(index + jump*jump);
            jump++;
            probesCount++;
        }
    }

    /**
     * Increases size of the HashMap if loadFactor is too high.
     * This increases speed of access and preserve from overflow.
     */
    private void resize() {
        double loadFactor = size * 1.0 / capacity;

        if (loadFactor < maxLoadFactor) {
            return;
        }

        ArrayList<Entry<K, V>> oldData = data;
        data = new ArrayList<>();
        capacity *= 2;
        data.resize(capacity);
        int oldSize = size;
        maxProbingSequenceLength = 0;

        for (int i = 0; i < oldData.size(); i++) {
            if (oldData.get(i) != null && oldData.get(i) != dummy) {
                Entry<K, V> element = oldData.get(i);
                put(element.key, element.value);
            }
        }

        size = oldSize;
    }
}