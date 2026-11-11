package tn.esprit.autoloc_api.dto;

import tn.esprit.autoloc_api.domain.RoleEmploye;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EmployeRequestDTO(
        @NotBlank(message = "Le nom de l'employé est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
        String nom,

        @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
        String prenom,

        @NotNull(message = "Le rôle de l'employé est obligatoire (AGENT ou MANAGER)")
        RoleEmploye role,

        @Positive(message = "L'identifiant de l'agence doit être strictement positif")
        Long agenceId
) {
}
