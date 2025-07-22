package org.sid.gestion_v.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Assurance;
import org.sid.gestion_v.entities.Utilisateur;
import org.sid.gestion_v.repository.AssuranceRepository;
import org.sid.gestion_v.service.AssuranceService;
import org.sid.gestion_v.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssuranceServiceImpl implements AssuranceService {

    private final AssuranceRepository assuranceRepository;
    private final EmailService emailService;

    @Override
    public List<Assurance> getAllAssurances() {
        return assuranceRepository.findAll();
    }

    @Override
    public Assurance getAssuranceById(Long id) {
        return assuranceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assurance introuvable"));
    }

    @Override
    public Assurance createAssurance(Assurance a) {
        return assuranceRepository.save(a);
    }

    @Override
    public Assurance updateAssurance(Long id, Assurance a) {
        Assurance existing = getAssuranceById(id);
        existing.setDateDebut(a.getDateDebut());
        existing.setDateFin(a.getDateFin());
        existing.setCompagnie(a.getCompagnie());
        existing.setVehicule(a.getVehicule());
        return assuranceRepository.save(existing);
    }

    @Override
    public void deleteAssurance(Long id) {
        assuranceRepository.deleteById(id);
    }

    // ✅ Méthode utilisée dans le controller (ajout direct / modification)
    @Override
    public void save(Assurance assurance) {
        assuranceRepository.save(assurance);
    }

    // ✅ Méthode utilisée dans le controller (suppression)
    @Override
    public void delete(Long id) {
        assuranceRepository.deleteById(id);
    }

    // 📩 Rappel automatique
    @Scheduled(cron = "0 10 8 * * *") // tous les jours à 8h10
    public void envoyerRappelsAssurance() {
        LocalDate dans2Jours = LocalDate.now().plusDays(2);
        List<Assurance> assurances = assuranceRepository.findByDateFin(dans2Jours);

        for (Assurance a : assurances) {
            Utilisateur u = a.getEffectuePar();
            if (u != null && u.getEmail() != null) {
                String to = u.getEmail();
                String subject = "🔔 Rappel : Fin d’assurance dans 2 jours";

                String html = """
                <html>
                    <body>
                        <p>Bonjour <strong>%s %s</strong>,</p>
                        <p>La police d’assurance suivante arrive à expiration dans 2 jours :</p>
                        <ul>
                            <li><strong>Compagnie :</strong> %s</li>
                            <li><strong>Véhicule :</strong> %s %s (%s)</li>
                            <li><strong>Date de fin :</strong> %s</li>
                        </ul>
                        <p>Merci de procéder au renouvellement.</p>
                        <p style="color:gray;">– Application Gestion des Véhicules</p>
                    </body>
                </html>
            """.formatted(
                        u.getPrenom(), u.getNom(),
                        a.getCompagnie(),
                        a.getVehicule().getMarque(),
                        a.getVehicule().getModele(),
                        a.getVehicule().getPlaqueImmatriculation(),
                        a.getDateFin()
                );

                emailService.sendHtmlEmail(to, subject, html);
                System.out.println("📧 Rappel assurance envoyé à " + to);
            }
        }
    }
}
