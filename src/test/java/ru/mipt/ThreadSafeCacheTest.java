package ru.mipt;

import org.junit.Test;
import ru.mipt.ThreadSafeCache;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadSafeCacheTest {
    private final int capacity = 5000;

    private void insertValues(ThreadSafeCache<Integer, Integer> cache, int start) {
        Runnable r = () -> {
            for(int i = 0; i < capacity; i ++) {
                cache.set(start + i, start + i);
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(r);
        Thread t4 = new Thread(r);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void mustStoreValuesCorrectly() {
        int numOfChecks = 100;
        for (int i = 0; i < numOfChecks; i++) {
            insertAndCheckValues();
        }
    }

    private void insertAndCheckValues() {
        ThreadSafeCache<Integer, Integer> cache = new ThreadSafeCache<>(capacity);
        insertValues(cache);
        checkValues(cache);
    }

    private void checkValues(ThreadSafeCache<Integer, Integer> cache) {
        for (int i = 0; i < capacity; i++) {
            assertNull(cache.find(i));
        }

        for (int i = capacity; i < 2 * capacity; i++) {
            assertEquals(i, cache.find(i));
        }
    }

    private void insertValues(ThreadSafeCache<Integer, Integer> cache) {
        insertValues(cache, 0);
        insertValues(cache, capacity);
    }
}
