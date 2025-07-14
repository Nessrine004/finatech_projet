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
public class Reservation {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Utilisateur demandeur;

    @ManyToOne
    private Vehicule vehicule;

    private LocalDate dateDemande;
    private String mission;
    private String client;
    private String lieu;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;
}

