package org.sid.gestion_v.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarteCarburant {

    @Id @GeneratedValue
    private Long id;

    private String numeroCarte;

    private String fournisseur;

    @OneToOne
    private Vehicule vehicule;
}

