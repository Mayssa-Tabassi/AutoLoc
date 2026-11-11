package tn.esprit.autoloc_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc_api.domain.Contrat;
import tn.esprit.autoloc_api.domain.Reservation;
import tn.esprit.autoloc_api.dto.ContratRequestDTO;
import tn.esprit.autoloc_api.dto.ContratResponseDTO;
import tn.esprit.autoloc_api.exception.ConflictException;
import tn.esprit.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc_api.mapper.ContratMapper;
import tn.esprit.autoloc_api.repository.IContratRepository;
import tn.esprit.autoloc_api.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper contratMapper;

    @Override
    @Transactional
    public ContratResponseDTO create(ContratRequestDTO dto) {
        Reservation reservation = chargerReservation(dto.reservationId());
        verifierReservationLibre(reservation, null);

        Contrat contrat = contratMapper.toEntity(dto);
        contrat.setReservation(reservation);

        return contratMapper.toResponse(contratRepository.save(contrat));
    }

    @Override
    @Transactional(readOnly = true)
    public ContratResponseDTO findById(Long id) {
        return contratMapper.toResponse(chargerContrat(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContratResponseDTO> findAll() {
        return contratMapper.toResponseList(contratRepository.findAll());
    }

    @Override
    @Transactional
    public ContratResponseDTO update(Long id, ContratRequestDTO dto) {
        Contrat existant = chargerContrat(id);

        Reservation reservation = chargerReservation(dto.reservationId());
        verifierReservationLibre(reservation, id);

        contratMapper.updateEntity(dto, existant);
        existant.setReservation(reservation);

        return contratMapper.toResponse(contratRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }

        contratRepository.deleteById(id);
    }

    private Contrat chargerContrat(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    private Reservation chargerReservation(Long idReservation) {
        return reservationRepository.findById(idReservation)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Réservation", idReservation));
    }

    /** Une réservation ne peut être liée qu'à un seul contrat (OneToOne). */
    private void verifierReservationLibre(Reservation reservation, Long idContratCourant) {
        Contrat lie = reservation.getContrat();
        if (lie != null && !lie.getIdContrat().equals(idContratCourant)) {
            throw new ConflictException(
                    "La réservation " + reservation.getIdReservation()
                            + " est déjà associée au contrat " + lie.getIdContrat());
        }
    }
}
