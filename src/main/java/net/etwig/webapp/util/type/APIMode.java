package net.etwig.webapp.util.type;

import net.etwig.webapp.util.InvalidActionException;

public enum APIMode {

    ADD,
    EDIT,
    VIEW,
    REMOVE;

    public static APIMode fromString(String str) {
        for (APIMode mode : APIMode.values()) {
            if (mode.name().equalsIgnoreCase(str)) {
                return mode;
            }
        }
        throw new InvalidActionException(str);
    }
}
