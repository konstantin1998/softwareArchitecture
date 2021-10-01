package ru.mipt;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThreadSafeCacheTest {

    @Test
    public void mustReturnCorrectResultsFromCache() {
        int size = 1;
        Cache cache = new ThreadSafeCache(size);
        Key key = new Key();

        Value calculatedValue = cache.calculateWithCache(key);
        Value valueFromCache = cache.calculateWithCache(key);

        assertEquals(calculatedValue, valueFromCache);
    }

    private List<Key> generateKeys(int n) {
        List<Key> keys = new ArrayList<Key>();
        for (int i = 0; i < n; i++) {
            keys.add(new Key());
        }
        return keys;
    }

    private void makeRequests(Cache cache, List<Key> keys) {

        Thread t1 = new Thread(new Task(new ArrayList<>(keys), cache));

        Thread t2 = new Thread(new Task(new ArrayList<>(keys), cache));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mustStoreOnlyLastUsedKeys() {
        int times = 50;
        for(int i = 0; i < times; i++) {
            checkKeys();
        }
    }

    private void checkKeys() {
        int n = 100;
        List<Key> keys = generateKeys(3 * n);

        ThreadSafeCache cache = new ThreadSafeCache(2 * n);

        List<Key> initialKeys = keys.subList(0, 2 * n);
        makeRequests(cache, initialKeys);
        assertTrue(cache.getKeys().containsAll(initialKeys));
        assertEquals(initialKeys.size(), cache.getKeys().size());

        List<Key> recentUsedKeys = keys.subList(2 * n, 3 * n);
        makeRequests(cache, recentUsedKeys);

        assertTrue(cache.getKeys().containsAll(recentUsedKeys));
    }
}