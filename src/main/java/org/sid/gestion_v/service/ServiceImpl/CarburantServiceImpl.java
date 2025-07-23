package org.sid.gestion_v.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Carburant;
import org.sid.gestion_v.repository.CarburantRepository;
import org.sid.gestion_v.service.CarburantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarburantServiceImpl implements CarburantService {

    private final CarburantRepository repository;

    @Override
    public Carburant save(Carburant carburant) {
        return repository.save(carburant);
    }

    @Override
    public List<Carburant> getAll() {
        return repository.findAll();
    }

    @Override
    public Carburant getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
