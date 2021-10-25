package com.kwee.jonathan.parser.factory;

import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.exceptions.UnsupportedDelimiterException;
import com.kwee.jonathan.parser.strategy.CustomDelimiterStrategy;
import com.kwee.jonathan.parser.strategy.FixedWidthStrategy;

public class FileParserFactory {

    /**
     * @param fileExtension a string that represents a file's extension.
     * @return an implementation of {@link com.kwee.jonathan.parser.factory.FileParser} based on provided extension.
     * @throws UnsupportedDelimiterException if provided extension is not supported in current version.
     */
    public static FileParser instantiateFileParser(String fileExtension) throws UnsupportedDelimiterException {
        if (isExtensionNumeric(fileExtension)) {
            return new FileParser(new FixedWidthStrategy());
        } else if (Delimiter.isExtensionValid(fileExtension)){
            return new FileParser(new CustomDelimiterStrategy());
        } else {
            throw new UnsupportedDelimiterException(fileExtension);
        }
    }

    /**
     * @param fileExtension a string that represents a file's extension
     * @return true if provided file extension is numeric and positive, else false.
     */
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
