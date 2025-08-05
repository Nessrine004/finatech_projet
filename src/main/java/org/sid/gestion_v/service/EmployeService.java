package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.Employe;

import java.util.List;

public interface EmployeService {
    List<Employe> getAllEmployes();
    Employe getEmployeById(Long id);
    Employe createEmploye(Employe employe);
    Employe updateEmploye(Long id, Employe employe);
    void deleteEmploye(Long id);
}
