package ru.mipt;

import org.junit.Test;
import ru.mipt.SingleThreadCache;
import static org.junit.jupiter.api.Assertions.*;

public class SingleThreadCacheTest {

    @Test
    public void shouldStoreValuesCorrectly() {
        int capacity = 10;
        SingleThreadCache<Integer, Integer> cache = new SingleThreadCache<>(capacity);
        for (int i = 0; i < 2 * capacity; i++) {
            cache.set(i, i);
        }

        for (int i = 0; i < capacity; i++) {
            assertNull(cache.find(i));
        }

        for (int i = capacity; i < 2 * capacity; i++) {
            assertEquals(i, cache.find(i));
        }
    }
}
