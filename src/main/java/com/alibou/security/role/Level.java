package com.alibou.security.role;

public enum Level {
    LEVEL_RED(1),
    LEVEL_ORANGE(2),
    LEVEL_YELLOW(3),
    LEVEL_GREEN(4),
    LEVEL_BLUE(5),
    LEVEL_INDIGO(6),
    LEVEL_VIOLET(7),
    LEVEL_NONE(0),
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
            case "RED": return LEVEL_RED;
            case "ORANGE": return LEVEL_ORANGE;
            case "YELLOW": return LEVEL_YELLOW;
            case "GREEN": return LEVEL_GREEN;
            case "BLUE": return LEVEL_BLUE;
            case "INDIGO": return LEVEL_INDIGO;
            case "VIOLET": return LEVEL_VIOLET;
        }

        return LEVEL_NONE;
    }
}
