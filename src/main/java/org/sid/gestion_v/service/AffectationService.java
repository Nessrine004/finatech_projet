package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.Affectation;

import java.util.List;

public interface AffectationService {
    List<Affectation> getAllAffectations();
    Affectation getAffectationById(Long id);
    Affectation createAffectation(Affectation a);
    Affectation updateAffectation(Long id, Affectation a);
    void deleteAffectation(Long id);
}
