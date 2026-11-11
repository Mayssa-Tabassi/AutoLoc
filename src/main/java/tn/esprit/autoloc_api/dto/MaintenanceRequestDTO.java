package tn.esprit.autoloc_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MaintenanceRequestDTO(
        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        LocalDate dateFin,

        @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
        String description,

        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être strictement positif")
        Long vehiculeId
) {
}
