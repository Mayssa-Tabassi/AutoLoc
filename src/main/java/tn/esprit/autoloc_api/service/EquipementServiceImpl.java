package tn.esprit.autoloc_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc_api.domain.Equipement;
import tn.esprit.autoloc_api.dto.EquipementRequestDTO;
import tn.esprit.autoloc_api.dto.EquipementResponseDTO;
import tn.esprit.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc_api.mapper.EquipementMapper;
import tn.esprit.autoloc_api.repository.IEquipementRepository;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    @Transactional
    public EquipementResponseDTO create(EquipementRequestDTO dto) {
        return equipementMapper.toResponse(equipementRepository.save(equipementMapper.toEntity(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public EquipementResponseDTO findById(Long id) {
        return equipementMapper.toResponse(charger(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipementResponseDTO> findAll() {
        return equipementMapper.toResponseList(equipementRepository.findAll());
    }

    @Override
    @Transactional
    public EquipementResponseDTO update(Long id, EquipementRequestDTO dto) {
        Equipement existant = charger(id);
        equipementMapper.updateEntity(dto, existant);
        return equipementMapper.toResponse(equipementRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private Equipement charger(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }
}
