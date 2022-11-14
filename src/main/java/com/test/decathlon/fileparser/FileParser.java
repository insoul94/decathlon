package com.test.decathlon.fileparser;

import com.test.decathlon.exception.InvalidDataException;

import java.io.IOException;

public interface FileParser<T> {
    T parse() throws IOException, InvalidDataException;
}
