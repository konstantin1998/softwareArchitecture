package ru.mipt;

import org.jetbrains.annotations.NotNull;

public class Value {
    private final Key key;

    public Value(@NotNull Key key) {
        this.key = key;
    }

    Key getKey() {
        return this.key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if ( o instanceof Value) {
            return this.key.equals(((Value) o).getKey());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
