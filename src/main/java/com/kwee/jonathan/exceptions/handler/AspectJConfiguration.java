package com.kwee.jonathan.exceptions.handler;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectJConfiguration {

    @Bean
    public ExceptionHandlerAspect exceptionHandlerAspect() {
        return Aspects.aspectOf(ExceptionHandlerAspect.class);
    }

}
