package com.kwee.jonathan.parser.strategy;

import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.dtos.Options;
import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomDelimiterStrategy implements ParseStrategy {

    @Override
    public void parseAndOutput(Options options) throws UnsupportedDelimiterException, FileNotFoundException, ParseFileException {
        Delimiter delimiter = Delimiter.convertNameToDelimiter(options.getFileExtension());
        char delimiterChar = delimiter.getDelimiter();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(options.getInputFile(), options.getEncoding()));
             BufferedWriter bufferedWriter = options.getOutputFile() != null ?
                     new BufferedWriter(new FileWriter(options.getOutputFile(), options.getEncoding())) : null) {

            int character;
            List<String> storedString = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();
            while ((character = bufferedReader.read()) != -1) {
                if (character == delimiterChar && !(stringBuilder.length() == 0)) {
                    storedString.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                } else if (character == '\n') {
                    storedString.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    writeToOutput(storedString, bufferedWriter);
                    storedString.clear();
                } else if (character == '\r') {
                    character = bufferedReader.read();
                    if (character == '\n') {
                        stringBuilder.append(character);
                        storedString.add(stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                        writeToOutput(storedString, bufferedWriter);
                        storedString.clear();
                    } else {
                        storedString.add(stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                        writeToOutput(storedString, bufferedWriter);
                        storedString.clear();
                        if (character != -1) stringBuilder.append(character);
                    }
                } else {
                    stringBuilder.append((char) character);
                }
            }

            if (!storedString.isEmpty()) {
                System.out.println(storedString);
            }

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
}
