package ru.mipt;

public interface Cache {
    Value calculateWithCache(Key key);
}
