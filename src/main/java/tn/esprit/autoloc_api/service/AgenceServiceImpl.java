package tn.esprit.autoloc_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc_api.domain.Agence;
import tn.esprit.autoloc_api.dto.AgenceResponseDTO;
import tn.esprit.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc_api.mapper.AgenceMapper;
import tn.esprit.autoloc_api.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    @Transactional
    public AgenceResponseDTO create(Agence dto) {
        return agenceMapper.toResponse(agenceRepository.save(agenceMapper.toEntity(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceResponseDTO findById(Long id) {
        return agenceMapper.toResponse(charger(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceResponseDTO> findAll() {
        return agenceMapper.toResponseList(agenceRepository.findAll());
    }

    @Override
    @Transactional
    public AgenceResponseDTO update(Long id, Agence dto) {
        Agence existant = charger(id);
        agenceMapper.updateEntity(dto, existant);
        return agenceMapper.toResponse(agenceRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private Agence charger(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }
}
