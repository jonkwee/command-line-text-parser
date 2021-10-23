package com.kwee.jonathan.dtos;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Options {

    private Charset encoding;
    private File outputFile;

    public static OptionsBuilder builder() {
        return new OptionsBuilder();
    }

    public void setEncoding(Charset encoding) {
        this.encoding = encoding;
    }
    public Charset getEncoding() { return this.encoding; }
    public void setOutputFile(File file) { this.outputFile = file; }
    public File getOutputFile() { return this.outputFile; }

    public static class OptionsBuilder {

        private Charset encoding;
        private File outputFile;

        public OptionsBuilder encoding(String encoding) {
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
            return this;
        }

        public OptionsBuilder outputFile(String filePath) {
            Path path = Paths.get(filePath);
            this.outputFile = path.toFile();
            return this;
        }

        public Options build() {
            Options options = new Options();
            options.setEncoding(encoding);
            options.setOutputFile(outputFile);
            return options;
        }

    }
}
