package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.Entretien;

import java.util.List;

public interface EntretienService {
    List<Entretien> getAllEntretiens();
    Entretien getEntretienById(Long id);
    Entretien createEntretien(Entretien e);
    Entretien updateEntretien(Long id, Entretien e);
    void deleteEntretien(Long id);
    void envoyerRappelsEntretiens();
    void mettreAJourStatutVehiculesPourMaintenance();
    void remettreVehiculesDisponibleApresEntretien();



}
