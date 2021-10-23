package com.kwee.jonathan.parser;

import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.dtos.Options;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileParser {

    public void readAndParseFile(File file, Delimiter delimiter, Options options) {
        char delimiterChar = delimiter.getDelimiter();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, options.getEncoding()));
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
                    writeToOutput(storedString,bufferedWriter);
                    storedString.clear();
                } else if (character == '\r') {
                    character = bufferedReader.read();
                    if (character == '\n') {
                        stringBuilder.append(character);
                        storedString.add(stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                        writeToOutput(storedString,bufferedWriter);
                        storedString.clear();
                    } else {
                        storedString.add(stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                        writeToOutput(storedString,bufferedWriter);
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
            ex.printStackTrace();
        }
    }

    //TODO: Handle IOException in Aspect
    private void writeToOutput(List<String> line, BufferedWriter bufferedWriter) throws IOException {
        if (bufferedWriter == null) {
            System.out.println(line);
        } else {
            bufferedWriter.write(line.toString());
            bufferedWriter.newLine();
        }
    }

}
