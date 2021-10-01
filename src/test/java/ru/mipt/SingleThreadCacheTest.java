package ru.mipt;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SingleThreadCacheTest {

    @Test
    public void mustReturnCorrectResultsFromCache() {
        int size = 1;
        Cache cache = new SingleThreadCache(size);
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

    private void makeRequests(Cache cache, Collection<Key> keys) {
        for (Key key: keys) {
            cache.calculateWithCache(key);
        }
    }

    @Test
    public void mustStoreOnlyLastUsedKeys() {
        int n = 500;
        List<Key> keys = generateKeys(3 * n);

        SingleThreadCache cache = new SingleThreadCache(2 * n);

        List<Key> initialKeys = keys.subList(0, 2 * n);
        makeRequests(cache, initialKeys);

        List<Key> recentUsedKeys = keys.subList(2 * n, 3 * n);
        makeRequests(cache, recentUsedKeys);

        List<Key> mostRecentUsedKeys = keys.subList(0, n);
        makeRequests(cache, mostRecentUsedKeys);

        Collection<Key> actualKeys = cache.getKeys();

        assertTrue(actualKeys.containsAll(recentUsedKeys));
        assertTrue(actualKeys.containsAll(mostRecentUsedKeys));

        actualKeys.removeAll(recentUsedKeys);
        actualKeys.removeAll(mostRecentUsedKeys);

        int expectedSize = 0;
        assertEquals(expectedSize, actualKeys.size());

    }

}