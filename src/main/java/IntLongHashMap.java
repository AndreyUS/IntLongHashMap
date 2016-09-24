/**
 * Simple implementation of IntLongHashMap. Can works only with int as key and long as value.
 */
public class IntLongHashMap implements Map {

    private static final int DEFAULT_VALUE = 0;
    private static final int FREE_VALUE = 0;
    private static final float DEFAULT_FILL_FACTOR = 0.75f;
    private static final int DEFAULT_SIZE = 16;

    private int[] keys;
    private long[] values;

    private boolean hasValueAtZeroIndex;
    private long valueAtZeroIndex;

    private int size;

    private int threshold;

    private float fillFactor;

    public IntLongHashMap() {
        this(DEFAULT_SIZE, DEFAULT_FILL_FACTOR);
    }

    public IntLongHashMap(int size, float fillFactor) {

        if (fillFactor <= 0 || fillFactor >= 1) {
            throw new IllegalArgumentException("Fill factor must be between 0 - 1.");
        }

        if (size < 0) {
            throw new IllegalArgumentException("Capacity can't be negative.");
        }

        threshold = (int) (size * fillFactor);
        keys = new int[size];
        values = new long[size];
        this.fillFactor = fillFactor;
    }


    @Override
    public long put(int key, long value) {
        if (FREE_VALUE == key) {
            long previousValue = valueAtZeroIndex;
            if (!hasValueAtZeroIndex) {
                ++size;
            }
            hasValueAtZeroIndex = true;
            valueAtZeroIndex = value;
            return previousValue;
        }

        int index = findPutIndex(key);
        if (index < 0) {
            resize(keys.length * 2);
            index = findPutIndex(key);
        }

        long prevValue = values[index];

        if (keys[index] != key) {
            keys[index] = key;
            values[index] = value;
            ++size;
            if (size >= threshold) {
                resize(keys.length * 2);
            }
        } else {
            keys[index] = key;
            values[index] = value;
        }
        return prevValue;
    }

    @Override
    public long get(int key) {
        if (FREE_VALUE == key) {
            return hasValueAtZeroIndex ? valueAtZeroIndex : 0;
        }
        int index = getKeyIndex(key);
        return index >= 0 ? values[index] : DEFAULT_VALUE;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Find a index of keys array that will be updated.
     * @param key key that look for.
     * @return index of keys array that will be updated.
     * It can be free index or index this given key.
     */
    private int findPutIndex(int key) {
        int index = getKeyIndex(key);

        if (index >= 0) {
            return index;
        }

        int startIndex = getStartIndex(key);
        int tempIndex = startIndex;
        while (keys[tempIndex] != FREE_VALUE) {
            tempIndex = getNextIndex(tempIndex);
            if (tempIndex == startIndex) {
                return -1;
            }
        }
        return tempIndex;
    }

    /**
     * Find key index in key arrays.
     * @param key key that look for.
     * @return index for key or -1 if key not found.
     */
    private int getKeyIndex(int key) {
        int index = getStartIndex(key);

        if (keys[index] == key) {
            return index;
        }

        if (keys[index] == FREE_VALUE) {
            return -1;
        }
        int startIndex = index;
        while ((index = getNextIndex(index)) != startIndex) {
            if (keys[index] == key) {
                return index;
            }

            if (keys[index] == FREE_VALUE) {
                return -1;
            }
        }
        return -1;
    }

    private int getNextIndex(int index) {
        return index < keys.length - 1 ? index + 1 : 0;
    }

    /**
     * Returns start index in keys array for specific key.
     * @param key key that will be put in keys array.
     * @return start index in keys array for specific key.
     */
    private int getStartIndex(int key) {
        return Math.abs(key % keys.length);
    }

    /**
     * This method make resize for keys and values arrays.
     * @param newSize size of new arrays.
     */
    private void resize(int newSize) {

        threshold = (int) (newSize * fillFactor);

        int[] oldKeys = keys;
        long[] oldValues = values;
        size = hasValueAtZeroIndex ? 1 : 0;

        keys = new int[newSize];
        values = new long[newSize];

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != FREE_VALUE) {
                put(oldKeys[i], oldValues[i]);
            }
        }
    }
}
