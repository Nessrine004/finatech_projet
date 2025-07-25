package org.sid.gestion_v;

import org.sid.gestion_v.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionVApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionVApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AccountService accountService) {
        return args -> {
            accountService.addNewRole("ADMIN");
            accountService.addNewRole("MANAGER");
            accountService.addNewRole("TECHNICIEN_INGENIEUR");

            if (accountService.loadUserByEmail("admin@example.com") == null) {
                accountService.addNewUser("admin", "1234", "admin@example.com", "1234");
            }
            accountService.addRoleToUser("admin@example.com", "ADMIN");
            accountService.removeRoleFromUser("admin@example.com", "MANAGER");

            if (accountService.loadUserByEmail("manager@example.com") == null) {
                accountService.addNewUser("manager", "1234", "manager@example.com", "1234");
            }
            accountService.addRoleToUser("manager@example.com", "MANAGER");

            if (accountService.loadUserByEmail("tech@example.com") == null) {
                accountService.addNewUser("tech", "1234", "tech@example.com", "1234");
            }
            accountService.addRoleToUser("tech@example.com", "TECHNICIEN_INGENIEUR");
        };
    }





}
