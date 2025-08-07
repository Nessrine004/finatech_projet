package org.sid.gestion_v.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Panne;
import org.sid.gestion_v.entities.StatutVehicule;
import org.sid.gestion_v.entities.Vehicule;
import org.sid.gestion_v.repository.PanneRepository;
import org.sid.gestion_v.repository.VehiculeRepository;
import org.sid.gestion_v.service.PanneService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PanneServiceImpl implements PanneService {

    private final PanneRepository panneRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    public List<Panne> getAllPannes() {
        return panneRepository.findAll();
    }

    @Override
    public Panne getPanneById(Long id) {
        return panneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Panne introuvable"));
    }

    @Override
    public Panne createPanne(Panne panne) {
        return panneRepository.save(panne);
    }

    @Override
    public Panne updatePanne(Long id, Panne panne) {
        Panne existing = getPanneById(id);
        existing.setType(panne.getType());
        existing.setMontant(panne.getMontant());
        existing.setDatePrevue(panne.getDatePrevue());
        existing.setDateEffectuee(panne.getDateEffectuee());
        existing.setCommentaire(panne.getCommentaire());
        existing.setVehicule(panne.getVehicule());
        existing.setImage(panne.getImage());
        return panneRepository.save(existing);
    }

    @Override
    public void supprimerPanne(Long id) {
        panneRepository.deleteById(id);
    }

}
