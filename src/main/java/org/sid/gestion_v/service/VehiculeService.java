package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.Vehicule;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VehiculeService {
    List<Vehicule> getAllVehicules();
    Vehicule getVehiculeById(Long id);

    Vehicule createVehicule(Vehicule vehicule,
                            MultipartFile carteGrise,
                            MultipartFile assurance,
                            MultipartFile vignette,
                            MultipartFile visiteTechnique);

    Vehicule updateVehicule(Long id, Vehicule vehicule,
                            MultipartFile carteGriseFile,
                            MultipartFile assuranceFile,
                            MultipartFile vignetteFile,
                            MultipartFile visiteTechniqueFile) throws IOException;

    Vehicule updateVehicule(Long id, Vehicule vehicule);

    void deleteVehicule(Long id);

    // Nouvelle méthode pour appeler les upload un par un
    void enregistrerDocumentsVehicule(Vehicule vehicule,
                                      MultipartFile carteGrise,
                                      MultipartFile assurance,
                                      MultipartFile vignette,
                                      MultipartFile visiteTechnique);
}