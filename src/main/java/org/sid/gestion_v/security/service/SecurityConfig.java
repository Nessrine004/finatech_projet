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
                                "/modifierVehicule/**"
                        ).hasRole("ADMIN")

                        // ADMIN + MANAGER : readonly assurances et entretiens
                        .requestMatchers(
                                "/assurances-readonly/**", "/listeEntretien/**"
                        ).hasAnyRole("ADMIN", "MANAGER")

                        // TECHNICIEN_INGENIEUR : accès en lecture seule aux vues readonly et formulaire demande
                        .requestMatchers(
                                "/vehicules-readonly/**", "/affectations-readonly/**", "/demande-affectation/**"
                        ).hasRole("TECHNICIEN_INGENIEUR")

                        // TECHNICIEN_INGENIEUR : gestion complète des assurances et entretiens
                        .requestMatchers(
                                "/assurances/**", "/pannes/**", "/visites-techniques/**"
                        ).hasRole("TECHNICIEN_INGENIEUR")
                        .requestMatchers(HttpMethod.POST, "/assurances/save").hasRole("TECHNICIEN_INGENIEUR")



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
