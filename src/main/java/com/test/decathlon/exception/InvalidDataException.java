package com.test.decathlon.exception;

/**
 * Indicates invalid data input or illegal state of object.
 */
public class InvalidDataException extends Exception{
    public InvalidDataException(String message) {
        super(message);
    }
}
