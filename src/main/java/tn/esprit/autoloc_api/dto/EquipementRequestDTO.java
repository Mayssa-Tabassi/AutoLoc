package tn.esprit.autoloc_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EquipementRequestDTO(
        @NotBlank(message = "Le libellé de l'équipement est obligatoire")
        @Size(max = 100, message = "Le libellé ne doit pas dépasser 100 caractères")
        String libelle
) {
}
