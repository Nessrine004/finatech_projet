package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Affectation;
import org.sid.gestion_v.service.AffectationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affectations")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AffectationController {
    private final AffectationService affectationService;

    @GetMapping
    public List<Affectation> getAll() {
        return affectationService.getAllAffectations();
    }

    @GetMapping("/{id}")
    public Affectation getById(@PathVariable Long id) {
        return affectationService.getAffectationById(id);
    }

    @PostMapping
    public Affectation create(@RequestBody Affectation affectation) {
        return affectationService.createAffectation(affectation);
    }

    @PutMapping("/{id}")
    public Affectation update(@PathVariable Long id, @RequestBody Affectation affectation) {
        return affectationService.updateAffectation(id, affectation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        affectationService.deleteAffectation(id);
    }
}
