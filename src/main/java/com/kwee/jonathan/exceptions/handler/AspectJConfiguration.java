package com.kwee.jonathan.exceptions.handler;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Required configuration for AspectJ's aspect to be handled within
 * the Spring context. Allows autowiring in AspectJ's aspect.
 */
@Configuration
public class AspectJConfiguration {

    @Bean
    public ExceptionHandlerAspect exceptionHandlerAspect() {
        return Aspects.aspectOf(ExceptionHandlerAspect.class);
    }

}
