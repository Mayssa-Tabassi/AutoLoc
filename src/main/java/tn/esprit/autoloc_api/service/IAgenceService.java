package tn.esprit.autoloc_api.service;

import java.util.List;

import tn.esprit.autoloc_api.domain.Agence;
import tn.esprit.autoloc_api.dto.AgenceResponseDTO;

public interface IAgenceService {

    AgenceResponseDTO create(Agence dto);

    AgenceResponseDTO findById(Long id);

    List<AgenceResponseDTO> findAll();

    AgenceResponseDTO update(Long id, Agence dto);

    void deleteById(Long id);
}
