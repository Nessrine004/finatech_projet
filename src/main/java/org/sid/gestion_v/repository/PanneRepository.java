package org.sid.gestion_v.repository;

import org.sid.gestion_v.entities.Panne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PanneRepository extends JpaRepository<Panne, Long> {
    List<Panne> findByVehiculeId(Long vehiculeId);
    List<Panne> findByDatePrevue(LocalDate date);
    List<Panne> findByDateEffectuee(LocalDate date);
}
