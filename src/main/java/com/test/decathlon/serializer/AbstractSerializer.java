package com.test.decathlon.serializer;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;

public abstract class AbstractSerializer<T extends Serializable> implements Serializer<T>{
    private final Path output;

    public AbstractSerializer(Path output) throws IOException {
        this.output = output;
        if (!output.toFile().exists()) {
            output.toFile().getParentFile().mkdirs();
            output.toFile().createNewFile();
            System.out.println("File created: " + output.getFileName());
        }
    }

    public Path getOutput() {
        return output;
    }
}
