package org.sid.gestion_v.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Affectation;
import org.sid.gestion_v.entities.Utilisateur;
import org.sid.gestion_v.entities.Vehicule;
import org.sid.gestion_v.repository.AffectationRepository;
import org.sid.gestion_v.service.AffectationService;
import org.sid.gestion_v.service.EmailService;
import org.sid.gestion_v.service.UtilisateurService;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AffectationServiceImpl implements AffectationService {
    private final AffectationRepository affectationRepository;
    private final VehiculeService vehiculeService;
    private final EmailService emailService;
    private final UtilisateurService utilisateurService;

    @Override
    public List<Affectation> getAllAffectations() {
        return affectationRepository.findAll();
    }

    @Override
    public Affectation getAffectationById(Long id) {
        return affectationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affectation introuvable"));
    }

    @Override
    public Affectation createAffectation(Affectation a) {
        verifierDates(a);
        verifierDisponibiliteVehicule(a);
        verifierDisponibiliteUtilisateur(a);

        // 🔄 Récupérer les objets complets
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(a.getUtilisateur().getId());
        Vehicule vehicule = vehiculeService.getVehiculeById(a.getVehicule().getId());

        a.setUtilisateur(utilisateur);
        a.setVehicule(vehicule);

        Affectation saved = affectationRepository.save(a);

        // 📧 Envoi d’e-mail
        String destinataire = utilisateur.getEmail();
        if (destinataire == null || destinataire.isBlank()) {
            throw new RuntimeException("L'utilisateur n’a pas d’adresse email renseignée.");
        }

        String sujet = "Affectation d’un véhicule";
        String contenuHtml = """
            <h2>Bonjour %s,</h2>
            <p>Vous avez été affecté au véhicule suivant :</p>
            <ul>
              <li><strong>Véhicule :</strong> %s %s</li>
              <li><strong>Immatriculation :</strong> %s</li>
              <li><strong>Période :</strong> %s ➝ %s</li>
            </ul>
            <p>Merci de prendre contact avec votre gestionnaire pour plus de détails.</p>
        """.formatted(
                utilisateur.getNom(),
                vehicule.getMarque(), vehicule.getModele(),
                vehicule.getPlaqueImmatriculation(),
                a.getDateDebut(), a.getDateFin()
        );

        emailService.sendHtmlEmail(destinataire, sujet, contenuHtml);

        return saved;
    }

    @Override
    public Affectation updateAffectation(Long id, Affectation a) {
        Affectation existing = getAffectationById(id);
        existing.setDateDebut(a.getDateDebut());
        existing.setDateFin(a.getDateFin());
        existing.setUtilisateur(a.getUtilisateur());
        existing.setVehicule(a.getVehicule());

        verifierDates(existing);
        verifierDisponibiliteVehicule(existing);
        verifierDisponibiliteUtilisateur(existing);

        return affectationRepository.save(existing);
    }

    @Override
    public void deleteAffectation(Long id) {
        affectationRepository.deleteById(id);
    }

    // 📅 Vérifie cohérence des dates
    private void verifierDates(Affectation a) {
        if (a.getDateDebut().isAfter(a.getDateFin())) {
            throw new RuntimeException("La date de début doit précéder la date de fin.");
        }
    }

    // 🚗 Vérifie qu’aucune affectation ne chevauche la période
    private void verifierDisponibiliteVehicule(Affectation nouvelle) {
        List<Affectation> existantes = affectationRepository.findByVehiculeId(nouvelle.getVehicule().getId());

        for (Affectation a : existantes) {
            boolean chevauchement = !(nouvelle.getDateFin().isBefore(a.getDateDebut()) ||
                    nouvelle.getDateDebut().isAfter(a.getDateFin()));
            if (chevauchement && (nouvelle.getId() == null || !a.getId().equals(nouvelle.getId()))) {
                throw new RuntimeException("Ce véhicule est déjà affecté pendant cette période.");
            }
        }
    }

    private void verifierDisponibiliteUtilisateur(Affectation nouvelle) {
        List<Affectation> existantes = affectationRepository.findByUtilisateurId(nouvelle.getUtilisateur().getId());

        for (Affectation a : existantes) {
            boolean chevauchement = !(nouvelle.getDateFin().isBefore(a.getDateDebut()) ||
                    nouvelle.getDateDebut().isAfter(a.getDateFin()));
            if (chevauchement && !a.getId().equals(nouvelle.getId())) {
                throw new RuntimeException("Cet utilisateur est déjà affecté pendant cette période.");
            }
        }
    }
}
