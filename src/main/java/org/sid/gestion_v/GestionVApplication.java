package org.sid.gestion_v;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionVApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionVApplication.class, args);
    }

}
