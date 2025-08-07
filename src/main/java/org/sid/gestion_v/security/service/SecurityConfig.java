package org.sid.gestion_v.security.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/affectation.html", "/ajouterAffectation/**", "/ajouterUtilisateur/**",
                                "/ajouterVehicule/**", "/modifierAffectation/**", "/modifierUtilisateur/**",
                                "/modifierVehicule/**",
                                "/employes/**", "/ajouter-employe/**", "/modifier-employe/**", "/supprimer-employe/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                "/assurances/**", "/pannes/**", "/visites-techniques/**",
                                "/carburants/**", "/ajouter-carburant/**",
                                "/cartes-carburant/**", "/ajouter-carte-carburant/**"
                        ).hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/assurances/save").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(
                                "/assurances-readonly/**", "/listeEntretien/**"
                        ).hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(
                                "/vehicules-readonly/**", "/affectations-readonly/**", "/demande-affectation/**"
                        ).hasRole("TECHNICIEN_INGENIEUR")
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
