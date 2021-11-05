package ru.mipt;

import java.util.LinkedHashMap;

public class ThreadSafeCache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {

        return (size() > this.capacity);
    }

    public ThreadSafeCache(int capacity) {
        super(capacity + 1, 1.0f, true);
        this.capacity = capacity;
    }

    public synchronized V find(K key) {
        return super.get(key);
    }

    public synchronized void set(K key, V value) {
        super.put(key, value);
    }

}
