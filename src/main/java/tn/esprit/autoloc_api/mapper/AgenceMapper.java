package tn.esprit.autoloc_api.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc_api.domain.Agence;
import tn.esprit.autoloc_api.dto.AgenceResponseDTO;

import java.util.List;

@Component
public class AgenceMapper {

    public Agence toEntity(Agence dto) {
        Agence agence = new Agence();
        updateEntity(dto, agence);
        return agence;
    }
    public void updateEntity(Agence dto, Agence agence) {
        agence.setNom(dto.getNom());
        agence.setVille(dto.getVille());
        agence.setAdresse(dto.getAdresse());
        agence.setTelephone(dto.getTelephone());
    }

    public AgenceResponseDTO toResponse(Agence a) {
        return new AgenceResponseDTO(a.getIdAgence(), a.getNom(), a.getVille(),
                a.getAdresse(), a.getTelephone());
    }

    public List<AgenceResponseDTO> toResponseList(List<Agence> agences) {
        return agences.stream().map(this::toResponse).toList();
    }
}
