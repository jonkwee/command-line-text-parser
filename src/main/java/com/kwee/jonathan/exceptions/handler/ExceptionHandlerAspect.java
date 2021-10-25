package com.kwee.jonathan.exceptions.handler;

import com.kwee.jonathan.exceptions.ParseFileException;
import com.kwee.jonathan.exceptions.NoArgumentsException;
import com.kwee.jonathan.exceptions.NoFileExtensionException;
import com.kwee.jonathan.exceptions.resolvers.SimpleExceptionResolver;
import com.kwee.jonathan.exceptions.UnsupportedDelimiterException;
import com.kwee.jonathan.exceptions.resolvers.UnsupportedDelimiterExceptionResolver;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;

/**
 * Aspect to handle global exceptions through weaving. Resolves exceptions to a user-friendly message to be printed on the console.
 * For each possible exception that could occur in this program, implement a custom {@link com.kwee.jonathan.exceptions.handler.interfaces.ExceptionResolver}
 * if the error message requires additional logic to render. If the error message is just a static string, make use of
 * the {@link SimpleExceptionResolver}.
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
