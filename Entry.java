/**
 * Class simulating entry of a Map.
 *
 * @param <K> key
 * @param <V> value
 */
class Entry<K, V> {
    K key;
    V value;

    /**
     * Constructs new Entry with given key and value.
     *
     * @param k key
     * @param v value
     */
    Entry(K k, V v) {
        key = k;
        value = v;
    }
}
