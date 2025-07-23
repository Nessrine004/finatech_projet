package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.CarteCarburant;

import java.util.List;

public interface CarteCarburantService {
    CarteCarburant save(CarteCarburant carte);
    List<CarteCarburant> getAll();
    CarteCarburant getById(Long id);
    void delete(Long id);
}
