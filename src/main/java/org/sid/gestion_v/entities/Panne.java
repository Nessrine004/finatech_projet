package org.sid.gestion_v.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "vehicule")
public class Panne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Double montant;

    private LocalDate datePrevue;
    private LocalDate dateEffectuee;

    private String commentaire;

    @Lob
    private byte[] image;

    @ManyToOne
    private Vehicule vehicule;

    @ManyToOne
    private Utilisateur effectuePar;
}
