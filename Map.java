/**
 * Interface of Map.
 *
 * @param <K> type of keys
 * @param <V> type of values
 */
interface Map<K, V> {
    /**
     * Return size of the map.
     *
     * @return size
     */
    int size();

    /**
     * Check whether the map is empty.
     *
     * @return empty or not
     */
    boolean isEmpty();

    /**
     * Get value with specified key.
     *
     * @param k key to be found
     * @return element with given key or null if it doesn't exist
     */
    V get(K k);

    /**
     * Put element with specified key and value and return old value.
     *
     * @param k key
     * @param v value
     * @return old value with specified key or null if it didn't exist
     */
    V put(K k, V v);

    /**
     * Remove the element with specified key and return its value or null
     * if it didn't exist.
     *
     * @param k key
     * @return the removed value or null
     */
    V remove(K k);

    /**
     * Returns the set of keys.
     *
     * @return set of keys (unique)
     */
    Set<K> keySet();

    /**
     * Returns the collection of values.
     *
     * @return collection of values
     */
    Collection<V> values();

    /**
     * Returns the set of entries (keys and values).
     *
     * @return set of entries
     */
    Set<Entry<K, V>> entrySet();
}