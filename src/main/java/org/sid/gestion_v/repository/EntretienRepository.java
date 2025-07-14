package org.sid.gestion_v.repository;

import org.sid.gestion_v.entities.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EntretienRepository extends JpaRepository<Entretien, Long> {
    List<Entretien> findByVehiculeId(Long vehiculeId);
    List<Entretien> findByDatePrevue(LocalDate date);
    List<Entretien> findByDateEffectuee(LocalDate date);





}

