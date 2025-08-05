package org.sid.gestion_v.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Vehicule;
import org.sid.gestion_v.repository.VehiculeRepository;
import org.sid.gestion_v.service.VehiculeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;

    @Override
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule getVehiculeById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));
    }

    @Override
    public Vehicule createVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }


    @Override
    public Vehicule updateVehicule(Long id, Vehicule vehicule) {
        Vehicule existing = getVehiculeById(id);
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setPlaqueImmatriculation(vehicule.getPlaqueImmatriculation());
        existing.setValidite(vehicule.getValidite());
        existing.setStatut(vehicule.getStatut());
        existing.setTypeCarburant(vehicule.getTypeCarburant());
        existing.setTypeVehicule(vehicule.getTypeVehicule());
        existing.setDateDebutLocation(vehicule.getDateDebutLocation());
        existing.setDateFinLocation(vehicule.getDateFinLocation());
        return vehiculeRepository.save(existing);
    }

    @Override
    public void deleteVehicule(Long id) {
        Vehicule v = vehiculeRepository.findById(id).orElseThrow();
        if (!v.getAffectations().isEmpty()) {
            throw new RuntimeException("Vous ne pouvez pas supprimer ce véhicule car il est encore affecté.");
        }
        vehiculeRepository.delete(v);
    }
}
