package com.kwee.jonathan.tests;

import com.kwee.jonathan.CommandLineParserApplication;
import com.kwee.jonathan.CustomSecurityManager;
import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.exceptions.SystemExitException;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;
import com.kwee.jonathan.parser.factory.FileParser;
import com.kwee.jonathan.parser.factory.FileParserFactory;
import com.kwee.jonathan.parser.strategy.CustomDelimiterStrategy;
import com.kwee.jonathan.parser.strategy.FixedWidthStrategy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommandLineParserApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FactoryTest {

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
    @ValueSource(strings = {"csv", "space", "tab"})
    public void instantiateCustomStrategyTest(String extension) throws UnsupportedDelimiterException {
        FileParser fileParser = FileParserFactory.instantiateFileParser(extension);
        Assertions.assertEquals(fileParser.getStrategyType().getName(), CustomDelimiterStrategy.class.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"10", "15", "25", "100"})
    public void instantiateFixedWidthStrategyTest(String extension) throws UnsupportedDelimiterException {
        FileParser fileParser = FileParserFactory.instantiateFileParser(extension);
        Assertions.assertEquals(fileParser.getStrategyType().getName(), FixedWidthStrategy.class.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"notsupported", "/////", "txt", "1/2", "0.5"})
    public void unsupportedDelimiterTest(String extension) throws UnsupportedDelimiterException {
        customOut.reset();
        try {
            FileParserFactory.instantiateFileParser(extension);
        } catch (SystemExitException ex) {
            StringBuilder unsupportedDelimiterErrorMessageFormat =
                    new StringBuilder(
                            "Extension: '%s' is not currently supported. Please use one of the supported delimiters:"
                    );

            for (Delimiter d : Delimiter.values()) {
                unsupportedDelimiterErrorMessageFormat
                        .append("\n")
                        .append("- ")
                        .append(d.getDelimiterExtension());
            }
            Assertions.assertEquals(String.format(unsupportedDelimiterErrorMessageFormat.toString() + "\n", extension), customOut.toString());
            Assertions.assertEquals(0, ex.getExitCode());
        }
    }

    @AfterAll
    public void restore() {
        System.setOut(systemOut);
        System.setSecurityManager(systemSecurityManager);
    }
}
