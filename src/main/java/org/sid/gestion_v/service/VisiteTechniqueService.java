package org.sid.gestion_v.service;

import org.sid.gestion_v.entities.VisiteTechnique;

import java.util.List;

public interface VisiteTechniqueService {
    List<VisiteTechnique> getAll();
    VisiteTechnique getById(Long id);
    VisiteTechnique save(VisiteTechnique v);
    VisiteTechnique update(Long id, VisiteTechnique v);
    void delete(Long id);
}
