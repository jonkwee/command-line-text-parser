package com.kwee.jonathan.exceptions.simple;

import com.kwee.jonathan.exceptions.handler.interfaces.ExceptionResolver;
import org.springframework.stereotype.Component;

/**
 * Exception resolver that extracts the message from any Exception class.
 */
@Component
public class SimpleExceptionResolver implements ExceptionResolver {

    @Override
    public String resolveExceptionToString(Exception e) {
        return e.getMessage();
    }
}
