package tn.esprit.autoloc_api.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc_api.domain.Employe;
import tn.esprit.autoloc_api.dto.EmployeRequestDTO;
import tn.esprit.autoloc_api.dto.EmployeResponseDTO;

import java.util.List;

@Component
public class EmployeMapper {

    /** L'agence est résolue par le service. */
    public Employe toEntity(EmployeRequestDTO dto) {
        Employe employe = new Employe();
        updateEntity(dto, employe);
        return employe;
    }

    public void updateEntity(EmployeRequestDTO dto, Employe employe) {
        employe.setNom(dto.nom());
        employe.setPrenom(dto.prenom());
        employe.setRole(dto.role());
    }

    public EmployeResponseDTO toResponse(Employe e) {
        return new EmployeResponseDTO(e.getIdEmploye(), e.getNom(), e.getPrenom(), e.getRole(),
                e.getAgence() == null ? null : e.getAgence().getIdAgence());
    }

    public List<EmployeResponseDTO> toResponseList(List<Employe> employes) {
        return employes.stream().map(this::toResponse).toList();
    }
}
