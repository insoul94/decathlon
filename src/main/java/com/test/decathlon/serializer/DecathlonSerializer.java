package com.test.decathlon.serializer;

import com.test.decathlon.model.Decathlon;

import java.nio.file.Path;


public interface DecathlonSerializer extends Serializer<Decathlon>{
    Path serialize(Decathlon obj) throws Exception;
}
