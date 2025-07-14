package org.sid.gestion_v.controller;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Assurance;
import org.sid.gestion_v.service.AssuranceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assurances")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AssuranceController {
    private final AssuranceService assuranceService;

    @GetMapping
    public List<Assurance> getAll() {
        return assuranceService.getAllAssurances();
    }

    @GetMapping("/{id}")
    public Assurance getById(@PathVariable Long id) {
        return assuranceService.getAssuranceById(id);
    }

    @PostMapping
    public Assurance create(@RequestBody Assurance assurance) {
        return assuranceService.createAssurance(assurance);
    }

    @PutMapping("/{id}")
    public Assurance update(@PathVariable Long id, @RequestBody Assurance assurance) {
        return assuranceService.updateAssurance(id, assurance);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        assuranceService.deleteAssurance(id);
    }
}
