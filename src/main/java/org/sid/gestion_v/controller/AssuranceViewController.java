package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Assurance;
import org.sid.gestion_v.service.AssuranceService;
import org.sid.gestion_v.service.UtilisateurService;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AssuranceViewController {

    private final AssuranceService assuranceService;
    private final VehiculeService vehiculeService;
    private final UtilisateurService utilisateurService;

    // 🔹 Afficher la liste des assurances
    @GetMapping("/assurances")
    public String listAssurances(Model model) {
        model.addAttribute("assurances", assuranceService.getAllAssurances());
        return "assurance/list"; // ← fichier Thymeleaf
    }

    // 🔹 Formulaire d’ajout
    @GetMapping("/ajouter-assurance")
    public String showAddForm(Model model) {
        model.addAttribute("assurance", new Assurance());
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "assurance/form";
    }

    // 🔹 Traitement de l’ajout
    @PostMapping("/ajouter-assurance")
    public String saveAssurance(@ModelAttribute Assurance a, Model model) {
        assuranceService.createAssurance(a);
        return "redirect:/assurances";
    }

    // 🔹 Formulaire de modification
    @GetMapping("/modifier-assurance/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("assurance", assuranceService.getAssuranceById(id));
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "assurance/form";
    }

    // 🔹 Traitement de la mise à jour
    @PostMapping("/modifier-assurance/{id}")
    public String updateAssurance(@PathVariable Long id, @ModelAttribute Assurance a) {
        assuranceService.updateAssurance(id, a);
        return "redirect:/assurances";
    }

    // 🔹 Suppression d’une assurance
    @PostMapping("/supprimer-assurance/{id}")
    public String deleteAssurance(@PathVariable Long id) {
        assuranceService.deleteAssurance(id);
        return "redirect:/assurances";
    }



}
