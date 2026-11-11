package tn.esprit.autoloc_api.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc_api.domain.Maintenance;
import tn.esprit.autoloc_api.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc_api.dto.MaintenanceResponseDTO;

import java.util.List;

@Component
public class MaintenanceMapper {

    /** Le véhicule est résolu par le service. */
    public Maintenance toEntity(MaintenanceRequestDTO dto) {
        Maintenance maintenance = new Maintenance();
        updateEntity(dto, maintenance);
        return maintenance;
    }

    public void updateEntity(MaintenanceRequestDTO dto, Maintenance maintenance) {
        maintenance.setDateDebut(dto.dateDebut());
        maintenance.setDateFin(dto.dateFin());
        maintenance.setDescription(dto.description());
    }

    public MaintenanceResponseDTO toResponse(Maintenance m) {
        return new MaintenanceResponseDTO(m.getIdMaintenance(), m.getDateDebut(), m.getDateFin(),
                m.getDescription(),
                m.getVehicule() == null ? null : m.getVehicule().getIdVehicule());
    }

    public List<MaintenanceResponseDTO> toResponseList(List<Maintenance> maintenances) {
        return maintenances.stream().map(this::toResponse).toList();
    }
}
