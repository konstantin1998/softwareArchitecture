package ru.mipt;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Task implements Runnable{
    private final List<Key> keys;
    private final Cache cache;

    @Override
    public void run() {
        for (Key key : keys) {
            cache.calculateWithCache(key);
        }
    }
}
