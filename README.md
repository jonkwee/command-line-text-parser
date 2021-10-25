# Command Line Parser

The command line parser will read lines of text from a file and parse each line into a list of tokens using various 
token delimiters. The delimiters are determined by the files' extensions. For example, a file name of "test.csv" will 
be tokenized by the comma delimiter and the file "test.tab" will be tokenized by the tab `\t` delimiter. 

## How to Build
Use the `mvn clean install` command to trigger the packaging of the application to a jar file.

## How to Run
Use the `java -jar target/command-line-parser.jar FILE_PATH` to run the application against the input file.
### Options
* --help `java -jar target/command-line-parser.jar --help` - displays the syntax and options available for this program.
* --output `java -jar target/command-line-parser.jar FILE_PATH --output OUTPUT_FILE_PATH` - output is now piped to the provided output file path.
* --encoding `java -jar target/command-line-parser.jar FILE_PATH --encoding ENCODING` - Determines the encoding when reading the input file. If output file is specified, also determines the encoding for writing into the output file.
  Supported options for encoding: utf-8, iso-8859-1, us-ascii. Defaults to utf-8 if not specified or if encoding is not in list of supported encodings.