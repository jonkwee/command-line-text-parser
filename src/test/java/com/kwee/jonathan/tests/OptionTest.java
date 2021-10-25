package com.kwee.jonathan.tests;

import com.kwee.jonathan.CommandLineParserApplication;
import com.kwee.jonathan.CommandLineParserStartup;
import com.kwee.jonathan.CustomSecurityManager;
import com.kwee.jonathan.constants.Option;
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

import java.io.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommandLineParserApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OptionTest {

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
    public void helpOptionTest()
            throws ParseFileException, NoArgumentsException, FileNotFoundException, UnsupportedDelimiterException, NoFileExtensionException {

        customOut.reset();
        try {
            commandLineParserStartup.run("--help");
        } catch (SystemExitException ex) {
            Assertions.assertEquals(Option.HELP_TEXT + "\n", customOut.toString());
            Assertions.assertEquals(0, ex.getExitCode());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "MOCK_DATA.10", "MOCK_DATA.csv", "MOCK_DATA.space", "MOCK_DATA.tab" })
    public void outputOptionTest(String fileName)
            throws ParseFileException, NoArgumentsException, IOException, UnsupportedDelimiterException, NoFileExtensionException {

        customOut.reset();
        String inputFilePath = "src/test/data/" + fileName;
        String outputFilePath = inputFilePath + ".out";
        commandLineParserStartup.run(inputFilePath, "--output", outputFilePath);

        // Check if file exists
        File outputFile = new File(outputFilePath);
        Assertions.assertTrue(outputFile.exists());

        // Check if there is no system outs
        Assertions.assertTrue(customOut.toString().trim().isBlank());

        outputFile.delete();

    }

    @AfterAll
    public void restore() {
        System.setOut(systemOut);
        System.setSecurityManager(systemSecurityManager);
    }
}
