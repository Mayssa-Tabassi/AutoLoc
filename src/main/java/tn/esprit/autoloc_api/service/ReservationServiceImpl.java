package tn.esprit.autoloc_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc_api.domain.Client;
import tn.esprit.autoloc_api.domain.Reservation;
import tn.esprit.autoloc_api.domain.Vehicule;
import tn.esprit.autoloc_api.dto.ReservationRequestDTO;
import tn.esprit.autoloc_api.dto.ReservationResponseDTO;
import tn.esprit.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc_api.mapper.ReservationMapper;
import tn.esprit.autoloc_api.repository.IClientRepository;
import tn.esprit.autoloc_api.repository.IReservationRepository;
import tn.esprit.autoloc_api.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IClientRepository clientRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationResponseDTO create(ReservationRequestDTO dto) {
        verifierDates(dto);
        Reservation reservation = reservationMapper.toEntity(dto);
        reservation.setVehicule(chargerVehicule(dto.vehiculeId()));
        reservation.setClient(chargerClient(dto.clientId()));
        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO findById(Long id) {
        return reservationMapper.toResponse(charger(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> findAll() {
        return reservationMapper.toResponseList(reservationRepository.findAll());
    }

    @Override
    @Transactional
    public ReservationResponseDTO update(Long id, ReservationRequestDTO dto) {
        Reservation existante = charger(id);
        verifierDates(dto);
        reservationMapper.updateEntity(dto, existante);
        existante.setVehicule(chargerVehicule(dto.vehiculeId()));
        existante.setClient(chargerClient(dto.clientId()));
        return reservationMapper.toResponse(reservationRepository.save(existante));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Réservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private Reservation charger(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation", id));
    }

    private Vehicule chargerVehicule(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", idVehicule));
    }

    private Client chargerClient(Long idClient) {
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client", idClient));
    }

    private void verifierDates(ReservationRequestDTO dto) {
        if (dto.dateFin().isBefore(dto.dateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
