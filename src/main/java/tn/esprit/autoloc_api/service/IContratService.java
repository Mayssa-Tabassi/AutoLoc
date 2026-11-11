package tn.esprit.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc_api.dto.ContratRequestDTO;
import tn.esprit.autoloc_api.dto.ContratResponseDTO;

public interface IContratService {

    ContratResponseDTO create(ContratRequestDTO contrat);

    ContratResponseDTO findById(Long id);

    List<ContratResponseDTO> findAll();

    ContratResponseDTO update(Long id, ContratRequestDTO contrat);

    void deleteById(Long id);
}
