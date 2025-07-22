package org.sid.gestion_v.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Assurance;
import org.sid.gestion_v.service.AssuranceService;
import org.sid.gestion_v.service.UtilisateurService;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

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

    @PostMapping("/ajouter-assurance")
    public String ajouterAssurance(@ModelAttribute Assurance assurance,
                                   @RequestParam("fichierJustificatif") MultipartFile fichier,
                                   Model model) {
        try {
            if (!fichier.isEmpty()) {
                assurance.setJustificatif(fichier.getBytes());
            }
            assuranceService.save(assurance);
            return "redirect:/assurances";
        } catch (IOException e) {
            model.addAttribute("error", "Erreur lors du téléchargement du fichier.");
            model.addAttribute("vehicules", vehiculeService.getAllVehicules());
            model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
            return "assurance/form";
        }
    }

    @GetMapping("/modifier-assurance/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("assurance", assuranceService.getAssuranceById(id));
        model.addAttribute("vehicules", vehiculeService.getAllVehicules());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "assurance/form";
    }

    @PostMapping("/modifier-assurance/{id}")
    public String modifierAssurance(@PathVariable Long id,
                                    @ModelAttribute Assurance assurance,
                                    @RequestParam("fichierJustificatif") MultipartFile fichier,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (!fichier.isEmpty()) {
                assurance.setJustificatif(fichier.getBytes());
            } else {
                Assurance ancienne = assuranceService.getAssuranceById(id);
                assurance.setJustificatif(ancienne.getJustificatif());
            }
            assurance.setId(id);
            assuranceService.save(assurance);
            redirectAttributes.addFlashAttribute("success", "Assurance modifiée !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la modification.");
        }
        return "redirect:/assurances";
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

    @GetMapping(value = "/assurances/justificatif/{id}", produces = "image/jpeg")
    @ResponseBody
    public byte[] afficherJustificatif(@PathVariable Long id) {
        return assuranceService.getAssuranceById(id).getJustificatif();
    }
}
