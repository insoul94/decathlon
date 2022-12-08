package com.test.decathlon.serializer.impl;

import com.test.decathlon.exception.InvalidDataException;
import com.test.decathlon.model.Decathlon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static com.test.decathlon.testutil.MockModel.mockDecathlon;
import static org.junit.jupiter.api.Assertions.*;

class DecathlonXmlSerializerTest {

    private DecathlonXmlSerializer serializer;

    @BeforeEach
    void setUp() throws IOException {
        Path output = Paths.get("./out/decathlon.xml");
        serializer = new DecathlonXmlSerializer(output);
    }

    @Test
    void givenDecathlon_whenSerialize_thenCreateXml() throws JAXBException, InvalidDataException {
        Decathlon decathlon = mockDecathlon();

        Path actual = serializer.serialize(decathlon);

        assertAll(
                () -> assertTrue(actual.toFile().exists()),
                () -> assertTrue(actual.toFile().canRead()),
                () -> assertTrue(actual.toFile().isFile()),
                () -> assertTrue(actual.toString().endsWith(".xml"))
        );
    }

    @Test
    void givenDecathlon_whenSerialize_thenSerializeDecathlon() throws JAXBException {
        Decathlon decathlon = mockDecathlon();

        serializer.serialize(decathlon);

        JAXBContext context = JAXBContext.newInstance(Decathlon.class);
        Decathlon o = (Decathlon) context.createUnmarshaller().unmarshal(new File("./out/decathlon.xml"));
        assertThat(o, instanceOf(Decathlon.class));
    }

    @Test
    void givenNull_whenSerialize_thenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> serializer.serialize(null));
    }
}