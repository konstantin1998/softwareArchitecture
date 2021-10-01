package ru.mipt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class Entry implements Comparable<Entry> {
    private final long time;
    @Getter
    @NotNull
    private final Key key;

    public int compareTo(@NotNull Entry entry) {
        if ((this.time - entry.time) == 0) {
            return 0;
        }
        return (int) ((this.time - entry.time) / Math.abs((this.time - entry.time)));
    }

}
