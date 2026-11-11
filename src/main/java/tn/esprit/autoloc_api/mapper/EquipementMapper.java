package tn.esprit.autoloc_api.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc_api.domain.Equipement;
import tn.esprit.autoloc_api.dto.EquipementRequestDTO;
import tn.esprit.autoloc_api.dto.EquipementResponseDTO;

import java.util.List;

@Component
public class EquipementMapper {

    public Equipement toEntity(EquipementRequestDTO dto) {
        Equipement equipement = new Equipement();
        updateEntity(dto, equipement);
        return equipement;
    }

    public void updateEntity(EquipementRequestDTO dto, Equipement equipement) {
        equipement.setLibelle(dto.libelle());
    }

    public EquipementResponseDTO toResponse(Equipement e) {
        return new EquipementResponseDTO(e.getIdEquipement(), e.getLibelle());
    }

    public List<EquipementResponseDTO> toResponseList(List<Equipement> equipements) {
        return equipements.stream().map(this::toResponse).toList();
    }
}
