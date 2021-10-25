package com.kwee.jonathan.exceptions;

public class UnsupportedDelimiterException extends Exception {

    private final String extension;

    public UnsupportedDelimiterException(String extension) {
        this.extension = extension;
    }

    public String getExtension() { return this.extension; }
}
