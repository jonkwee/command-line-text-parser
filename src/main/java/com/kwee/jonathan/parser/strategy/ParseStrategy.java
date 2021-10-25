package com.kwee.jonathan.parser.strategy;

import com.kwee.jonathan.dtos.Options;
import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.UnsupportedDelimiterException;

import java.io.*;
import java.util.List;

public interface ParseStrategy {

    void executeStrategy(Options options, BufferedWriter writer, BufferedReader reader) throws UnsupportedDelimiterException, IOException;

    /**
     * Parses the outputs input file contents as tokens based on file extension.
     * Different file extensions will have different strategies/implementations of this method.
     * @param options contains the input file, file extension, output file (if specified) and encoding (if specified).
     * @throws UnsupportedDelimiterException when file extension is not supported in current version.
     * @throws FileNotFoundException when the input or output file is not valid.
     * @throws ParseFileException when something is wrong when parsing the file.
     */
    default void parseAndOutput(Options options) throws UnsupportedDelimiterException, FileNotFoundException, ParseFileException {
        readAndWriteFile(options, this::executeStrategy);
    }

    /**
     * Wrapper method for {@link #parseAndOutput(Options)}.
     * In charge of creating buffered reader and writer for different parsing strategies.
     * @param options contains the input file, file extension, output file (if specified) and encoding (if specified).
     * @param strategy specific implementation for parsing a file.
     * @throws UnsupportedDelimiterException when file extension is not supported in current version.
     * @throws FileNotFoundException when the input or output file is not valid.
     * @throws ParseFileException when something is wrong when parsing the file.
     */
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

    /**
     * Contains logic to write to console out or new file.
     * @param line a line (ending in \n) of the input file.
     * @param bufferedWriter
     * @throws IOException
     */
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

    /**
     * Contains logic to append a token to an output line.
     * @param sb StringBuilder containing a token
     * @param storedString list of tokens that is from a line of the input file.
     * @return new StringBuilder
     */
    default StringBuilder addAndResetString(StringBuilder sb, List<String> storedString) {
        String newString = sb.toString();

        // If token is either empty or whitespace, don't include it in the overall output.
        if (!newString.isBlank()) {
            storedString.add(newString);
        }
        return new StringBuilder();
    }

}
