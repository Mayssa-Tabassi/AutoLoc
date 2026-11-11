package tn.esprit.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc_api.dto.VehiculeRequestDTO;
import tn.esprit.autoloc_api.dto.VehiculeResponseDTO;

public interface IVehiculeService {

    VehiculeResponseDTO create(VehiculeRequestDTO dto);

    VehiculeResponseDTO findById(Long id);

    List<VehiculeResponseDTO> findAll();

    VehiculeResponseDTO update(Long id, VehiculeRequestDTO dto);

    void deleteById(Long id);
}
