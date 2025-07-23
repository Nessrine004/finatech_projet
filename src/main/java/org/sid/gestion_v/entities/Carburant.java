package org.sid.gestion_v.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carburant {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private double montant;
    private int kilometrage;
    private String lieu;

    @ManyToOne
    private Utilisateur effectuePar;

    @ManyToOne
    private CarteCarburant carteCarburant;
}

