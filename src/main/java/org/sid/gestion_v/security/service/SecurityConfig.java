package org.sid.gestion_v.security.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailServiceImpl;

    public SecurityConfig(UserDetailServiceImpl userDetailServiceImpl) {
        this.userDetailServiceImpl = userDetailServiceImpl;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ADMIN uniquement
                        .requestMatchers(
                                "/affectation.html", "/ajouterAffectation/**", "/ajouterUtilisateur/**",
                                "/ajouterVehicule/**", "/modifierAffectation/**", "/modifierUtilisateur/**",
                                "/modifierVehicule/**",

                                // 👇 Accès restreint au module Employe
                                "/employes/**", "/ajouter-employe/**", "/modifier-employe/**", "/supprimer-employe/**"
                        ).hasRole("ADMIN")

                        // ADMIN + MANAGER : accès complet aux modules suivants
                        .requestMatchers(
                                "/assurances/**", "/pannes/**", "/visites-techniques/**",
                                "/carburants/**", "/ajouter-carburant/**",
                                "/cartes-carburant/**", "/ajouter-carte-carburant/**"
                        ).hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/assurances/save").hasAnyRole("ADMIN", "MANAGER")

                        // ADMIN + MANAGER : readonly si tu veux conserver des vues supplémentaires
                        .requestMatchers(
                                "/assurances-readonly/**", "/listeEntretien/**"
                        ).hasAnyRole("ADMIN", "MANAGER")

                        // TECHNICIEN_INGENIEUR : accès lecture seule véhicules + demande affectation
                        .requestMatchers(
                                "/vehicules-readonly/**", "/affectations-readonly/**", "/demande-affectation/**"
                        ).hasRole("TECHNICIEN_INGENIEUR")

                        // Toute autre requête nécessite l'authentification
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .build();
    }

}
