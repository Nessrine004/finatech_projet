package org.sid.gestion_v.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.VisiteTechnique;
import org.sid.gestion_v.repository.VisiteTechniqueRepository;
import org.sid.gestion_v.service.VisiteTechniqueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisiteTechniqueServiceImpl implements VisiteTechniqueService {

    private final VisiteTechniqueRepository visiteTechniqueRepository;

    @Override
    public List<VisiteTechnique> getAll() {
        return visiteTechniqueRepository.findAll();
    }

    @Override
    public VisiteTechnique getById(Long id) {
        return visiteTechniqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visite technique introuvable"));
    }

    @Override
    public VisiteTechnique save(VisiteTechnique v) {
        return visiteTechniqueRepository.save(v);
    }

    @Override
    public VisiteTechnique update(Long id, VisiteTechnique v) {
        VisiteTechnique existing = getById(id);
        existing.setDateVisite(v.getDateVisite());
        existing.setLieu(v.getLieu());
        existing.setCommentaire(v.getCommentaire());
        existing.setJustificatif(v.getJustificatif());
        existing.setVehicule(v.getVehicule());
        existing.setEffectuePar(v.getEffectuePar());
        return visiteTechniqueRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        visiteTechniqueRepository.deleteById(id);
    }
}
