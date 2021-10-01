package ru.mipt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeQueueTest {

    @Test
    void mustReturnOldestKey() {

        Key youngestKey = new Key();
        int youngestKeyTime = 2;
        Key oldestKey = new Key();
        int oldestKeyTime = 1;

        TimeQueue queue = new TimeQueue();
        queue.set(oldestKey, oldestKeyTime);
        queue.set(youngestKey, youngestKeyTime);

        assertEquals(oldestKey, queue.extractOldestKey());
    }

    @Test
    void mustUpdateTime() {
        Key key1 = new Key();
        int time1 = 10;
        Key key2 = new Key();
        int time2 = 20;
        int updatedTime = 30;

        TimeQueue queue = new TimeQueue();
        queue.set(key1, time1);
        queue.set(key2, time2);
        queue.updateTime(key1, (long) updatedTime);

        assertEquals(key2, queue.extractOldestKey());
    }

}