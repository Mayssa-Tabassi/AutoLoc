package tn.esprit.autoloc_api.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc_api.domain.Reservation;
import tn.esprit.autoloc_api.dto.ReservationRequestDTO;
import tn.esprit.autoloc_api.dto.ReservationResponseDTO;

import java.util.List;

@Component
public class ReservationMapper {

    /** Le véhicule et le client sont résolus par le service. */
    public Reservation toEntity(ReservationRequestDTO dto) {
        Reservation reservation = new Reservation();
        updateEntity(dto, reservation);
        return reservation;
    }

    public void updateEntity(ReservationRequestDTO dto, Reservation reservation) {
        reservation.setDateDebut(dto.dateDebut());
        reservation.setDateFin(dto.dateFin());
        reservation.setStatut(dto.statut());
    }

    public ReservationResponseDTO toResponse(Reservation r) {
        return new ReservationResponseDTO(r.getIdReservation(), r.getDateDebut(), r.getDateFin(),
                r.getStatut(),
                r.getVehicule() == null ? null : r.getVehicule().getIdVehicule(),
                r.getClient() == null ? null : r.getClient().getIdClient(),
                r.getContrat() == null ? null : r.getContrat().getIdContrat());
    }

    public List<ReservationResponseDTO> toResponseList(List<Reservation> reservations) {
        return reservations.stream().map(this::toResponse).toList();
    }
}
