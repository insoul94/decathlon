package com.test.decathlon.fileparser.impl;

import com.test.decathlon.model.Unit;
import com.test.decathlon.exception.InvalidDataException;
import com.test.decathlon.model.Participant;
import com.test.decathlon.fileparser.AbstractCsvParser;
import com.test.decathlon.fileparser.ResultsFileParser;
import com.test.decathlon.fileparser.UnitsFileParser;
import com.test.decathlon.util.CsvUtil;
import com.test.decathlon.util.DataUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Results parser - CSV file implementation. Input columns contract:
 *
 * <p> #1 - event name; #2-#11 - performances in seconds/minutes-seconds for track events,
 * in metres for field events. Format: floating point number.
 *
 * <p> <code>delimiter = ';'</code>
 */
public class ResultsCsvParser extends AbstractCsvParser<Map<Participant, List<Float>>>
        implements ResultsFileParser {

    private final UnitsFileParser unitsFileParser;

    public ResultsCsvParser(Path source, UnitsFileParser unitsFileParser) {
        super(source);
        this.unitsFileParser = unitsFileParser;
    }


    @Override
    public Map<Participant, List<Float>> parse() throws IOException, InvalidDataException {
        List<Unit> inputUnits = unitsFileParser.parse();
        return parse(inputUnits);
    }


    /**
     * Parse source file into a list of results.
     * @param inputUnits sequence of input units corresponding to results file
     * @return list of results
     * @throws IOException if an I/O error occurs reading from the file
     * @throws InvalidDataException if invalid input data is passed
     */
    public Map<Participant, List<Float>> parse(List<Unit> inputUnits) throws IOException, InvalidDataException {
        List<String> lines = Files.readAllLines(getSource(), getCharset());
        Map<Participant, List<Float>> resultsMap = new HashMap<>();

        for (String line : lines) {
            String[] cells = CsvUtil.parseCells(line, getDelimiter());
            if (cells.length != 11) {
                throw new InvalidDataException(
                        "Invalid results input data format. Must be 11 cells/containers per line. Was: "
                                + cells.length);
            }
            String name = cells[0];
            Participant participant = new Participant(name);
            List<Float> performanceList = new ArrayList<>();
            for (int i = 1; i < cells.length; i++) {
                float p = DataUtil.parsePerformance(cells[i], inputUnits.get(i - 1));
                performanceList.add(p);
            }
            resultsMap.put(participant, performanceList);
        }
        return resultsMap;
    }
}

