package com.kwee.jonathan.parser.strategy;

import com.kwee.jonathan.exceptions.UnsupportedDelimiterException;

import java.io.IOException;

@FunctionalInterface
public interface ParserConsumer<O, BufferedWriter, BufferedReader> {

    void accept(O options, BufferedWriter writer, BufferedReader reader) throws UnsupportedDelimiterException, IOException;

}
