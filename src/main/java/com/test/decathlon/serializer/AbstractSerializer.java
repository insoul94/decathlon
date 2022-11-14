package com.test.decathlon.serializer;

import java.io.Serializable;
import java.nio.file.Path;

public abstract class AbstractSerializer<T extends Serializable> implements Serializer<T>{
    private final Path output;

    public AbstractSerializer(Path output) {
        this.output = output;
    }

    public Path getOutput() {
        return output;
    }
}
