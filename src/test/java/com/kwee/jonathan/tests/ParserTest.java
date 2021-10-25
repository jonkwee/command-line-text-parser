package com.kwee.jonathan.tests;

import com.kwee.jonathan.CommandLineParserApplication;
import com.kwee.jonathan.CustomSecurityManager;
import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.dtos.Options;
import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.UnsupportedDelimiterException;
import com.kwee.jonathan.parser.factory.FileParser;
import com.kwee.jonathan.parser.factory.FileParserFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommandLineParserApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParserTest {

    private final ByteArrayOutputStream customOut = new ByteArrayOutputStream();
    private final PrintStream systemOut = System.out;
    private SecurityManager systemSecurityManager;

    @BeforeAll
    public void initialize() {
        System.setOut(new PrintStream(customOut));
        systemSecurityManager = System.getSecurityManager();
        System.setSecurityManager(new CustomSecurityManager());
    }

    @ParameterizedTest
    @ValueSource(strings = { "MOCK_DATA.tab" })
    public void tabParseTest(String fileName)
            throws UnsupportedDelimiterException, ParseFileException, IOException {

        customOut.reset();
        String inputFilePath = "src/test/data/" + fileName;
        String resultString = retrieveFileContents(inputFilePath, Delimiter.TAB.getDelimiterExtension());
        FileParser customFileParser = FileParserFactory.instantiateFileParser(Delimiter.TAB.getDelimiterExtension());
        customFileParser.parseAndOutput(createOptions(inputFilePath, Delimiter.TAB.getDelimiterExtension()));
        Assertions.assertEquals(resultString, customOut.toString());

    }

    @ParameterizedTest
    @ValueSource(strings = { "MOCK_DATA.space" })
    public void spaceParseTest(String fileName)
            throws UnsupportedDelimiterException, ParseFileException, IOException {

        customOut.reset();
        String inputFilePath = "src/test/data/" + fileName;
        String resultString = retrieveFileContents(inputFilePath, Delimiter.SPACE.getDelimiterExtension());
        FileParser customFileParser = FileParserFactory.instantiateFileParser(Delimiter.SPACE.getDelimiterExtension());
        customFileParser.parseAndOutput(createOptions(inputFilePath, Delimiter.SPACE.getDelimiterExtension()));
        Assertions.assertEquals(resultString, customOut.toString());

    }

    @ParameterizedTest
    @ValueSource(strings = { "MOCK_DATA.csv" })
    public void commaParseTest(String fileName)
            throws UnsupportedDelimiterException, ParseFileException, IOException {

        customOut.reset();
        String inputFilePath = "src/test/data/" + fileName;
        String resultString = retrieveFileContents(inputFilePath, Delimiter.COMMA.getDelimiterExtension());
        FileParser customFileParser = FileParserFactory.instantiateFileParser(Delimiter.COMMA.getDelimiterExtension());
        customFileParser.parseAndOutput(createOptions(inputFilePath, Delimiter.COMMA.getDelimiterExtension()));
        Assertions.assertEquals(resultString, customOut.toString());

    }

    @ParameterizedTest
    @ValueSource(strings = { "MOCK_DATA.10" })
    public void fixedWidthTest(String fileName)
            throws UnsupportedDelimiterException, ParseFileException, IOException {
        String delimiter = fileName.substring(fileName.lastIndexOf(".") + 1);
        customOut.reset();
        String inputFilePath = "src/test/data/" + fileName;
        String resultString = retreiveFixedWidthFileContents(inputFilePath, delimiter);
        FileParser customFileParser = FileParserFactory.instantiateFileParser(delimiter);
        customFileParser.parseAndOutput(createOptions(inputFilePath, delimiter));
        Assertions.assertEquals(resultString, customOut.toString());
    }

    private Options createOptions(String filePath, String delimiter) {
        Options options = new Options();
        options.setFileExtension(delimiter);
        options.setInputFile(filePath);
        return options;
    }

    public static String retrieveFileContents(String filePath, String delimiter) throws IOException, UnsupportedDelimiterException {
        List<String> contents = Files.readAllLines(Path.of(filePath));
        StringBuilder sb = new StringBuilder();
        for (String content : contents) {
            sb.append(
                Arrays.toString(
                    content.split("" + Delimiter.convertNameToChar(delimiter))
                )
            ).append("\n");
        }
        return sb.toString();
    }

    private String retreiveFixedWidthFileContents(String filePath, String delimiter) throws IOException {
        List<String> contents = Files.readAllLines(Path.of(filePath));
        StringBuilder sb = new StringBuilder();
        for (String content : contents) {
            sb.append(splitByFixedWidth(content, Integer.parseInt(delimiter)))
                .append("\n");
        }
        return sb.toString();
    }

    private List<String> splitByFixedWidth(String text, int size) {
        List<String> lineContent = new ArrayList<>((text.length() + size - 1) / size);
        for (int start = 0; start < text.length(); start += size) {
            lineContent.add(
                text.substring(start, Math.min(text.length(), start + size))
            );
        }
        return lineContent;
    }


    @AfterAll
    public void restore() {
        System.setOut(systemOut);
        System.setSecurityManager(systemSecurityManager);
    }
}
