package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Utilisateur;
import org.sid.gestion_v.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UtilisateurViewController {

    private final UtilisateurService utilisateurService;

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
    public String enregistrerUtilisateur(@ModelAttribute Utilisateur utilisateur) {
        utilisateurService.save(utilisateur);
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
    public String supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return "redirect:/utilisateurs";
    }
}
