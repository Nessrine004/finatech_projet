package org.sid.gestion_v.repository;

import org.sid.gestion_v.entities.StatutVehicule;
import org.sid.gestion_v.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    Vehicule save(Vehicule vehicule);

}

