package com.kwee.jonathan.dtos;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Options {

    private Charset encoding = StandardCharsets.UTF_8;
    private File outputFile;
    private File inputFile;
    private String fileExtension;

    public void setEncoding(String encoding) {
        if (encoding.equalsIgnoreCase(StandardCharsets.UTF_8.name())) {
            this.encoding = StandardCharsets.UTF_8;
        } else if (encoding.equalsIgnoreCase(StandardCharsets.ISO_8859_1.name())) {
            this.encoding = StandardCharsets.ISO_8859_1;
        } else if (encoding.equalsIgnoreCase(StandardCharsets.US_ASCII.name())) {
            this.encoding = StandardCharsets.US_ASCII;
        } else if (encoding.equalsIgnoreCase(StandardCharsets.UTF_16.name())) {
            this.encoding = StandardCharsets.UTF_16;
        } else {
            this.encoding = StandardCharsets.UTF_8;
        }
    }

    public Charset getEncoding() { return this.encoding; }

    public void setOutputFile(String filePath) {
        Path path = Paths.get(filePath);
        this.outputFile = path.toFile();
    }

    public File getOutputFile() { return this.outputFile; }

    public void setInputFile(String filePath) {
        Path path = Paths.get(filePath);
        this.inputFile = path.toFile();
    }

    public File getInputFile() { return this.inputFile; }

    public void setFileExtension(String fileExtension) { this.fileExtension = fileExtension; }

    public String getFileExtension() { return this.fileExtension; }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ")
                .append("ENCODING: ").append(encoding).append(" ")
                .append("INPUT FILE ").append(inputFile.getPath()).append(" ")
                .append("FILE EXTENSION: ").append(fileExtension).append(" ");
        if (outputFile != null) {
            stringBuilder.append("OUTPUT FILE: ").append(outputFile.getPath()).append(" ");
        }
        return stringBuilder.append("]").toString();
    }

}
