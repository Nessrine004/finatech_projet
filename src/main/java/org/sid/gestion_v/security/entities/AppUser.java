package org.sid.gestion_v.security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    private String userId;

    private String nom;
    private String prenom;

    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<AppRole> roles = new ArrayList<>(); // ✅ initialise ici
}
