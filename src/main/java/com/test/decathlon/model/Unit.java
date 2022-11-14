package com.test.decathlon.model;

/**
 * Unique descriptors must be assigned for each enum member to operate with units.
 *
 * <p> Delimiter for <code>MINUTES_SECONDS</code> must be of format:
 * <code>'xxYzz'</code> where Y - is a 1 char symbol.
 */
public enum Unit {
    CENTIMETRES("cm"),
    METRES("m"),
    SECONDS("sec"),
    MINUTES_SECONDS("min:" + SECONDS.descriptor);

    private final String descriptor;

    Unit(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }

    /**
     * @return delimiter of <code>MINUTES_SECONDS</code> descriptor
     */
    public static String getMinutesSecondsDelimiter() {
        int i = MINUTES_SECONDS.descriptor.indexOf(SECONDS.descriptor);
        return MINUTES_SECONDS.descriptor.substring(i - 1, i);
    }
}
