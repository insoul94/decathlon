package com.test.decathlon.fileparser;

import com.test.decathlon.model.Unit;
import com.test.decathlon.exception.InvalidDataException;

import java.io.IOException;
import java.util.List;

public interface UnitsFileParser extends FileParser<List<Unit>> {
    List<Unit> parse() throws IOException, InvalidDataException;
}
