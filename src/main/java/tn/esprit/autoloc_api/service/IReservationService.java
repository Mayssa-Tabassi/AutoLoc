package tn.esprit.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc_api.dto.ReservationRequestDTO;
import tn.esprit.autoloc_api.dto.ReservationResponseDTO;

public interface IReservationService {

    ReservationResponseDTO create(ReservationRequestDTO dto);

    ReservationResponseDTO findById(Long id);

    List<ReservationResponseDTO> findAll();

    ReservationResponseDTO update(Long id, ReservationRequestDTO dto);

    void deleteById(Long id);
}
