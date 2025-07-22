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
            // 🔹 Ajouter les rôles avant toute utilisation
            accountService.addNewRole("ADMIN");
            accountService.addNewRole("MANAGER");
            accountService.addNewRole("TECHNICIEN_INGENIEUR");

            // 🔹 ADMIN
            if (accountService.loadUserByNom("admin") == null) {
                accountService.addNewUser("admin", "1234", "admin@example.com", "1234");
            }
            accountService.addRoleToUser("admin", "ADMIN");
            accountService.removeRoleFromUser("admin", "MANAGER"); // maintenant OK

            // 🔹 MANAGER
            if (accountService.loadUserByNom("manager") == null) {
                accountService.addNewUser("manager", "1234", "manager@example.com", "1234");
            }
            accountService.addRoleToUser("manager", "MANAGER");

            // 🔹 TECHNICIEN
            if (accountService.loadUserByNom("tech") == null) {
                accountService.addNewUser("tech", "1234", "tech@example.com", "1234");
            }
            accountService.addRoleToUser("tech", "TECHNICIEN_INGENIEUR");
        };
    }




}
