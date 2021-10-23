package com.kwee.jonathan.parser;

import com.kwee.jonathan.constants.Delimiter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileParser {

    public void readAndParseFile(File file, Delimiter delimiter) {
        char delimiterChar = delimiter.getDelimiter();
        // charset should be an option
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
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
                    System.out.println(storedString);
                    storedString.clear();
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

}
