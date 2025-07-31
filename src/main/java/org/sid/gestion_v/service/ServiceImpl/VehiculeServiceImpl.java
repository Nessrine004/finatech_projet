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
public abstract class VehiculeServiceImpl implements VehiculeService {

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
    public Vehicule createVehicule(Vehicule vehicule,
                                   MultipartFile carteGrise,
                                   MultipartFile assurance,
                                   MultipartFile vignette,
                                   MultipartFile visiteTechnique) {
        try {
            if (carteGrise != null && !carteGrise.isEmpty()) vehicule.setCarteGrise(carteGrise.getBytes());
            if (assurance != null && !assurance.isEmpty()) vehicule.setAssuranceDocument(assurance.getBytes());
            if (vignette != null && !vignette.isEmpty()) vehicule.setVignette(vignette.getBytes());
            if (visiteTechnique != null && !visiteTechnique.isEmpty()) vehicule.setVisiteTechnique(visiteTechnique.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l’enregistrement des fichiers", e);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule updateVehicule(Long id, Vehicule vehicule,
                                   MultipartFile carteGrise,
                                   MultipartFile assurance,
                                   MultipartFile vignette,
                                   MultipartFile visiteTechnique) {
        Vehicule existing = getVehiculeById(id);
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setPlaqueImmatriculation(vehicule.getPlaqueImmatriculation());
        existing.setKilometrage(vehicule.getKilometrage());
        existing.setStatut(vehicule.getStatut());
        existing.setTypeCarburant(vehicule.getTypeCarburant());
        existing.setTypeVehicule(vehicule.getTypeVehicule());
        existing.setDateDebutLocation(vehicule.getDateDebutLocation());
        existing.setDateFinLocation(vehicule.getDateFinLocation());

        try {
            if (carteGrise != null && !carteGrise.isEmpty()) existing.setCarteGrise(carteGrise.getBytes());
            if (assurance != null && !assurance.isEmpty()) existing.setAssuranceDocument(assurance.getBytes());
            if (vignette != null && !vignette.isEmpty()) existing.setVignette(vignette.getBytes());
            if (visiteTechnique != null && !visiteTechnique.isEmpty()) existing.setVisiteTechnique(visiteTechnique.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la mise à jour des fichiers", e);
        }

        return vehiculeRepository.save(existing);
    }

    // ✅ Nouvelle méthode : pour MAJ SANS fichiers (utilisée dans le service d’affectation)
    @Override
    public Vehicule updateVehicule(Long id, Vehicule vehicule) {
        Vehicule existing = getVehiculeById(id);
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setPlaqueImmatriculation(vehicule.getPlaqueImmatriculation());
        existing.setKilometrage(vehicule.getKilometrage());
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