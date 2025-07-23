package org.sid.gestion_v.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.CarteCarburant;
import org.sid.gestion_v.repository.CarteCarburantRepository;
import org.sid.gestion_v.service.CarteCarburantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarteCarburantServiceImpl implements CarteCarburantService {

    private final CarteCarburantRepository repository;

    @Override
    public CarteCarburant save(CarteCarburant carte) {
        return repository.save(carte);
    }

    @Override
    public List<CarteCarburant> getAll() {
        return repository.findAll();
    }

    @Override
    public CarteCarburant getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
