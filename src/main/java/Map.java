
public interface Map {

    /**
     * Connect the value with the key. This method can return previous value for the key if it was placed previously.
     * @param key key that will be connected with value
     * @param value value that will be connected with key
     * @return previous  value for the key or 0.
     */
    long put(int key, long value);

    /**
     * Return the value for specific key. Can return 0 if map don't contains the key.
     * @param key the key with connected with value which will be returned.
     * @return the value that connected with the key.
     */
    long get(int key);

    /**
     * Return the number of elements in map.
     * @return the number of elements in map.
     */
    int size();
}
