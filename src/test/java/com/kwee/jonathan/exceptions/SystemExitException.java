package com.kwee.jonathan.exceptions;

public class SystemExitException extends SecurityException {

    private final int exitCode;

    public SystemExitException(int exitCode) {
        this.exitCode = exitCode;
    }

    public int getExitCode() { return this.exitCode; }
}
