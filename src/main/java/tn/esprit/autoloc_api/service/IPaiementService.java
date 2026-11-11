package tn.esprit.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc_api.dto.PaiementResponseDTO;

public interface IPaiementService {

    PaiementResponseDTO findById(Long id);

    List<PaiementResponseDTO> findAll();
}
