package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.Vehicule;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface VehiculeService {
    List<Vehicule> getAllVehicules();
    Vehicule getVehiculeById(Long id);
    List<Vehicule> getVehiculesDisponibles(LocalDate dateDebut, LocalDate dateFin);
    Vehicule createVehicule(Vehicule vehicule);
    Vehicule updateVehicule(Long id, Vehicule vehicule);
    Vehicule saveVehicule(Vehicule vehicule);

    void deleteVehicule(Long id);
}
