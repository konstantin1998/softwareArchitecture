package ru.mipt;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TimeQueue {
    private final NavigableMap<Long, Key> map = new TreeMap<>();

    public void set(Key key, long time) {
        map.put(time, key);
    }

    public Key extractOldestKey() {
        Map.Entry<Long, Key> entry = map.pollFirstEntry();
        if (entry != null) {
            return entry.getValue();
        }
        throw new RuntimeException("Time queue is empty");
    }

    public void updateTime(Key key, Long time) {
        Map.Entry<Long, Key> entry = null;

        for (Map.Entry<Long, Key> e: map.entrySet()) {
            if (e.getValue() == key) {
                entry = e;
                break;
            }
        }

        if (entry != null) {
            map.remove(entry.getKey());
            map.put(time, entry.getValue());
        }
    }
}
