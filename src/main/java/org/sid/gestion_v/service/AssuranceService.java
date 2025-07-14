package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.Assurance;
import java.util.List;

public interface AssuranceService {
    List<Assurance> getAllAssurances();
    Assurance getAssuranceById(Long id);
    Assurance createAssurance(Assurance a);
    Assurance updateAssurance(Long id, Assurance a);
    void deleteAssurance(Long id);
    void envoyerRappelsAssurance();

}
