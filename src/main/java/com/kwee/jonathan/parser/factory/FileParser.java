package com.kwee.jonathan.parser.factory;

import com.kwee.jonathan.dtos.Options;
import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;
import com.kwee.jonathan.parser.strategy.ParseStrategy;

import java.io.FileNotFoundException;

public class FileParser {

    private final ParseStrategy parseStrategy;

    FileParser(ParseStrategy strategy) {
        this.parseStrategy = strategy;
    }

    /**
     * Parses the file indicated in the CLI argument, and outputs the content in tokens specified by
     * the file's extension type. Output method depends on what the user has indicated.
     * @param options contains the input file, file extension, output file (if specified) and encoding (if specified).
     * @throws ParseFileException when something is wrong when parsing the file.
     * @throws FileNotFoundException when the input or output file is not valid.
     * @throws UnsupportedDelimiterException when file extension is not supported in current version.
     */
    public void parseAndOutput(Options options)
            throws ParseFileException, FileNotFoundException, UnsupportedDelimiterException {
        parseStrategy.parseAndOutput(options);
    }

    public Class<? extends ParseStrategy> getStrategyType() {
        return parseStrategy.getClass();
    }
}
