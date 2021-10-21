package com.kwee.jonathan.exceptions.invalidpath;

import com.kwee.jonathan.exceptions.handler.interfaces.ExceptionResolver;
import org.springframework.stereotype.Service;

@Service
public class InvalidPathExceptionResolver implements ExceptionResolver {

    private final String messageFormat = "Please provide a valid path to the file you need to be parsed.";

    @Override
    public String resolveExceptionToString(Exception e) {
        return messageFormat;
    }
}
