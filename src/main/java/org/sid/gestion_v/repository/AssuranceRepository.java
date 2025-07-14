package org.sid.gestion_v.repository;

import org.sid.gestion_v.entities.Assurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AssuranceRepository extends JpaRepository<Assurance, Long> {
    List<Assurance> findByVehiculeId(Long vehiculeId);
    List<Assurance> findByDateFin(LocalDate date);

}

