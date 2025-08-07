package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Employe;
import org.sid.gestion_v.entities.Poste;
import org.sid.gestion_v.service.EmployeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;


import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employes")
public class EmployeViewController {

    private final EmployeService employeService;

    // ✅ Liste des employés
    @GetMapping
    public String listEmployes(Model model) {
        model.addAttribute("employes", employeService.getAllEmployes());
        return "employes/list"; // chemin vers employes/list.html
    }

    // ✅ Affichage du formulaire d’ajout
    @GetMapping("/ajouter")
    public String showFormAjout(Model model) {
        model.addAttribute("employe", new Employe());
        model.addAttribute("postes", Poste.values());
        return "employes/form"; // chemin vers employes/form.html
    }

    // ✅ Enregistrement (ajout ou modification)
    @PostMapping("/save")
    public String saveEmploye(@ModelAttribute Employe employe,
                              @RequestParam("cinFile") MultipartFile cinFile,
                              Model model) {
        try {
            if (!cinFile.isEmpty()) {
                employe.setCIN(cinFile.getBytes());
            }
            employeService.createEmploye(employe);
            return "redirect:/employes";
        } catch (IOException e) {
            model.addAttribute("error", "Erreur lors du téléchargement du fichier CIN.");
            model.addAttribute("employe", employe);
            model.addAttribute("postes", Poste.values());
            return "employes/form";
        }
    }
    @GetMapping(value = "/cin/{id}", produces = "image/jpeg")
    @ResponseBody
    public byte[] afficherCIN(@PathVariable Long id) {
        return employeService.getEmployeById(id).getCIN();
    }




    // ✅ Affichage du formulaire de modification
    @GetMapping("/modifier/{id}")
    public String showFormModif(@PathVariable Long id, Model model) {
        Employe employe = employeService.getEmployeById(id);
        if (employe == null) return "redirect:/employes";

        model.addAttribute("employe", employe);
        model.addAttribute("postes", Poste.values());
        return "employes/form";
    }

    // ✅ Suppression
    @GetMapping("/supprimer/{id}")
    public String deleteEmploye(@PathVariable Long id) {
        employeService.deleteEmploye(id);
        return "redirect:/employes";
    }
}