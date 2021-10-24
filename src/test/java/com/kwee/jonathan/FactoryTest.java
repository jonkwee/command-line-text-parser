package com.kwee.jonathan;

import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.exceptions.SystemExitException;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;
import com.kwee.jonathan.parser.factory.FileParser;
import com.kwee.jonathan.parser.factory.FileParserFactory;
import com.kwee.jonathan.parser.strategy.CustomDelimiterStrategy;
import com.kwee.jonathan.parser.strategy.FixedWidthStrategy;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommandLineParserApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FactoryTest {

    private final ByteArrayOutputStream customOut = new ByteArrayOutputStream();
    private PrintStream systemOut = System.out;
    private SecurityManager systemSecurityManager;

    @BeforeAll
    public void initialize() {
        System.setOut(new PrintStream(customOut));
        systemSecurityManager = System.getSecurityManager();
        System.setSecurityManager(new CustomSecurityManager());
    }

    @Test
    public void instantiateCustomStrategyTest() throws UnsupportedDelimiterException {
        FileParser fileParser = FileParserFactory.instantiateFileParser("csv");
        Assertions.assertEquals(fileParser.getStrategyType().getName(), CustomDelimiterStrategy.class.getName());
    }

    @Test
    public void instantiateFixedWidthStrategyTest() throws UnsupportedDelimiterException {
        FileParser fileParser = FileParserFactory.instantiateFileParser("10");
        Assertions.assertEquals(fileParser.getStrategyType().getName(), FixedWidthStrategy.class.getName());
    }

    @Test
    public void unsupportedDelimiterTest() throws UnsupportedDelimiterException {
        String extension = "notsupported";
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
