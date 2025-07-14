package org.sid.gestion_v.entities;


import lombok.Data;

import java.time.LocalDate;

@Data
public class DemandeAffectationForm {
    private String nom;
    private String email;
    private String mission;
    private String client;
    private String lieu;
    private LocalDate dateDebut;
    private LocalDate dateFin;
}
