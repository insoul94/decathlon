package com.test.decathlon.fileparser.impl;

import com.test.decathlon.exception.InvalidDataException;
import com.test.decathlon.model.EventProperties;
import com.test.decathlon.fileparser.AbstractCsvParser;
import com.test.decathlon.fileparser.PropertiesFileParser;
import com.test.decathlon.util.CsvUtil;
import com.test.decathlon.util.DataUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * Properties parser - CSV file implementation. Input columns contract:
 *
 * <p> #1 - event name; #2, #3, #4 - A, B, C decathlon score formula parameters respectively, #5 - units of
 * performance used in formula
 *
 * <p> <code>delimiter = ';'</code>
 */
public class PropertiesCsvParser extends AbstractCsvParser<List<EventProperties>>
        implements PropertiesFileParser {

    public PropertiesCsvParser(Path source) {
        super(source);
    }


    /**
     * Parse source file into a list of event properties.
     * @return list with size of 10
     * @throws IOException if an I/O error occurs reading from the file
     * @throws InvalidDataException if source file contains invalid input data
     */
    @Override
    public List<EventProperties> parse() throws IOException, InvalidDataException {
        List<String> lines = Files.readAllLines(getSource(), getCharset());
        List<EventProperties> propertiesList = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String[] cells = CsvUtil.parseCells(lines.get(i), getDelimiter());
            EventProperties ep = DataUtil.parseEventProperties(cells);
            propertiesList.add(ep);
        }
        if (propertiesList.size() != 10) {
            throw new InvalidDataException(
                    "Invalid events properties input data. Must be 10 events. Was: " + propertiesList.size());
        }
        return propertiesList;
    }
}