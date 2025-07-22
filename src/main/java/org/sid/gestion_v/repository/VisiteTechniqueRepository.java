package org.sid.gestion_v.repository;

import org.sid.gestion_v.entities.VisiteTechnique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisiteTechniqueRepository extends JpaRepository<VisiteTechnique, Long> {
    List<VisiteTechnique> findByVehiculeId(Long vehiculeId);
    List<VisiteTechnique> findByDateVisite(LocalDate dateVisite);
}
