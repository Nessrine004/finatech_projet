package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Panne;
import org.sid.gestion_v.service.PanneService;
import org.sid.gestion_v.service.UtilisateurService;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PanneViewController {

    private final PanneService panneService;
    private final VehiculeService vehiculeService;
    private final UtilisateurService utilisateurService;

    @GetMapping("/pannes")
    public String afficherPannes(Model model) {
        model.addAttribute("pannes", panneService.getAllPannes());
        return "pannes/list"; // fichier list.html dans templates/pannes
    }

    @GetMapping("/ajouter-panne")
    public String formulaireAjout(Model model) {
        model.addAttribute("panne", new Panne());
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "pannes/form"; // fichier form.html dans templates/pannes
    }

    @PostMapping("/ajouter-panne")
    public String enregistrerPanne(@ModelAttribute Panne panne,
                                   @RequestParam("imageFile") MultipartFile imageFile,
                                   Model model) {
        try {
            if (!imageFile.isEmpty()) {
                panne.setImage(imageFile.getBytes());
            }
            panneService.createPanne(panne);
            return "redirect:/pannes";
        } catch (IOException e) {
            model.addAttribute("error", "Erreur lors de l'upload de l'image.");
            model.addAttribute("vehicules", vehiculeService.getAllVehicules());
            model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
            return "pannes/form";
        }
    }

    @GetMapping("/supprimer-panne/{id}")
    public String supprimerPanne(@PathVariable Long id) {
        panneService.supprimerPanne(id);
        return "redirect:/pannes";
    }
    @GetMapping(value = "/pannes/image/{id}", produces = "image/jpeg") // ou "image/png" si PNG
    @ResponseBody
    public byte[] afficherImage(@PathVariable Long id) {
        Panne panne = panneService.getPanneById(id);
        return panne.getImage();
    }


}
