package com.kwee.jonathan;

import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.constants.Option;
import com.kwee.jonathan.dtos.Options;
import com.kwee.jonathan.exceptions.noarguments.NoArgumentsException;
import com.kwee.jonathan.exceptions.nofileextension.NoFileExtensionException;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;
import com.kwee.jonathan.parser.FileParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class CommandLineParserStartup implements ApplicationRunner {

    private FileParser fileParser;

    public CommandLineParserStartup(FileParser fileParser) {
        this.fileParser = fileParser;
    }

    // Option line syntax
    // java -jar command-line-parser.jar FILEPATH [OPTIONS]
    // Options:
    // --encoding [ARGUMENT] valid arguments: utf-8, iso-8859-1, us-ascii, utf-16. default: utf-8
    @Override
    public void run(ApplicationArguments args) throws UnsupportedDelimiterException, NoArgumentsException,
            InvalidPathException, NoFileExtensionException {

        List<String> arguments = args.getNonOptionArgs();
        Options options = constructOptions(args.getSourceArgs());

        // Throw error if no arguments are provided
        if (arguments.isEmpty()) {
            throw new NoArgumentsException("Please provide a file path as an argument.");
        }

        String fileExtension = options.getFileExtension();
        if (Delimiter.isExtensionValid(fileExtension)) {
            fileParser.readAndParseFile(options.getInputFile(), Delimiter.convertNameToChar(fileExtension), options);
        } else {
            throw new UnsupportedDelimiterException(fileExtension);
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
                .orElseThrow(() -> new NoFileExtensionException("Please provide a file path with a file extension that is delimited by '.'"));
    }



}
