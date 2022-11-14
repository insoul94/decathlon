package com.test.decathlon.fileparser.impl;

import com.test.decathlon.model.Unit;
import com.test.decathlon.exception.InvalidDataException;
import com.test.decathlon.fileparser.AbstractCsvParser;
import com.test.decathlon.fileparser.UnitsFileParser;
import com.test.decathlon.util.CsvUtil;
import com.test.decathlon.util.DataUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Units parser - CSV file implementation. Input columns contract:
 *
 * <p> #1-#10 - descriptors encoding measurement units of input results file in respective order
 *
 * <p> Allowed values: ['m', 'cm', 'sec' or 'min:sec']
 *
 * <p> <code>delimiter = ';'</code>
 */
public class UnitsCsvParser extends AbstractCsvParser<List<Unit>>
        implements UnitsFileParser {

    public UnitsCsvParser(Path source) {
        super(source);
    }

    /**
     * Parse source file into a list of units.
     * @return list of size 10
     * @throws IOException if an I/O error occurs reading from the file
     * @throws InvalidDataException if invalid input data is passed
     */
    @Override
    public List<Unit> parse() throws IOException, InvalidDataException {
        List<String> lines = Files.readAllLines(getSource(), getCharset());
        List<Unit> units = new ArrayList<>();

        for (String line : lines) {
            String[] cells = CsvUtil.parseCells(line, getDelimiter());
            for (int i = 0; i < cells.length; i++) {
                Unit u = DataUtil.parseUnit(cells[i]);
                units.add(u);
            }
        }
        if (units.size() != 10) {
            throw new InvalidDataException("Invalid # of unit cells/containers. Must be 10. Was: " + units.size());
        }
        return units;
    }
}
