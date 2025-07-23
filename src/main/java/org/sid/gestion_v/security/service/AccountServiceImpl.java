package org.sid.gestion_v.security.service;

import org.sid.gestion_v.security.entities.AppRole;
import org.sid.gestion_v.security.entities.AppUser;
import org.sid.gestion_v.security.repo.AppRoleRepository;
import org.sid.gestion_v.security.repo.AppUserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Lazy;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;



    public AccountServiceImpl(AppUserRepository appUserRepository,
                              AppRoleRepository appRoleRepository,
                              @Lazy PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser = appUserRepository.findByNom(username);
        if (appUser != null) throw new RuntimeException("Cet utilisateur existe déjà.");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Mots de passe incorrects.");
        appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .nom(username) // si tu considères que username = nom
                .password(passwordEncoder.encode(password)) // ✅ mot de passe encodé ici
                .email(email)
                .build();
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole existingRole = appRoleRepository.findById(role).orElse(null);
        if (existingRole != null) return existingRole; // ✅ Retourne le rôle existant

        AppRole appRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }


    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByNom(username);
        AppRole appRole = appRoleRepository.findById(role)
                .orElseThrow(() -> new RuntimeException("Rôle introuvable"));

        if (!appUser.getRoles().contains(appRole)) {
            appUser.getRoles().add(appRole);
        }
    }


    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByNom(username);
        AppRole appRole = appRoleRepository.findById(role)
                .orElseThrow(() -> new RuntimeException("Rôle introuvable"));
        appUser.getRoles().remove(appRole);
    }


    @Override
    public AppUser loadUserByNom(String nom) {
        return appUserRepository.findByNom(nom);
    }

}
