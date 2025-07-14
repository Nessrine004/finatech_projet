package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Entretien;
import org.sid.gestion_v.entities.Utilisateur;
import org.sid.gestion_v.service.EntretienService;
import org.sid.gestion_v.service.UtilisateurService;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EntretienViewController {

    private final EntretienService entretienService;
    private final VehiculeService vehiculeService;
    private final UtilisateurService utilisateurService;

    // 🔹 Liste des entretiens
    @GetMapping("/entretiens")
    public String listEntretiens(Model model) {
        model.addAttribute("entretiens", entretienService.getAllEntretiens());
        return "entretien/list";  // ← fichier: templates/entretien/list.html
    }

    // 🔹 Formulaire d’ajout
    @GetMapping("/ajouter-entretien")
    public String showAddForm(Model model) {
        model.addAttribute("entretien", new Entretien());
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "entretien/form";  // ← fichier: templates/entretien/form.html
    }

    // 🔹 Soumission du formulaire
    @PostMapping("/ajouter-entretien")
    public String saveEntretien(@ModelAttribute Entretien entretien, Model model) {
        try {
            entretienService.createEntretien(entretien);
            return "redirect:/entretiens";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("vehicules", vehiculeService.getAllVehicules());
            model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
            return "entretien/form";
        }
    }

    // 🔹 Formulaire de modification
    @GetMapping("/modifier-entretien/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Entretien entretien = entretienService.getEntretienById(id);
        model.addAttribute("entretien", entretien);
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "entretien/form";
    }

    // 🔹 Mise à jour
    @PostMapping("/modifier-entretien/{id}")
    public String updateEntretien(@PathVariable Long id, @ModelAttribute Entretien entretien, Model model) {
        try {
            entretienService.updateEntretien(id, entretien);
            return "redirect:/entretiens";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("vehicules", vehiculeService.getAllVehicules());
            model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
            return "entretien/form";
        }
    }

    // 🔹 Suppression
    @PostMapping("/supprimer-entretien/{id}")
    public String deleteEntretien(@PathVariable Long id) {
        entretienService.deleteEntretien(id);
        return "redirect:/entretiens";
    }




}
