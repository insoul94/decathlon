package com.test.decathlon;

import com.test.decathlon.service.DecathlonService;
import com.test.decathlon.fileparser.*;
import com.test.decathlon.fileparser.impl.PropertiesCsvParser;
import com.test.decathlon.fileparser.impl.ResultsCsvParser;
import com.test.decathlon.fileparser.impl.UnitsCsvParser;
import com.test.decathlon.serializer.DecathlonSerializer;
import com.test.decathlon.serializer.impl.DecathlonXmlSerializer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * To run Decathlon Application, set up <code>DecathlonService</code> with desired implementations
 * of parsers and com.test.decathlon.serializer, choose input/output files and call <code>run()</code>.
 */
public class DecathlonApp {

    public static final Path PROPERTIES_DEFAULT_PATH = Paths.get("src/main/resources/properties.csv");
    public static final Path RESULTS_DEFAULT_PATH = Paths.get("src/main/resources/results.csv");
    public static final Path INPUT_UNITS_DEFAULT_PATH = Paths.get("src/main/resources/input-units.csv");
    public static final Path OUTPUT_DEFAULT_PATH = Paths.get("./out/decathlon.xml");

    public static void main(String[] args) throws Exception {

        PropertiesFileParser propFileParser = new PropertiesCsvParser(PROPERTIES_DEFAULT_PATH);
        UnitsFileParser unitsFileParser = new UnitsCsvParser(INPUT_UNITS_DEFAULT_PATH);
        ResultsFileParser resFileParser = new ResultsCsvParser(RESULTS_DEFAULT_PATH, unitsFileParser);
        DecathlonSerializer serializer = new DecathlonXmlSerializer(OUTPUT_DEFAULT_PATH);

        DecathlonService service = new DecathlonService(propFileParser, resFileParser, serializer);
        service.run();
    }

}
