package com.kwee.jonathan;

import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.exceptions.noarguments.NoArgumentsException;
import com.kwee.jonathan.exceptions.nofileextension.NoFileExtensionException;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;
import com.kwee.jonathan.parser.FileParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Component
public class CommandLineParserStartup implements ApplicationRunner {

    private FileParser fileParser;

    public CommandLineParserStartup(FileParser fileParser) {
        this.fileParser = fileParser;
    }

    @Override
    public void run(ApplicationArguments args) throws UnsupportedDelimiterException, NoArgumentsException,
            InvalidPathException, NoFileExtensionException {
        List<String> arguments = args.getNonOptionArgs();

        // Throw error if no arguments are provided
        if (arguments.isEmpty()) {
            throw new NoArgumentsException("Please provide a file path as an argument.");
        }

        // Will always pick the first argument as file path
        String filePathArgument = arguments.get(0);

        Path filePath = Paths.get(filePathArgument);
        File file = filePath.toFile();
        String fileExtension = Optional.of(file.getName())
            .filter(n -> n.contains("."))
            .map(n -> n.substring(n.lastIndexOf(".") + 1))
            .orElseThrow(() -> new NoFileExtensionException("Please provide a file path with a file extension that is delimited by '.'"));

        if (Delimiter.isExtensionValid(fileExtension)) {
            fileParser.readAndParseFile(file, Delimiter.convertNameToChar(fileExtension));
        } else {
            throw new UnsupportedDelimiterException(fileExtension);
        }






    }


}
