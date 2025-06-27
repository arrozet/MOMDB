package es.uma.taw.momdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author arrozet (Rubén Oliva - 86.7%), edugbau (Eduardo González - 13.3%)
 */
@SpringBootApplication
@EnableCaching
public class MomDb {

    public static void main(String[] args) {
        SpringApplication.run(MomDb.class, args);
    }

}
