package com.kwee.jonathan.constants;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;

/**
 * Single source of truth for delimiters. Internal logic for parsing will make use of these enums.
 * Supporting a new delimiter will be as easy as adding a new delimiter in this file.
 */
public enum Delimiters {

    TAB("tab", "\t"),
    NEWLINE("newline", "\n"),
    SPACE("space", " "),
    COMMA("csv", ",");

    private final String delimiterExtension;
    private final String delimiter;

    Delimiters(String delimiterExtension, String delimiter) {
        this.delimiterExtension = delimiterExtension;
        this.delimiter = delimiter;
    }

    public String getDelimiterExtension() { return this.delimiterExtension; }
    public String getDelimiter() { return this.delimiter; }

    public static boolean isExtensionValid(String extension) {
        return Arrays.stream(Delimiters.values())
                .anyMatch(delimiters -> delimiters.getDelimiterExtension().equalsIgnoreCase(extension));
    }


}
