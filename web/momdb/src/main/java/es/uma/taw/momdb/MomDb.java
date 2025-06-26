package es.uma.taw.momdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MomDb {

    public static void main(String[] args) {
        SpringApplication.run(MomDb.class, args);
    }

}
