package com.test.decathlon.fileparser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public abstract class AbstractCsvParser<T> extends AbstractFileParser<T> {
    public AbstractCsvParser(Path source) {
        super(source, ";", StandardCharsets.UTF_8);
    }
}
