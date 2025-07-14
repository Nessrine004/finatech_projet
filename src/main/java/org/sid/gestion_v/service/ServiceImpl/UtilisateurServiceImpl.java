package org.sid.gestion_v.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Utilisateur;
import org.sid.gestion_v.repository.UtilisateurRepository;
import org.sid.gestion_v.service.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Override
    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateur) {
        Utilisateur u = getUtilisateurById(id);
        u.setNom(utilisateur.getNom());
        u.setPrenom(utilisateur.getPrenom());
        u.setEmail(utilisateur.getEmail());
        u.setMotDePasse(utilisateur.getMotDePasse());
        u.setRole(utilisateur.getRole());
        return utilisateurRepository.save(u);
    }

    @Override
    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
}
