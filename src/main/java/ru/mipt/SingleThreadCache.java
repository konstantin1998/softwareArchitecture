package ru.mipt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import static ru.mipt.Calculator.calculate;

public class SingleThreadCache implements Cache{
    private final int size;
    private final PriorityQueue<Entry> queue;
    private final Map<Key, Value> map;

    public SingleThreadCache(int size) {
        this.size = size;
        this.queue = new PriorityQueue<Entry>();
        this.map = new HashMap<Key, Value>();
    }

    public Value calculateWithCache(Key key) {
        long currTime = System.nanoTime();

        if (map.containsKey(key)) {
            return getResultFromCache(currTime, key);
        }

        Value result = calculate(key);
        optionallyDeleteOldKeysAndValues();
        addNewKeyAndValue(key, result, currTime);

        return result;
    }

    private void optionallyDeleteOldKeysAndValues() {
        if (map.size() == size) {
            Key minKey = queue.poll().getKey();
            map.remove(minKey);
        }
    }

    private void addNewKeyAndValue(Key key, Value value, long time) {
        map.put(key, value);
        queue.add(new Entry(time, key));
    }

    private Value getResultFromCache(long time, Key key) {
        queue.add(new Entry(time, key));
        return map.get(key);
    }

    public Collection<Key> getKeys() {
        return map.keySet();
    }
}
