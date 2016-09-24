import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class IntLongHashMapTest {

    private Map intLongHashMap;

    @Before
    public void setUp() throws Exception {
        intLongHashMap = new IntLongHashMap();
    }

    @Test
    public void testSize() throws Exception {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            intLongHashMap.put(random.nextInt(), random.nextLong());
        }
        assertEquals(20, intLongHashMap.size());
    }

    @Test
    public void testPut() throws Exception {
        assertEquals(0, intLongHashMap.put(0, 500));
        assertEquals(0, intLongHashMap.put(1, 100));
        assertEquals(0, intLongHashMap.put(2, 200));
        assertEquals(0, intLongHashMap.put(3, 300));

        assertEquals(500, intLongHashMap.get(0));
        assertEquals(100, intLongHashMap.get(1));
        assertEquals(200, intLongHashMap.get(2));
        assertEquals(300, intLongHashMap.get(3));
        assertEquals(0, intLongHashMap.get(4));
    }

    @Test
    public void testDoublePut() throws Exception {
        intLongHashMap.put(143, 999);
        intLongHashMap.put(467, 654);
        intLongHashMap.put(976, 234);
        intLongHashMap.put(564, 222);

        assertEquals(999, intLongHashMap.get(143));
        assertEquals(654, intLongHashMap.get(467));
        assertEquals(234, intLongHashMap.get(976));
        assertEquals(222, intLongHashMap.get(564));

        assertEquals(999, intLongHashMap.put(143, 777));
        assertEquals(654, intLongHashMap.put(467, 423));
        assertEquals(234, intLongHashMap.put(976, 614));

        assertEquals(777, intLongHashMap.get(143));
        assertEquals(423, intLongHashMap.get(467));
        assertEquals(614, intLongHashMap.get(976));
        assertEquals(222, intLongHashMap.get(564));
    }

    @Test
    public void testLargePut() throws Exception {
        final int maxIndex = 10000;
        for (int i = 0; i < maxIndex; i++) {
            intLongHashMap.put(i, i * 2);
        }

        for (int i = 0; i < maxIndex; i++) {
            assertEquals(i * 2, intLongHashMap.get(i));
        }

        assertEquals(maxIndex, intLongHashMap.size());
    }

    @Test
    public void testBasic() throws Exception {
        assertEquals(0, intLongHashMap.size());
        assertEquals(0, intLongHashMap.get(34));
        assertEquals(0, intLongHashMap.put(555, 234));
        assertEquals(234, intLongHashMap.get(555));
        assertEquals(1, intLongHashMap.size());
        assertEquals(0, intLongHashMap.put(433, 333));
        assertEquals(234, intLongHashMap.put(555, 999));
        assertEquals(2, intLongHashMap.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLargeFillFactor() {
        new IntLongHashMap(5, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeFillFactor() {
        new IntLongHashMap(5, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSize() {
        new IntLongHashMap(-2, 0.44f);
    }

}