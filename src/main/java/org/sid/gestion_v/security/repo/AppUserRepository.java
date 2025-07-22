package org.sid.gestion_v.security.repo;

import org.sid.gestion_v.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByNom(String nom);

}
