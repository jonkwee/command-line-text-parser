package com.kwee.jonathan.parser.strategy;

import com.kwee.jonathan.dtos.Options;
import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ParseStrategy {

    void parseAndOutput(Options options) throws UnsupportedDelimiterException, FileNotFoundException, ParseFileException;

    default void writeToOutput(List<String> line, BufferedWriter bufferedWriter) throws IOException {
        if (bufferedWriter == null) {
            System.out.println(line);
        } else {
            bufferedWriter.write(line.toString());
            bufferedWriter.newLine();
        }
    }

}
