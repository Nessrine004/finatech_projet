package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Utilisateur;
import org.sid.gestion_v.security.entities.AppUser;
import org.sid.gestion_v.security.service.AccountService;
import org.sid.gestion_v.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UtilisateurViewController {

    private final UtilisateurService utilisateurService;
    private final AccountService accountService;

    @GetMapping("/utilisateurs")
    public String afficherUtilisateurs(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "utilisateurs";
    }

    @GetMapping("/ajouter-utilisateur")
    public String formulaireAjout(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "ajouterUtilisateur";
    }

    @PostMapping("/ajouter-utilisateur")
    public String enregistrerUtilisateur(@ModelAttribute Utilisateur utilisateur, Model model) {
        // Bloquer l'ajout d'un autre admin
        if (utilisateur.getRole().name().equals("ADMIN")) {
            model.addAttribute("error", "Vous ne pouvez pas créer un autre utilisateur avec le rôle ADMIN.");
            return "ajouterUtilisateur";
        }

        // 1. Enregistrer l’utilisateur dans la table Utilisateur
        utilisateurService.save(utilisateur);

        // 2. Créer l'utilisateur dans AppUser (avec nom/prenom)
        String nom = utilisateur.getNom();
        String prenom = utilisateur.getPrenom();
        String password = utilisateur.getMotDePasse();
        String confirmPassword = password;
        String email = utilisateur.getEmail();

        try {
            AppUser user = accountService.addNewUser(nom, password, email, confirmPassword);
            user.setPrenom(prenom); // ajoute le prénom dans AppUser
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "ajouterUtilisateur";
        }

        // 3. Ajouter le rôle
        accountService.addNewRole(utilisateur.getRole().name());
        accountService.addRoleToUser(email, utilisateur.getRole().name());


        return "redirect:/utilisateurs";
    }

    @GetMapping("/modifier-utilisateur/{id}")
    public String formulaireModifier(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        model.addAttribute("utilisateur", utilisateur);
        return "modifierUtilisateur";
    }

    @PostMapping("/modifier-utilisateur/{id}")
    public String modifierUtilisateur(@PathVariable Long id, @ModelAttribute Utilisateur utilisateur) {
        utilisateurService.updateUtilisateur(id, utilisateur);
        return "redirect:/utilisateurs";
    }

    @PostMapping("/supprimer-utilisateur/{id}")
    public String supprimerUtilisateur(@PathVariable Long id, Model model) {
        try {
            utilisateurService.deleteUtilisateur(id);
            return "redirect:/utilisateurs";
        } catch (RuntimeException e) {
            model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
            model.addAttribute("error", e.getMessage());
            return "utilisateurs";
        }
    }

}
