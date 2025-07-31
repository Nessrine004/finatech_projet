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
        existing.setJustificatif(a.getJustificatif());
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




}
