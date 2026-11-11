package tn.esprit.autoloc_api.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc_api.domain.Equipement;
import tn.esprit.autoloc_api.domain.Vehicule;
import tn.esprit.autoloc_api.dto.VehiculeRequestDTO;
import tn.esprit.autoloc_api.dto.VehiculeResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VehiculeMapper {

    /** L'agence et les équipements sont résolus par le service. */
    public Vehicule toEntity(VehiculeRequestDTO dto) {
        Vehicule vehicule = new Vehicule();
        updateEntity(dto, vehicule);
        return vehicule;
    }

    public void updateEntity(VehiculeRequestDTO dto, Vehicule vehicule) {
        vehicule.setImmatriculation(dto.immatriculation());
        vehicule.setMarque(dto.marque());
        vehicule.setModele(dto.modele());
        vehicule.setCategorie(dto.categorie());
        vehicule.setTarifJournalier(dto.tarifJournalier());
        vehicule.setStatut(dto.statut());
    }

    public VehiculeResponseDTO toResponse(Vehicule v) {
        Set<Long> equipementIds = v.getEquipements() == null
                ? Set.of()
                : v.getEquipements().stream()
                        .map(Equipement::getIdEquipement)
                        .collect(Collectors.toSet());

        return new VehiculeResponseDTO(v.getIdVehicule(), v.getImmatriculation(), v.getMarque(),
                v.getModele(), v.getCategorie(), v.getTarifJournalier(), v.getStatut(),
                v.getAgence() == null ? null : v.getAgence().getIdAgence(), equipementIds);
    }

    public List<VehiculeResponseDTO> toResponseList(List<Vehicule> vehicules) {
        return vehicules.stream().map(this::toResponse).toList();
    }
}
