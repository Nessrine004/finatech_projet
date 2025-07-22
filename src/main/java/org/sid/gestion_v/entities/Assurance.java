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
public class Assurance {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Vehicule vehicule;

    private String compagnie;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    @ManyToOne
    private Utilisateur effectuePar;

    @Column(name = "type_usage")
    private String usage;

    private String proprietaire;
    private String adresse;
}

