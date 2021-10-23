package com.kwee.jonathan.exceptions.handler;

import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.noarguments.NoArgumentsException;
import com.kwee.jonathan.exceptions.nofileextension.NoFileExtensionException;
import com.kwee.jonathan.exceptions.simple.SimpleExceptionResolver;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterException;
import com.kwee.jonathan.exceptions.unsupporteddelimiter.UnsupportedDelimiterExceptionResolver;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

/**
 * Aspect to handle global exceptions through weaving. For each possible exception that could occur
 * in this program, implement a custom ExceptionResolver for the exception such that a user-friendly
 * message can be displayed on the command prompt.
 */
@Aspect
public class ExceptionHandlerAspect {

    @Autowired
    private UnsupportedDelimiterExceptionResolver unsupportedDelimiterExceptionResolver;

    @Autowired
    private SimpleExceptionResolver simpleExceptionResolver;

    @AfterThrowing(pointcut = "execution(* com.kwee.jonathan..*(..))", throwing="ex")
    public void handleUnsupportedDelimiterException(UnsupportedDelimiterException ex) {
        printAndShutdown(unsupportedDelimiterExceptionResolver.resolveExceptionToString(ex));
    }

    @AfterThrowing(pointcut = "execution(* com.kwee.jonathan..*(..))", throwing="ex")
    public void handleNoArgumentsException(NoArgumentsException ex) {
        printAndShutdown(simpleExceptionResolver.resolveExceptionToString(ex));
    }

    @AfterThrowing(pointcut = "execution(* com.kwee.jonathan..*(..))", throwing = "ex")
    public void handleInvalidPathException(InvalidPathException ex) {
        printAndShutdown(simpleExceptionResolver.resolveExceptionToString(ex));
    }

    @AfterThrowing(pointcut = "execution(* com.kwee.jonathan..*(..))", throwing = "ex")
    public void handleNoFileExtensionException(NoFileExtensionException ex) {
        printAndShutdown(simpleExceptionResolver.resolveExceptionToString(ex));
    }

    @AfterThrowing(pointcut = "execution(* com.kwee.jonathan..*(..))", throwing = "ex")
    public void handleFileNotFoundException(FileNotFoundException ex) {
        printAndShutdown(simpleExceptionResolver.resolveExceptionToString(ex));
    }

    @AfterThrowing(pointcut = "execution(* com.kwee.jonathan..*(..))", throwing = "ex")
    public void handleParseFileException(ParseFileException ex) {
        printAndShutdown(simpleExceptionResolver.resolveExceptionToString(ex));
    }

    private void printAndShutdown(String displayMessage) {
        System.out.println(displayMessage);
        System.exit(0);
    }

}
