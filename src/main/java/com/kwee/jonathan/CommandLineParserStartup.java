package com.kwee.jonathan;

import com.kwee.jonathan.constants.Option;
import com.kwee.jonathan.dtos.Options;
import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.NoArgumentsException;
import com.kwee.jonathan.exceptions.NoFileExtensionException;
import com.kwee.jonathan.exceptions.UnsupportedDelimiterException;
import com.kwee.jonathan.parser.factory.FileParser;
import com.kwee.jonathan.parser.factory.FileParserFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.util.Optional;

@Component
public class CommandLineParserStartup implements CommandLineRunner {

    @Override
    public void run(String... args) throws UnsupportedDelimiterException, NoArgumentsException,
            InvalidPathException, NoFileExtensionException, FileNotFoundException, ParseFileException {

        // Throw error if no arguments are provided
        if (args.length == 0) {
            throw new NoArgumentsException("Please provide a file path as an argument.");
        }

        checkIfHelpOption(args);
        Options options = constructOptions(args);

        FileParser fileParser = FileParserFactory.instantiateFileParser(options.getFileExtension());
        fileParser.parseAndOutput(options);
    }

    private void checkIfHelpOption(String[] arguments) {
        if (arguments.length > 0) {
            String option = arguments[0];
            if (option.startsWith("--") && Option.HELP.name().equalsIgnoreCase(option.substring(2))) {
                System.out.println(Option.HELP_TEXT);
                System.exit(0);
            }
        }
    }

    private Options constructOptions(String[] arguments) throws NoFileExtensionException {

        Options options = new Options();

        // Extract extension from file name
        options.setInputFile(arguments[0]);
        options.setFileExtension(extractFileExtension(options.getInputFile()));

        // First argument is file name and not an option so can be skipped.
        int argumentPointer = 1;

        while (argumentPointer < arguments.length) {
            String argument = arguments[argumentPointer];
            if (argument.startsWith("--")) {
                argument = argument.substring(2);

                // Current supported set of options expects only one parameter so look forward by one.
                String parameter = arguments[++argumentPointer];

                if (Option.ENCODING.name().equalsIgnoreCase(argument)) {
                    options.setEncoding(parameter);
                } else if (Option.OUTPUT.name().equalsIgnoreCase(argument)) {
                    options.setOutputFile(parameter);
                }
            }

            ++argumentPointer;
        }

        return options;
    }

    private String extractFileExtension(File inputFile) throws NoFileExtensionException {
        return Optional.of(inputFile.getName())
                .filter(n -> n.contains("."))
                .map(n -> n.substring(n.lastIndexOf(".") + 1))
                .orElseThrow(() -> new NoFileExtensionException("Please provide a file path with a file extension."));
    }



}
