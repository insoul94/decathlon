package com.test.decathlon.fileparser;

import com.test.decathlon.exception.InvalidDataException;
import com.test.decathlon.model.EventProperties;

import java.io.IOException;
import java.util.List;

public interface PropertiesFileParser extends FileParser<List<EventProperties>> {
    List<EventProperties> parse() throws IOException, InvalidDataException;
}
