package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Carburant;
import org.sid.gestion_v.service.CarburantService;
import org.sid.gestion_v.service.CarteCarburantService;
import org.sid.gestion_v.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CarburantViewController {

    private final CarburantService carburantService;
    private final CarteCarburantService carteCarburantService;
    private final UtilisateurService utilisateurService;

    @GetMapping("/carburants")
    public String listCarburants(Model model) {
        model.addAttribute("carburants", carburantService.getAll());
        return "carburant/list"; // à créer
    }

    @GetMapping("/ajouter-carburant")
    public String formAjoutCarburant(Model model) {
        model.addAttribute("carburant", new Carburant());
        model.addAttribute("cartes", carteCarburantService.getAll());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "carburant/form"; // à créer
    }

    @GetMapping("/supprimer-carburant/{id}")
    public String supprimerCarburant(@PathVariable Long id) {
        carburantService.delete(id);
        return "redirect:/carburants";
    }
    @PostMapping("/ajouter-carburant")
    public String enregistrerCarburant(
            @ModelAttribute Carburant carburant,
            @RequestParam("carteCarburantId") Long carteId,
            @RequestParam("effectueParId") Long userId,
            Model model
    ) {
        carburant.setCarteCarburant(carteCarburantService.getById(carteId));
        carburant.setEffectuePar(utilisateurService.getUtilisateurById(userId));

        carburantService.save(carburant);
        return "redirect:/carburants";
    }

}
