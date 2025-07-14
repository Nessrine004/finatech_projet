package org.sid.gestion_v.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Entretien;
import org.sid.gestion_v.entities.StatutVehicule;
import org.sid.gestion_v.entities.Utilisateur;
import org.sid.gestion_v.entities.Vehicule;
import org.sid.gestion_v.repository.EntretienRepository;
import org.sid.gestion_v.repository.VehiculeRepository;
import org.sid.gestion_v.service.EmailService;
import org.sid.gestion_v.service.EntretienService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntretienServiceImpl implements EntretienService {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final VehiculeRepository vehiculeRepository;

    @Override
    public List<Entretien> getAllEntretiens() {
        return entretienRepository.findAll();
    }

    @Override
    public Entretien getEntretienById(Long id) {
        return entretienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entretien introuvable"));
    }

    @Override
    public Entretien createEntretien(Entretien e) {
        return entretienRepository.save(e);
    }

    @Override
    public Entretien updateEntretien(Long id, Entretien e) {
        Entretien existing = getEntretienById(id);
        existing.setType(e.getType());
        existing.setDatePrevue(e.getDatePrevue());
        existing.setDateEffectuee(e.getDateEffectuee());
        existing.setCommentaire(e.getCommentaire());
        existing.setVehicule(e.getVehicule());
        return entretienRepository.save(existing);
    }

    @Override
    public void deleteEntretien(Long id) {
        entretienRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 9 * * *") // Tous les jours à 9h
    public void envoyerRappelsEntretiens() {
        LocalDate dansDeuxJours = LocalDate.now().plusDays(2);
        List<Entretien> entretiens = entretienRepository.findByDatePrevue(dansDeuxJours);

        for (Entretien e : entretiens) {
            if (e.getEffectuePar() != null && e.getEffectuePar().getEmail() != null) {

                String to = e.getEffectuePar().getEmail();
                String subject = "🔔 Rappel entretien prévu le " + e.getDatePrevue();

                String html = """
                <html>
                    <body>
                        <p>Bonjour <strong>%s %s</strong>,</p>
                        <p>Un entretien est prévu dans <strong>2 jours</strong> pour le véhicule suivant :</p>
                        <ul>
                            <li><strong>Véhicule :</strong> %s %s (%s)</li>
                            <li><strong>Type :</strong> %s</li>
                            <li><strong>Date prévue :</strong> %s</li>
                        </ul>
                        <p>Merci de bien vouloir vous organiser en conséquence.</p>
                        <p style="color:gray;">– Application Gestion des Véhicules</p>
                    </body>
                </html>
            """.formatted(
                        e.getEffectuePar().getPrenom(),
                        e.getEffectuePar().getNom(),
                        e.getVehicule().getMarque(),
                        e.getVehicule().getModele(),
                        e.getVehicule().getPlaqueImmatriculation(),
                        e.getType(),
                        e.getDatePrevue().toString()
                );

                // ✅ Appel correct du service
                emailService.sendHtmlEmail(to, subject, html);
            }
        }
    }
    @Scheduled(cron = "0 30 8 * * *") // Tous les jours à 8h30
    public void mettreAJourStatutVehiculesPourMaintenance() {
        LocalDate aujourdHui = LocalDate.now();
        List<Entretien> entretiens = entretienRepository.findByDatePrevue(aujourdHui);

        for (Entretien e : entretiens) {
            Vehicule v = e.getVehicule();
            if (v != null && v.getStatut() != StatutVehicule.EN_MAINTENANCE) {
                v.setStatut(StatutVehicule.EN_MAINTENANCE);
                vehiculeRepository.save(v);
                System.out.println("🔧 Statut du véhicule " + v.getId() + " mis à jour → EN_MAINTENANCE");
            }
        }
    }
    @Scheduled(cron = "0 0 9 * * *") // tous les jours à 9h
    public void remettreVehiculesDisponibleApresEntretien() {
        LocalDate aujourdHui = LocalDate.now();
        List<Entretien> entretiens = entretienRepository.findByDateEffectuee(aujourdHui);

        for (Entretien e : entretiens) {
            Vehicule v = e.getVehicule();
            if (v != null && v.getStatut() == StatutVehicule.EN_MAINTENANCE) {
                v.setStatut(StatutVehicule.LIBRE);
                vehiculeRepository.save(v);
                System.out.println("✅ Véhicule " + v.getId() + " remis en DISPONIBLE après entretien.");
            }
        }
    }



}
