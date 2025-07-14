package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    Utilisateur save(Utilisateur utilisateur);
    List<Utilisateur> getAllUtilisateurs();
    Utilisateur getUtilisateurById(Long id);
    Utilisateur updateUtilisateur(Long id, Utilisateur utilisateur);
    void deleteUtilisateur(Long id);
}
