package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    Reservation createReservation(Reservation r);
    Reservation updateReservation(Long id, Reservation r);
    void deleteReservation(Long id);
}
