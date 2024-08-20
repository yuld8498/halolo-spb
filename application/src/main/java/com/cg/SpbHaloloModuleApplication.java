package com.cg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpbHaloloModuleApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpbHaloloModuleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpbHaloloModuleApplication.class, args);
        logger.info("Halolo Application Started........");
    }

}