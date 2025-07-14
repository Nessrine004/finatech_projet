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
public class Affectation {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Vehicule vehicule;

    private LocalDate dateDebut;
    private LocalDate dateFin;
}

