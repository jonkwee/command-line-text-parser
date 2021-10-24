package com.kwee.jonathan.parser.factory;

import com.kwee.jonathan.dtos.Options;
import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;
import com.kwee.jonathan.parser.strategy.ParseStrategy;

import java.io.FileNotFoundException;

public class FileParser {

    private final ParseStrategy parseStrategy;

    public FileParser(ParseStrategy strategy) {
        this.parseStrategy = strategy;
    }

    public void parseAndOutput(Options options)
            throws ParseFileException, FileNotFoundException, UnsupportedDelimiterException {
        parseStrategy.parseAndOutput(options);
    }

    public Class<? extends ParseStrategy> getStrategyType() {
        return parseStrategy.getClass();
    }
}
