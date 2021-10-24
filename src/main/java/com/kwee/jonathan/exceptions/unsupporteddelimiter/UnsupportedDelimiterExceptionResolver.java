package com.kwee.jonathan.exceptions.unsupporteddelimiter;

import com.kwee.jonathan.constants.Delimiter;
import com.kwee.jonathan.exceptions.handler.interfaces.ExceptionResolver;
import org.springframework.stereotype.Component;

@Component
public class UnsupportedDelimiterExceptionResolver implements ExceptionResolver {

    @Override
    public String resolveExceptionToString(Exception e) {
        UnsupportedDelimiterException unsupportedDelimiterException = (UnsupportedDelimiterException) e;

        StringBuilder unsupportedDelimiterErrorMessageFormat =
            new StringBuilder(
                    "Extension: '%s' is not currently supported. Please use one of the supported delimiters:"
            );

        for (Delimiter d : Delimiter.values()) {
            unsupportedDelimiterErrorMessageFormat
                .append("\n")
                .append("- ")
                .append(d.getDelimiterExtension());
        }

        return String.format(unsupportedDelimiterErrorMessageFormat.toString(), unsupportedDelimiterException.getExtension());
    }

}
