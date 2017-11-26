/**
 * Class hashing several types of data.
 */
class Hasher {
    /**
     * Calculates hash code of an element.
     * Calls explicitly defined function for several types and
     * default hash function for other types.
     *
     * @param el element to be hashed
     * @param <T> type of the element
     * @return hash code
     */
    static <T> int getHashCode(T el) {
        if (el instanceof Integer) {
            return getHashCode((Integer) el);
        }
        else if (el instanceof String) {
            return getHashCode((String) el);
        }

        return el.hashCode();
    }

    /**
     * Hashes int.
     * Just returns its value.
     *
     * @param el int for hashing
     * @return hash code of the element
     */
    private static int getHashCode(Integer el) {
        return el;
    }

    /**
     * Hashes string.
     * Assumes average text - both lower and uppercase letters,
     * numbers, punctuation marks, etc. Total number of characters
     * will be about 26*2 + 10 + 10 += 5, so constant is closest prime number 79.
     *
     * @param el string for hashing
     * @return hash code of the element
     */
    private static int getHashCode(String el) {
        int hash = 0;
        final int c = 79;

        for (int i = 0; i < el.length(); i++) {
            hash = c * hash + el.charAt(i) + 1;
        }

        return hash;
    }

    /**
     * Hashes char.
     * Returns its integer value.
     *
     * @param el char for hashing
     * @return hash code of the element
     */
    private static int getHashCode(Character el) {
        return (int) el;
    }
}
