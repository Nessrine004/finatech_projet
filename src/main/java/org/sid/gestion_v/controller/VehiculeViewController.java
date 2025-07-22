package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.StatutVehicule;
import org.sid.gestion_v.entities.Vehicule;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class VehiculeViewController {

    private final VehiculeService vehiculeService;

    @GetMapping("/vehicules")
    public String listVehicules(Model model) {
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        return "vehicules";
    }

    @GetMapping("/ajouter-vehicule")
    public String showAjoutForm(Model model) {
        model.addAttribute("vehicule", new Vehicule());
        model.addAttribute("statuts", StatutVehicule.values());
        return "ajouterVehicule";
    }

    @PostMapping("/ajouter-vehicule")
    public String ajouterVehicule(@ModelAttribute Vehicule vehicule) {
        vehiculeService.createVehicule(vehicule);
        return "redirect:/vehicules";
    }

    @GetMapping("/modifier-vehicule/{id}")
    public String showModifierForm(@PathVariable Long id, Model model) {
        Vehicule vehicule = vehiculeService.getVehiculeById(id);
        model.addAttribute("vehicule", vehicule);
        model.addAttribute("statuts", StatutVehicule.values());
        return "modifierVehicule";
    }

    @PostMapping("/modifier-vehicule")
    public String modifierVehicule(@ModelAttribute Vehicule vehicule) {
        vehiculeService.updateVehicule(vehicule.getId(), vehicule);
        return "redirect:/vehicules";
    }
    @PostMapping("/supprimer-vehicule/{id}")
    public String supprimerVehicule(@PathVariable Long id, Model model) {
        try {
            vehiculeService.deleteVehicule(id);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("vehicules", vehiculeService.getAllVehicules()); // pour recharger la liste
            return "vehicules"; // retourne à ta page de liste de véhicules
        }
        return "redirect:/vehicules";
    }
    @GetMapping("/vehicules-readonly")
    public String listeVehiculesReadonly(Model model) {
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        return "vehicules-readonly";
    }

}
