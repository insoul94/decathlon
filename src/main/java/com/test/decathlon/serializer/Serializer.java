package com.test.decathlon.serializer;

import java.io.Serializable;
import java.nio.file.Path;

public interface Serializer<T extends Serializable> {
    Path serialize(T obj) throws Exception;
}
