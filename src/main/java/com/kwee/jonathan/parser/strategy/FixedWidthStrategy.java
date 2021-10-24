package com.kwee.jonathan.parser.strategy;

import com.kwee.jonathan.dtos.Options;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FixedWidthStrategy implements ParseStrategy {

    @Override
    public void executeStrategy(Options options, BufferedWriter writer, BufferedReader reader) throws UnsupportedDelimiterException, IOException {

        int fixedWidthLength = Integer.parseInt(options.getFileExtension());

        int character;
        List<String> storedString = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        while ((character = reader.read()) != -1) {
            if (stringBuilder.length() == fixedWidthLength) {
                stringBuilder = addAndResetString(stringBuilder, storedString);
            } else if (character == '\n') {
                stringBuilder = addAndResetString(stringBuilder, storedString);
                writeToOutput(storedString, writer);
                storedString.clear();
            } else if (character == '\r') {
                character = reader.read();
                if (character == '\n') {
                    stringBuilder.append(character);
                    stringBuilder = addAndResetString(stringBuilder, storedString);
                    writeToOutput(storedString, writer);
                    storedString.clear();
                } else {
                    stringBuilder = addAndResetString(stringBuilder, storedString);
                    writeToOutput(storedString, writer);
                    storedString.clear();
                    if (character != -1) stringBuilder.append(character);
                }
            } else {
                stringBuilder.append((char) character);
            }
        }

        if (!storedString.isEmpty()) {
            writeToOutput(storedString, writer);
        }
    }

}
