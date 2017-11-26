/**
 * Interface of iterable - any object whose contents can be iterated over.
 */
public interface Iterable<T> {
    /**
     * Returns an iterator over elements of type T.
     *
     * @return an Iterator.
     */
    Iterator<T> iterator();
}
