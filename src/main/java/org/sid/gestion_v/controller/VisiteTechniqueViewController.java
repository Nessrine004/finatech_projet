package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.VisiteTechnique;
import org.sid.gestion_v.service.UtilisateurService;
import org.sid.gestion_v.service.VehiculeService;
import org.sid.gestion_v.service.VisiteTechniqueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class VisiteTechniqueViewController {

    private final VisiteTechniqueService visiteTechniqueService;
    private final VehiculeService vehiculeService;
    private final UtilisateurService utilisateurService;

    @GetMapping("/visites-techniques")
    public String afficherVisites(Model model) {
        model.addAttribute("visites", visiteTechniqueService.getAll());
        return "visites/list";
    }

    @GetMapping("/ajouter-visite-technique")
    public String formulaireAjout(Model model) {
        model.addAttribute("visiteTechnique", new VisiteTechnique());
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "visites/form";
    }

    @PostMapping("/ajouter-visite-technique")
    public String enregistrerVisite(@ModelAttribute VisiteTechnique visiteTechnique,
                                    @RequestParam("justificatifFile") MultipartFile justificatifFile,
                                    Model model) {
        try {
            if (!justificatifFile.isEmpty()) {
                visiteTechnique.setJustificatif(justificatifFile.getBytes());
            }
            visiteTechniqueService.save(visiteTechnique);
            return "redirect:/visites-techniques";
        } catch (IOException e) {
            model.addAttribute("error", "Erreur lors du téléchargement du fichier.");
            model.addAttribute("vehicules", vehiculeService.getAllVehicules());
            model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
            return "visites/form";
        }
    }

    @GetMapping("/supprimer-visite-technique/{id}")
    public String supprimerVisite(@PathVariable Long id) {
        visiteTechniqueService.delete(id);
        return "redirect:/visites-techniques";
    }

    @GetMapping(value = "/visites-techniques/justificatif/{id}", produces = "image/jpeg")
    @ResponseBody
    public byte[] afficherJustificatif(@PathVariable Long id) {
        return visiteTechniqueService.getById(id).getJustificatif();
    }
}
