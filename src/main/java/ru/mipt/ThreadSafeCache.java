package ru.mipt;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafeCache implements Cache{
    private final int size;
    private final  TimeQueue timeQueue;
    private final Map<Key, Value> map;
    private long timestamp = 0;

    public ThreadSafeCache(int size) {
        this.size = size;
        timeQueue = new TimeQueue();
        map = new ConcurrentHashMap<>();
    }

    public Value calculateWithCache(Key key) {
        long currTime = getTimeStamp();

        if (map.containsKey(key)) {
            return getResultFromCache(currTime, key);
        }

        Value result = Calculator.calculate(key);
        optionallyDeleteOldKeysAndValues();
        addNewKeyAndValue(key, result, currTime);

        return result;
    }

    private synchronized void optionallyDeleteOldKeysAndValues() {
        while (map.size() > size) {
            Key minKey = timeQueue.extractOldestKey();
            map.remove(minKey);
        }
    }

    private synchronized void addNewKeyAndValue(Key key, Value value, long time) {
        map.put(key, value);
        timeQueue.set(key, time);
    }

    private synchronized Value getResultFromCache(long time, Key key) {
        timeQueue.updateTime(key, time);
        return map.get(key);
    }

    public Collection<Key> getKeys() {
        return map.keySet();
    }

    private long getTimeStamp() {
        timestamp++;
        return timestamp;
    }
}
