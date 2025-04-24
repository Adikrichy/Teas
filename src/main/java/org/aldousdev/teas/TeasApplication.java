package org.aldousdev.teas;

import org.aldousdev.teas.service.dataSeed.SeedProperties;
import org.aldousdev.teas.service.dataSeed.SeedService;
import org.aldousdev.teas.service.storage.StorageProperties;
import org.aldousdev.teas.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, SeedProperties.class})

public class TeasApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeasApplication.class, args);
    }
    @Autowired
    private SeedService seedService;

    @Bean
    CommandLineRunner init(StorageService storageService) {
        System.out.println("I am preparing the photo storage?");
        return (args) -> {
            storageService.init();
        };
    }
    @Bean
    CommandLineRunner initSeed(SeedService seedService) {
        System.out.println("I am preparing the database?");
        return args -> {
            seedService.init();
        };

    }

}
