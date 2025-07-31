package org.sid.gestion_v.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"pannes", "assurances", "affectations", "reservations"})
public class Vehicule {

    @Id
    @GeneratedValue
    private Long id;

    private String marque;
    private String modele;
    private String plaqueImmatriculation;
    private Double kilometrage;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;

    private String typeCarburant;

    private String typeVehicule;

    private String dateDebutLocation;
    private String dateFinLocation;

    @Lob
    private byte[] carteGrise;

    @Lob
    private byte[] assuranceDocument;

    @Lob
    private byte[] vignette;

    @Lob
    private byte[] visiteTechnique;

    @OneToMany(mappedBy = "vehicule")
    private List<Affectation> affectations;

    @OneToMany(mappedBy = "vehicule")
    private List<Panne> pannes;

    @OneToMany(mappedBy = "vehicule")
    private List<Assurance> assurances;

    @OneToMany(mappedBy = "vehicule")
    private List<Reservation> reservations;
}
