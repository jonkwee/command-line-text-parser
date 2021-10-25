package com.kwee.jonathan.tests;

import com.kwee.jonathan.CommandLineParserApplication;
import com.kwee.jonathan.CommandLineParserStartup;
import com.kwee.jonathan.CustomSecurityManager;
import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.SystemExitException;
import com.kwee.jonathan.exceptions.NoArgumentsException;
import com.kwee.jonathan.exceptions.NoFileExtensionException;
import com.kwee.jonathan.exceptions.UnsupportedDelimiterException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommandLineParserApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InputTest {

    @Autowired
    private CommandLineParserStartup commandLineParserStartup;

    private final ByteArrayOutputStream customOut = new ByteArrayOutputStream();
    private final PrintStream systemOut = System.out;
    private SecurityManager systemSecurityManager;

    @BeforeAll
    public void initialize() {
        System.setOut(new PrintStream(customOut));
        systemSecurityManager = System.getSecurityManager();
        System.setSecurityManager(new CustomSecurityManager());
    }

    @Test
    public void noArgumentsTest() throws ParseFileException, FileNotFoundException,
            UnsupportedDelimiterException, NoFileExtensionException, NoArgumentsException {

        customOut.reset();
        try {
            commandLineParserStartup.run();
        } catch (SystemExitException ex) {
            Assertions.assertEquals("Please provide a file path as an argument.\n", customOut.toString());
            Assertions.assertEquals(0, ex.getExitCode());
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"/////", "test.1/2"})
    public void noFileExtensionTest(String fileName) throws ParseFileException, FileNotFoundException,
            UnsupportedDelimiterException, NoFileExtensionException, NoArgumentsException {

        customOut.reset();
        try {
            commandLineParserStartup.run(fileName);
        } catch (SystemExitException ex) {
            Assertions.assertEquals("Please provide a file path with a file extension.\n", customOut.toString());
            Assertions.assertEquals(0, ex.getExitCode());
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"test.notsupported", "test.txt", "index.html", "test.jar", "test.-1"})
    public void unsupportedDelimiterTest(String fileName) throws ParseFileException, FileNotFoundException,
            UnsupportedDelimiterException, NoFileExtensionException, NoArgumentsException {

        customOut.reset();
        try {
            commandLineParserStartup.run(fileName);
        } catch (SystemExitException ex) {
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
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
            Assertions.assertEquals(String.format(unsupportedDelimiterErrorMessageFormat + "\n", extension), customOut.toString());
            Assertions.assertEquals(0, ex.getExitCode());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"test.csv", "test.csv", "test.space" })
    public void invalidInputFilePathTest(String fileName) throws ParseFileException, FileNotFoundException,
            UnsupportedDelimiterException, NoFileExtensionException, NoArgumentsException {

        customOut.reset();
        try {
            commandLineParserStartup.run(fileName);
        } catch (SystemExitException ex) {
            Assertions.assertEquals("Please provide a valid input file!\n", customOut.toString());
            Assertions.assertEquals(0, ex.getExitCode());
        }
    }

    @AfterAll
    public void restore() {
        System.setOut(systemOut);
        System.setSecurityManager(systemSecurityManager);
    }

}
