package org.sid.gestion_v.repository;

import org.sid.gestion_v.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si nécessaire plus tard
}
