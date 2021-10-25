package com.kwee.jonathan.constants;

import com.kwee.jonathan.exceptions.UnsupportedDelimiterException;

import java.util.Arrays;

/**
 * Single source of truth for delimiters. Internal logic for parsing will make use of these enums.
 * Supporting a new delimiter character will be as easy as adding a new delimiter in this file.
 */
public enum Delimiter {

    TAB("tab", '\t'),
    SPACE("space", ' '),
    COMMA("csv", ',');

    private final String delimiterExtension;
    private final char delimiter;

    Delimiter(String delimiterExtension, char delimiter) {
        this.delimiterExtension = delimiterExtension;
        this.delimiter = delimiter;
    }

    public String getDelimiterExtension() { return this.delimiterExtension; }
    public char getDelimiter() { return this.delimiter; }

    public static boolean isExtensionValid(String extension) {
        return Arrays.stream(Delimiter.values())
                .anyMatch(delimiters -> delimiters.getDelimiterExtension().equalsIgnoreCase(extension));
    }

    public static Delimiter convertNameToDelimiter(String name) throws UnsupportedDelimiterException {
        return Arrays.stream(Delimiter.values())
                .filter(delimiters -> delimiters.delimiterExtension.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new UnsupportedDelimiterException(name));

    }

    public static char convertNameToChar(String name) throws UnsupportedDelimiterException {
        return convertNameToDelimiter(name).delimiter;
    }


}
