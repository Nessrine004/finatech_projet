package org.sid.gestion_v.repository;

import org.sid.gestion_v.entities.Reservation;
import org.sid.gestion_v.entities.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDemandeurId(Long utilisateurId);
    List<Reservation> findByStatut(StatutReservation statut);
}

