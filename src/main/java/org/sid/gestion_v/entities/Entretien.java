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
public class Entretien {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Vehicule vehicule;

    private String type;
    private LocalDate datePrevue;
    private LocalDate dateEffectuee;
    private String commentaire;
    @ManyToOne
    private Utilisateur effectuePar;


}

