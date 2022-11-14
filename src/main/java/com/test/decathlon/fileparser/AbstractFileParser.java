package com.test.decathlon.fileparser;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Objects;

public abstract class AbstractFileParser<T> implements FileParser<T> {

    private final Path source;
    private final String delimiter;
    private final Charset charset;

    /**
     * @param source must not be <code>null</code>
     * @param delimiter between cells/data containers; if <code>null</code> - use default: any whitespace of any length
     * @param charset encoding of source file; if <code>null</code> - use default for current JVM
     */
    public AbstractFileParser(Path source, String delimiter, Charset charset) {
        this.source = Objects.requireNonNull(source);
        this.delimiter = Objects.nonNull(delimiter) ? delimiter : "\\s+";
        this.charset = Objects.nonNull(charset) ? charset : Charset.defaultCharset();
    }

    public Path getSource() {
        return source;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public Charset getCharset() {
        return charset;
    }
}