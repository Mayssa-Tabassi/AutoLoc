package tn.esprit.autoloc_api.dto;

import java.time.LocalDate;

public record MaintenanceResponseDTO(Long idMaintenance, LocalDate dateDebut, LocalDate dateFin,
                                     String description, Long vehiculeId) {
}
