package com.kwee.jonathan.exceptions.unsupporteddelimiter;

import com.kwee.jonathan.constants.Delimiters;
import com.kwee.jonathan.exceptions.handler.interfaces.ExceptionResolver;
import org.springframework.stereotype.Component;

@Component
public class UnsupportedDelimiterExceptionResolver implements ExceptionResolver {

    @Override
    public String resolveExceptionToString(Exception e) {
        UnsupportedDelimiterException unsupportedDelimiterException = (UnsupportedDelimiterException) e;

        StringBuilder unsupportedDelimiterErrorMessageFormat =
            new StringBuilder(
                    "Extension: '%s' is not currently supported. Please use one of the supported delimiters: \n"
            );

        for (Delimiters d : Delimiters.values()) {
            unsupportedDelimiterErrorMessageFormat
                .append("- ")
                .append(d.getDelimiterExtension())
                .append("\n");
        }

        return String.format(unsupportedDelimiterErrorMessageFormat.toString(), unsupportedDelimiterException.getExtension());
    }

}
