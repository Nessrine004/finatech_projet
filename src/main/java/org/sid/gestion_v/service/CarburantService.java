package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.Carburant;

import java.util.List;

public interface CarburantService {
    Carburant save(Carburant carburant);
    List<Carburant> getAll();
    Carburant getById(Long id);
    
    void delete(Long id);
}
