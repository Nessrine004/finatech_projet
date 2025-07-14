package org.sid.gestion_v.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.sid.gestion_v.entities.Reservation;
import org.sid.gestion_v.repository.ReservationRepository;
import org.sid.gestion_v.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));
    }

    @Override
    public Reservation createReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public Reservation updateReservation(Long id, Reservation r) {
        Reservation existing = getReservationById(id);
        existing.setDateDemande(r.getDateDemande());
        existing.setMission(r.getMission());
        existing.setClient(r.getClient());
        existing.setLieu(r.getLieu());
        existing.setStatut(r.getStatut());
        existing.setDemandeur(r.getDemandeur());
        existing.setVehicule(r.getVehicule());
        return reservationRepository.save(existing);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
