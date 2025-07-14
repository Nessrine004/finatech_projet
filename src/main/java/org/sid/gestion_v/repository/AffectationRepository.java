package org.sid.gestion_v.repository;

import org.sid.gestion_v.entities.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AffectationRepository extends JpaRepository<Affectation, Long> {
    List<Affectation> findByUtilisateurId(Long utilisateurId);
    List<Affectation> findByVehiculeId(Long vehiculeId);
}

