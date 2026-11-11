package tn.esprit.autoloc_api.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc_api.domain.Agence;
import tn.esprit.autoloc_api.domain.Equipement;
import tn.esprit.autoloc_api.domain.Vehicule;
import tn.esprit.autoloc_api.dto.VehiculeRequestDTO;
import tn.esprit.autoloc_api.dto.VehiculeResponseDTO;
import tn.esprit.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc_api.mapper.VehiculeMapper;
import tn.esprit.autoloc_api.repository.IAgenceRepository;
import tn.esprit.autoloc_api.repository.IEquipementRepository;
import tn.esprit.autoloc_api.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    @Transactional
    public VehiculeResponseDTO create(VehiculeRequestDTO dto) {
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        vehicule.setAgence(chargerAgence(dto.agenceId()));
        vehicule.setEquipements(chargerEquipements(dto.equipementIds()));
        return vehiculeMapper.toResponse(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeResponseDTO findById(Long id) {
        return vehiculeMapper.toResponse(charger(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResponseDTO> findAll() {
        return vehiculeMapper.toResponseList(vehiculeRepository.findAll());
    }

    @Override
    @Transactional
    public VehiculeResponseDTO update(Long id, VehiculeRequestDTO dto) {
        Vehicule existant = charger(id);
        vehiculeMapper.updateEntity(dto, existant);
        existant.setAgence(chargerAgence(dto.agenceId()));
        existant.setEquipements(chargerEquipements(dto.equipementIds()));
        return vehiculeMapper.toResponse(vehiculeRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Véhicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private Vehicule charger(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", id));
    }

    private Agence chargerAgence(Long idAgence) {
        if (idAgence == null) {
            return null;
        }
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }

    private Set<Equipement> chargerEquipements(Set<Long> ids) {
        Set<Equipement> equipements = new HashSet<>();
        if (ids == null) {
            return equipements;
        }
        for (Long idEquipement : ids) {
            equipements.add(equipementRepository.findById(idEquipement)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Equipement", idEquipement)));
        }
        return equipements;
    }
}
