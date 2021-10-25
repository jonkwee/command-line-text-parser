package com.kwee.jonathan.constants;

/**
 * Supported list of options when running through jar command.
 */
public enum Option {


    HELP("help"),
    ENCODING("encoding"),
    OUTPUT("output");

    private String optionName;

    public static final String HELP_TEXT = "Syntax:\n" +
            "java -jar command-line-parser.jar FILEPATH [OPTIONS]\n" +
            "Options:\n" +
            "  --encoding [ARGUMENT] - valid arguments: utf-8, iso-8859-1, us-ascii, utf-16. Default: utf-8\n" +
            "  --output [FILEPATH] - parse output to provided filepath instead of console out";

    Option(String optionName) {
        this.optionName = optionName;
    }


}
