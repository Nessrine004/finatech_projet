package org.sid.gestion_v.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Utilisateur {

        @Id
        @GeneratedValue
        private Long id;

        private String nom;
        private String prenom;

        @Column(nullable = false)
        private String email;
        private String motDePasse;





    @Enumerated(EnumType.STRING)
        private Role role;

        @OneToMany(mappedBy = "utilisateur")
        private List<Affectation> affectations;

        @OneToMany(mappedBy = "demandeur")
        private List<Reservation> reservations;
    @Override
    public String toString() {
        return prenom + " " + nom;
    }



}

