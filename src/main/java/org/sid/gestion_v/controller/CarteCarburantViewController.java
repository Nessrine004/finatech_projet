package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.CarteCarburant;
import org.sid.gestion_v.service.CarteCarburantService;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CarteCarburantViewController {

    private final CarteCarburantService carteCarburantService;
    private final VehiculeService vehiculeService;

    @GetMapping("/cartes-carburant")
    public String listCartes(Model model) {
        model.addAttribute("cartes", carteCarburantService.getAll());
        return "carteCarburant/list"; // à créer
    }

    @GetMapping("/ajouter-carte-carburant")
    public String formAjout(Model model) {
        model.addAttribute("carteCarburant", new CarteCarburant());
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        return "carteCarburant/form"; // à créer
    }

    @PostMapping("/ajouter-carte-carburant")
    public String enregistrerCarte(@ModelAttribute CarteCarburant carte) {
        carteCarburantService.save(carte);
        return "redirect:/cartes-carburant";
    }

    @GetMapping("/supprimer-carte-carburant/{id}")
    public String supprimerCarte(@PathVariable Long id) {
        carteCarburantService.delete(id);
        return "redirect:/cartes-carburant";
    }
}
