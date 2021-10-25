package com.kwee.jonathan.parser.factory;

import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;
import com.kwee.jonathan.parser.strategy.CustomDelimiterStrategy;
import com.kwee.jonathan.parser.strategy.FixedWidthStrategy;

public class FileParserFactory {

    public static FileParser instantiateFileParser(String fileExtension) throws UnsupportedDelimiterException {
        if (isExtensionNumeric(fileExtension)) {
            return new FileParser(new FixedWidthStrategy());
        } else if (Delimiter.isExtensionValid(fileExtension)){
            return new FileParser(new CustomDelimiterStrategy());
        } else {
            throw new UnsupportedDelimiterException(fileExtension);
        }
    }

    private static boolean isExtensionNumeric(String fileExtension) {
        long extension;
        try {
            extension = Long.parseLong(fileExtension);
        } catch (NumberFormatException ex) {
            return false;
        }
        return extension > 0;
    }
}
