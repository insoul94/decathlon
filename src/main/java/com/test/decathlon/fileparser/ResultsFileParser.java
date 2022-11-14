package com.test.decathlon.fileparser;

import com.test.decathlon.exception.InvalidDataException;
import com.test.decathlon.model.Participant;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ResultsFileParser extends FileParser<Map<Participant, List<Float>>> {
    Map<Participant, List<Float>> parse() throws IOException, InvalidDataException;
}
