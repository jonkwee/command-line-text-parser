package com.kwee.jonathan.parser.strategy;

import com.kwee.jonathan.dtos.Options;
import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;

import java.io.*;
import java.util.List;
import java.util.function.Consumer;

public interface ParseStrategy {

    void executeStrategy(Options options, BufferedWriter writer, BufferedReader reader) throws UnsupportedDelimiterException, IOException;

    default void parseAndOutput(Options options) throws UnsupportedDelimiterException, FileNotFoundException, ParseFileException {
        readAndWriteFile(options, this::executeStrategy);
    }

    default void readAndWriteFile(Options options, ParserConsumer<Options, BufferedWriter, BufferedReader> strategy)
            throws UnsupportedDelimiterException, FileNotFoundException, ParseFileException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(options.getInputFile(), options.getEncoding()));
             BufferedWriter bufferedWriter = options.getOutputFile() != null ?
                     new BufferedWriter(new FileWriter(options.getOutputFile(), options.getEncoding())) : null) {
            strategy.accept(options, bufferedWriter, bufferedReader);
        } catch (IOException ex) {
            if (ex instanceof FileNotFoundException) {
                if (options.getOutputFile() != null)
                    throw new FileNotFoundException("Please provide a valid input/output file!");
                else
                    throw new FileNotFoundException("Please provide a valid input file!");
            } else {
                throw new ParseFileException("Unable to parse files!");
            }
        }

    }

    default void writeToOutput(List<String> line, BufferedWriter bufferedWriter) throws IOException {
        if (!line.isEmpty()) {
            if (bufferedWriter == null) {
                System.out.println(line);
            } else {
                bufferedWriter.write(line.toString());
                bufferedWriter.newLine();
            }
        }
    }

    default StringBuilder addAndResetString(StringBuilder sb, List<String> storedString) {
        String newString = sb.toString();
        if (!newString.isBlank()) {
            storedString.add(newString);
        }
        return new StringBuilder();
    }

}
