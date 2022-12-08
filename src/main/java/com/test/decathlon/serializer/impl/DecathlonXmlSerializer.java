package com.test.decathlon.serializer.impl;

import com.test.decathlon.exception.InvalidDataException;
import com.test.decathlon.model.Decathlon;
import com.test.decathlon.serializer.DecathlonSerializer;
import com.test.decathlon.serializer.AbstractSerializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Decathlon com.test.decathlon.serializer - XML output file implementation.
 */
public class DecathlonXmlSerializer extends AbstractSerializer<Decathlon> implements DecathlonSerializer {

    public DecathlonXmlSerializer(Path output) throws IOException {
        super(output);
    }

    @Override
    public Path serialize(Decathlon decathlon) throws JAXBException, IllegalArgumentException {
        if (decathlon == null) {
            throw new IllegalArgumentException("Decathlon must not be null");
        }
        JAXBContext context = JAXBContext.newInstance(Decathlon.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(decathlon, getOutput().toFile());
        return getOutput();
    }
}
