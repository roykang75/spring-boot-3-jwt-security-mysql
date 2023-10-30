package com.alibou.security.role;

public enum Level {
    RED(1),
    ORANGE(2),
    YELLOW(3),
    GREEN(4),
    BLUE(5),
    INDIGO(6),
    VIOLET(7),
    NONE(0),
    ;

    private final int value;

    Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Level getLevel(String level) {
        level = level.toUpperCase();
        switch (level) {
            case "RED": return RED;
            case "ORANGE": return ORANGE;
            case "YELLOW": return YELLOW;
            case "GREEN": return GREEN;
            case "BLUE": return BLUE;
            case "INDIGO": return INDIGO;
            case "VIOLET": return VIOLET;
        }

        return NONE;
    }
}
