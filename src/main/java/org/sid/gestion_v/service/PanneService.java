package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.Panne;

import java.util.List;

public interface PanneService {
    List<Panne> getAllPannes();
    Panne getPanneById(Long id);
    Panne createPanne(Panne panne);
    Panne updatePanne(Long id, Panne panne);
    void supprimerPanne(Long id);
}
