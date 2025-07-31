package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.StatutVehicule;
import org.sid.gestion_v.entities.TypeCarburant;
import org.sid.gestion_v.entities.TypeVehicule;
import org.sid.gestion_v.entities.Vehicule;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        model.addAttribute("typesCarburant", TypeCarburant.values());
        model.addAttribute("typesVehicule", TypeVehicule.values());
        return "ajouterVehicule";
    }

    @PostMapping("/ajouter-vehicule")
    public String ajouterVehicule(@ModelAttribute Vehicule vehicule,
                                  @RequestParam("carteGriseFile") MultipartFile carteGriseFile,
                                  @RequestParam("assuranceFile") MultipartFile assuranceFile,
                                  @RequestParam("vignetteFile") MultipartFile vignetteFile,
                                  @RequestParam("visiteTechniqueFile") MultipartFile visiteTechniqueFile) throws IOException {
        vehiculeService.createVehicule(vehicule, carteGriseFile, assuranceFile, vignetteFile, visiteTechniqueFile);
        return "redirect:/vehicules";
    }

    @GetMapping("/modifier-vehicule/{id}")
    public String showModifierForm(@PathVariable Long id, Model model) {
        Vehicule vehicule = vehiculeService.getVehiculeById(id);
        model.addAttribute("vehicule", vehicule);
        model.addAttribute("statuts", StatutVehicule.values());
        model.addAttribute("typesCarburant", TypeCarburant.values());
        model.addAttribute("typesVehicule", TypeVehicule.values());
        return "modifierVehicule";
    }

    @PostMapping("/modifier-vehicule")
    public String modifierVehicule(@ModelAttribute Vehicule vehicule,
                                   @RequestParam(value = "carteGriseFile", required = false) MultipartFile carteGriseFile,
                                   @RequestParam(value = "assuranceFile", required = false) MultipartFile assuranceFile,
                                   @RequestParam(value = "vignetteFile", required = false) MultipartFile vignetteFile,
                                   @RequestParam(value = "visiteTechniqueFile", required = false) MultipartFile visiteTechniqueFile) throws IOException {
        vehiculeService.updateVehicule(vehicule.getId(), vehicule, carteGriseFile, assuranceFile, vignetteFile, visiteTechniqueFile);
        return "redirect:/vehicules";
    }

    @PostMapping("/supprimer-vehicule/{id}")
    public String supprimerVehicule(@PathVariable Long id, Model model) {
        try {
            vehiculeService.deleteVehicule(id);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("vehicules", vehiculeService.getAllVehicules());
            return "vehicules";
        }
        return "redirect:/vehicules";
    }

    @GetMapping("/vehicules-readonly")
    public String listeVehiculesReadonly(Model model) {
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        return "vehicules-readonly";
    }
}
