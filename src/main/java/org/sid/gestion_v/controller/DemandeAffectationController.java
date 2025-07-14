package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.DemandeAffectationForm;
import org.sid.gestion_v.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class DemandeAffectationController {

    private final EmailService emailService;

    @GetMapping("/demande-affectation")
    public String showForm(Model model) {
        model.addAttribute("demande", new DemandeAffectationForm());
        return "demandeAffectation";
    }

    @PostMapping("/demande-affectation")
    public String submitForm(@ModelAttribute("demande") DemandeAffectationForm demande, Model model) {
        try {
            String subject = "Nouvelle demande d'affectation";
            String content = "Nom : " + demande.getNom() +
                    "\nEmail : " + demande.getEmail() +
                    "\nMission : " + demande.getMission() +
                    "\nClient : " + demande.getClient() +
                    "\nLieu : " + demande.getLieu();

            emailService.sendEmail("nissrine.bekri@gmail.com", subject, content);

            model.addAttribute("success", " Votre demande a été envoyée avec succès !");
            return "demandeAffectation";
        } catch (Exception e) {
            model.addAttribute("error", " Erreur lors de l'envoi : " + e.getMessage());
            return "demandeAffectation";
        }
    }
}
