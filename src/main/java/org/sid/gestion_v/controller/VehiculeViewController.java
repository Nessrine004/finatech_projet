package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
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
        model.addAttribute("typesCarburant", TypeCarburant.values());
        model.addAttribute("typesVehicule", TypeVehicule.values());
        return "ajouterVehicule";
    }

    @PostMapping("/ajouter-vehicule")
    public String ajouterVehicule(@ModelAttribute Vehicule vehicule,
                                  @RequestParam("carteGriseFile") MultipartFile carteGriseFile,
                                  Model model) {
        try {
            if (!carteGriseFile.isEmpty()) {
                vehicule.setCarteGrise(carteGriseFile.getBytes());
            }
            vehiculeService.createVehicule(vehicule);
            return "redirect:/vehicules";
        } catch (IOException e) {
            model.addAttribute("error", "Erreur lors de l'ajout de la carte grise.");
            model.addAttribute("vehicule", vehicule);
            model.addAttribute("typesCarburant", TypeCarburant.values());
            model.addAttribute("typesVehicule", TypeVehicule.values());
            return "ajouterVehicule";
        }
    }

    @GetMapping("/modifier-vehicule/{id}")
    public String showModifierForm(@PathVariable Long id, Model model) {
        Vehicule vehicule = vehiculeService.getVehiculeById(id);
        model.addAttribute("vehicule", vehicule);
        model.addAttribute("validiteFormatted", vehicule.getValidite() != null ? vehicule.getValidite().toString() : "");
        model.addAttribute("dateDebutFormatted", vehicule.getDateDebutLocation() != null ? vehicule.getDateDebutLocation().toString() : "");
        model.addAttribute("dateFinFormatted", vehicule.getDateFinLocation() != null ? vehicule.getDateFinLocation().toString() : "");
        model.addAttribute("typesCarburant", TypeCarburant.values());
        model.addAttribute("typesVehicule", TypeVehicule.values());
        return "modifierVehicule";
    }

    @PostMapping("/modifier-vehicule")
    public String updateVehicule(@ModelAttribute Vehicule vehicule,
                                 @RequestParam("carteGriseFile") MultipartFile carteGriseFile) throws IOException {

        Vehicule existingVehicule = vehiculeService.getVehiculeById(vehicule.getId());

        // Si un nouveau fichier est envoyé, on le remplace
        if (!carteGriseFile.isEmpty()) {
            vehicule.setCarteGrise(carteGriseFile.getBytes());
        } else {
            // Sinon on conserve l’ancien
            vehicule.setCarteGrise(existingVehicule.getCarteGrise());
        }

        // Tu dois aussi conserver les listes s’il y en avait
        vehicule.setPannes(existingVehicule.getPannes());
        vehicule.setAssurances(existingVehicule.getAssurances());
        vehicule.setAffectations(existingVehicule.getAffectations());
        vehicule.setReservations(existingVehicule.getReservations());

        vehiculeService.saveVehicule(vehicule);
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

    @GetMapping(value = "/vehicules/carte-grise/{id}", produces = "image/jpeg")
    @ResponseBody
    public byte[] afficherCarteGrise(@PathVariable Long id) {
        return vehiculeService.getVehiculeById(id).getCarteGrise();
    }
}
