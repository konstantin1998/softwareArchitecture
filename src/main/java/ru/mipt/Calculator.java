package ru.mipt;

public class Calculator {
    public static Value calculate(Key key) {
        return new Value(key);
    }
}
