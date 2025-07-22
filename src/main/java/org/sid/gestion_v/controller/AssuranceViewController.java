package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Assurance;
import org.sid.gestion_v.service.AssuranceService;
import org.sid.gestion_v.service.UtilisateurService;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AssuranceViewController {

    private final AssuranceService assuranceService;
    private final VehiculeService vehiculeService;
    private final UtilisateurService utilisateurService;

    @GetMapping("/assurances")
    public String listAssurances(Model model) {
        model.addAttribute("assurances", assuranceService.getAllAssurances());
        return "assurance/list";
    }

    @GetMapping("/ajouter-assurance")
    public String showAddForm(Model model) {
        model.addAttribute("assurance", new Assurance());
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "assurance/form";
    }

    @GetMapping("/modifier-assurance/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("assurance", assuranceService.getAssuranceById(id));
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "assurance/form";
    }

    @PostMapping("/supprimer-assurance/{id}")
    public String deleteAssurance(@PathVariable Long id) {
        assuranceService.delete(id);
        return "redirect:/assurances";
    }

    @GetMapping("/assurances-readonly")
    public String afficherAssurancesReadonly(Model model) {
        model.addAttribute("assurances", assuranceService.getAllAssurances());
        return "assurances-readonly";
    }

    @GetMapping("/assurances/form")
    public String afficherFormulaireAssurance(Model model) {
        Assurance assurance = new Assurance();
        model.addAttribute("assurance", assurance);
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());

        String titreFormulaire = (assurance.getId() == null) ? "Ajouter une assurance" : "Modifier l'assurance";
        model.addAttribute("formTitle", titreFormulaire);

        return "assurance/form";
    }

    @PostMapping("/assurances/save")
    public String ajouterAssurance(@ModelAttribute Assurance assurance, Model model) {
        try {
            assuranceService.save(assurance);  // si assurance.id ≠ null => modification
            return "redirect:/assurances";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erreur lors de l'enregistrement.");
            model.addAttribute("vehicules", vehiculeService.getAllVehicules());
            model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
            return "assurance/form";
        }
    }


}
