package org.sid.gestion_v.security.service;

import org.sid.gestion_v.security.entities.AppRole;
import org.sid.gestion_v.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);

    AppUser loadUserByEmail(String email);



}
