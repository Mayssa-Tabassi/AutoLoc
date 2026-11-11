package tn.esprit.autoloc_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc_api.dto.PaiementResponseDTO;
import tn.esprit.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc_api.mapper.PaiementMapper;
import tn.esprit.autoloc_api.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;

    @Override
    public PaiementResponseDTO findById(Long id) {
        return paiementRepository.findById(id)
                .map(paiementMapper::toResponse)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<PaiementResponseDTO> findAll() {
        return paiementMapper.toResponseList(paiementRepository.findAll());
    }
}
