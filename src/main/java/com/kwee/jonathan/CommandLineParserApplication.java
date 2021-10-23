package com.kwee.jonathan;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CommandLineParserApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CommandLineParserApplication.class)
            .bannerMode(Banner.Mode.OFF)
            .logStartupInfo(false)
            .run(args);
    }

}
