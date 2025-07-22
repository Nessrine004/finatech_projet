package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Affectation;
import org.sid.gestion_v.entities.Utilisateur;
import org.sid.gestion_v.entities.Vehicule;
import org.sid.gestion_v.service.AffectationService;
import org.sid.gestion_v.service.UtilisateurService;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.sid.gestion_v.entities.StatutVehicule;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class AffectationViewController {

    private final AffectationService affectationService;
    private final VehiculeService vehiculeService;
    private final UtilisateurService utilisateurService;

    // Afficher toutes les affectations
    @GetMapping("/affectations")
    public String listAffectations(Model model) {
        model.addAttribute("affectations", affectationService.getAllAffectations());
        return "affectations";
    }

    // Afficher formulaire ajout
    @GetMapping("/ajouter-affectation")
    public String showForm(Model model) {
        List<Vehicule> vehiculesDisponibles = vehiculeService.getAllVehicules().stream()
                .filter(v -> v.getStatut() == StatutVehicule.LIBRE)
                .toList();

        model.addAttribute("affectation", new Affectation());
        model.addAttribute("vehicules", vehiculesDisponibles);
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        model.addAttribute("aucunVehiculeDispo", vehiculesDisponibles.isEmpty()); // ⚠️ nouvelle variable

        return "ajouterAffectation";
    }


    @PostMapping("/ajouter-affectation")
    public String save(@ModelAttribute Affectation affectation, Model model) {
        try {
            affectationService.createAffectation(affectation);
            return "redirect:/affectations";
        } catch (RuntimeException e) {
            // ⚠️ Ajouter les objets nécessaires à la vue
            model.addAttribute("affectation", affectation);
            model.addAttribute("vehicules", vehiculeService.getAllVehicules());
            model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
            model.addAttribute("errorMessage", e.getMessage()); // 🟥 afficher l'erreur

            return "ajouterAffectation";
        }
    }


    @GetMapping("/modifier-affectation/{id}")
    public String showFormModifier(@PathVariable Long id, Model model) {
        Affectation affectation = affectationService.getAffectationById(id);

        // Sécurité anti-null (optionnelle si tes données sont cohérentes)
        if (affectation.getVehicule() == null) affectation.setVehicule(new Vehicule());
        if (affectation.getUtilisateur() == null) affectation.setUtilisateur(new Utilisateur());

        model.addAttribute("affectation", affectation);
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());

        return "modifierAffectation";
    }
    @PostMapping("/modifier-affectation/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Affectation affectation, Model model) {
        try {
            affectationService.updateAffectation(id, affectation);
            return "redirect:/affectations";
        } catch (RuntimeException e) {
            model.addAttribute("affectation", affectation);
            model.addAttribute("vehicules", vehiculeService.getAllVehicules());
            model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
            model.addAttribute("errorMessage", e.getMessage());
            return "modifierAffectation";
        }
    }
    @PostMapping("/supprimer-affectation/{id}")
    public String deleteAffectation(@PathVariable Long id) {
        affectationService.deleteAffectation(id);
        return "redirect:/affectations";
    }
    @GetMapping("/affectations-readonly")
    public String listeAffectationsReadonly(Model model) {
        model.addAttribute("affectations", affectationService.getAllAffectations());
        return "affectations-readonly";
    }
}
